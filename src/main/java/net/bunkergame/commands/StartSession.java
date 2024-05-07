package net.bunkergame.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.bunkergame.GameSession;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class StartSession {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("bunkergame").
                then(CommandManager.literal("startsession").executes(StartSession::run)));
    }

    private static int run(CommandContext<ServerCommandSource> commandContext) {
        final ServerCommandSource source = commandContext.getSource();
        final ServerPlayerEntity player = source.getPlayer();
        try {
            GameSession.startSession(player);
        }
        catch (Exception e) {
            player.sendMessage(Text.literal(e.getMessage()));
        }
        return Command.SINGLE_SUCCESS;
    }
}
