package net.bunkergame.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.bunkergame.GameSession;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CreateGameSession {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("bunkergame").
                then(CommandManager.literal("createsession").executes(CreateGameSession::createGameSession)));
    }

    public static int createGameSession(CommandContext<ServerCommandSource> context)
            throws CommandSyntaxException {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity player = source.getPlayer();

        assert player != null;

        try {
            GameSession new_game_session = new GameSession(player);
            player.sendMessage(Text.literal("Сессия усппешно создана"));
        } catch (Exception e) {
            player.sendMessage(Text.literal(e.getMessage()));
        }
        return Command.SINGLE_SUCCESS;

    }
}
