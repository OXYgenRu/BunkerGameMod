package net.bunkergame.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static net.bunkergame.items.ModItems.*;

public class CustomCommand {
    public static List<InfoCard> card_list = new ArrayList<>();


    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity player = source.getPlayer();

        assert player != null;

        try {
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

            for (int i = 0; i < card_list.size(); i++) {
                InfoCard card = card_list.get(i);
                NbtCompound displayTag = new NbtCompound();
                displayTag.putString("CardStat", stats[i]);
                ItemStack card_stack = new ItemStack(card, 1);
                card_stack.getOrCreateNbt().put("CustomTag", displayTag);
                player.giveItemStack(card_stack);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        serverCommandSourceCommandDispatcher.register(CommandManager.literal("createbunkergamesession").executes(CustomCommand::run));
    }
}
