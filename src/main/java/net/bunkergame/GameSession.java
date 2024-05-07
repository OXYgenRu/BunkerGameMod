package net.bunkergame;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.bunkergame.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static net.bunkergame.items.ModItems.*;
import static net.bunkergame.items.ModItems.SPECIAL_CONDITIONS_CARD_2;

public class GameSession {
    public static List<GameSession> game_sessions_list = new ArrayList<>();

    public ServerPlayerEntity owner;
    public List<ServerPlayerEntity> players = new ArrayList<ServerPlayerEntity>();

    public void addPlayer(ServerPlayerEntity player) throws Exception {
        if (players.contains(player)) {
            throw new Exception("Вы уже добавлены в сессию игрока " + owner.getGameProfile().getName());
        }
        for (GameSession session : game_sessions_list) {
            if (session.players.contains(player)) {
                throw new Exception("Вы уже добавлены в сессию игрока " + session.owner.getGameProfile().getName()
                        + " и не можете быть добавлены в еще одну сессию");
            }
        }
        players.add(player);
    }

    public GameSession(ServerPlayerEntity player) throws Exception {
        if (GameSession.getSession(player) != null) {
            throw new Exception("Вы уже являетесь владельцем сессии");
        }
        owner = player;
        game_sessions_list.add(this);
    }

    public static GameSession getSession(ServerPlayerEntity player) {
        for (GameSession session : game_sessions_list) {
            if (session.owner == player) {
                return session;
            }
        }
        return null;
    }

    public static void startSession(ServerPlayerEntity player) throws Exception {
        GameSession session = getSession(player);
        if (session == null) {
            throw new Exception("Вы не являетесь владельцем сессии");
        }
        for (ServerPlayerEntity current_player : session.players) {
            giveCards(current_player);
        }
    }

    public static void giveCards(ServerPlayerEntity player) throws Exception {
        assert player != null;


        URL url = new URL("https://randomall.ru/api/gens/1723");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        JsonReader jsonReader = new JsonReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();

        conn.disconnect();

        String character_stat_line = jsonObject.get("msg").getAsString();
        String[] stats = character_stat_line.split("\n");

        List<ModItems.InfoCard> card_list = new ArrayList<>();

        card_list.add(CHARACTER_CARD);
        card_list.add(AGE_BIO_CARD);
        card_list.add(BODY_TYPE_CARD);
        card_list.add(PROFESSION_CARD);
        card_list.add(HEALTH_CARD);
        card_list.add(HOBBY_CARD);
        card_list.add(PHOBIA_CARD);
        card_list.add(TRAIT_CARD);
        card_list.add(INVENTORY_CARD);
        card_list.add(ADDITIONAL_INFORMATION_CARD);
        card_list.add(SPECIAL_CONDITIONS_CARD_1);
        card_list.add(SPECIAL_CONDITIONS_CARD_2);

        for (int i = 0; i < card_list.size(); i++) {
            ModItems.InfoCard card = card_list.get(i);
            NbtCompound displayTag = new NbtCompound();
            displayTag.putString("CardStat", stats[i]);
            ItemStack card_stack = new ItemStack(card, 1);
            card_stack.getOrCreateNbt().put("CustomTag", displayTag);
            player.giveItemStack(card_stack);
        }


    }
}
