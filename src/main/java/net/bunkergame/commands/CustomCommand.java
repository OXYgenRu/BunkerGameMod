package net.bunkergame.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
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

            List<ItemStack> card_list = new ArrayList<>();

            card_list.add(new ItemStack(CHARACTER_CARD, 1));
            card_list.add(new ItemStack(AGE_BIO_CARD, 1));
            card_list.add(new ItemStack(BODY_TYPE_CARD, 1));
            card_list.add(new ItemStack(PROFESSION_CARD, 1));
            card_list.add(new ItemStack(HEALTH_CARD, 1));
            card_list.add(new ItemStack(HOBBY_CARD, 1));
            card_list.add(new ItemStack(PHOBIA_CARD, 1));
            card_list.add(new ItemStack(TRAIT_CARD, 1));
            card_list.add(new ItemStack(INVENTORY_CARD, 1));
            card_list.add(new ItemStack(ADDITIONAL_INFORMATION_CARD, 1));
            card_list.add(new ItemStack(SPECIAL_CONDITIONS_CARD_1, 1));
            card_list.add(new ItemStack(SPECIAL_CONDITIONS_CARD_2, 1));


            for (int i = 0; i < card_list.size(); i++) {
                ItemStack stack = card_list.get(i);
                InfoCard card = (InfoCard) stack.getItem();
                player.giveItemStack(new ItemStack(card, 1));
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
