package net.bunkergame.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.bunkergame.GameSession;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MySession {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("bunkergame").
                then(CommandManager.literal("mysession").then(CommandManager.literal("info").executes(MySession::MySession))));
    }

    private static int MySession(CommandContext<ServerCommandSource> commandContext) {
        final ServerCommandSource source = commandContext.getSource();
        final ServerPlayerEntity player = source.getPlayer();

        assert player != null;

        GameSession game_session = GameSession.getSession(player);
        if (game_session == null) {
            player.sendMessage(Text.literal("Вы не являетесь владельцем сессии"));
        } else {
            player.sendMessage(Text.literal("Владалец сессии: " + game_session.owner.getGameProfile().getName()));
            List<String> playres_list = new ArrayList<>();
            for (ServerPlayerEntity game_sess_player : game_session.players) {
                playres_list.add(game_sess_player.getGameProfile().getName());
            }
            player.sendMessage(Text.literal("Игроки: " + playres_list));
        }
        return Command.SINGLE_SUCCESS;
    }
}
