package net.bunkergame;

import net.bunkergame.commands.*;
import net.bunkergame.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BunkerGame implements ModInitializer {
    public static final String MOD_ID = "bunkergame";
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        CommandRegistrationCallback.EVENT.register(
                CustomCommand::register);
        CommandRegistrationCallback.EVENT.register(
                CreateGameSession::register);
        CommandRegistrationCallback.EVENT.register(
                EnjoyGameSession::register);
        CommandRegistrationCallback.EVENT.register(
                MySession::register);
        CommandRegistrationCallback.EVENT.register(
                StartSession::register);
//        Registry.register(Registry.ITEM, new Identifier("tutorial", "fabric_item"), FABRIC_ITEM);
        LOGGER.info("Hello Fabric world!");
        ModItems.register();
    }
}
