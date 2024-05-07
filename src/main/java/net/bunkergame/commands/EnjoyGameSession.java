package net.bunkergame.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.bunkergame.GameSession;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class EnjoyGameSession {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {

        serverCommandSourceCommandDispatcher.register(CommandManager.literal("bunkergame").
                then(CommandManager.literal("enjoysession").then(CommandManager.argument("player", EntityArgumentType.player()).
                        executes(context -> enjoyGameSession(context.getSource(), EntityArgumentType.getPlayer(context, "player"))))));


    }

    public static int enjoyGameSession(ServerCommandSource context, ServerPlayerEntity nick) {
        final ServerPlayerEntity player = context.getPlayer();
        assert player != null;

        GameSession game_sess = GameSession.getSession(nick);
        if (game_sess == null) {
            player.sendMessage(Text.literal("Сессия игрока " + nick.getGameProfile().getName() + " не найдена"));
            return Command.SINGLE_SUCCESS;
        }
        try {
            game_sess.addPlayer(player);
            player.sendMessage(Text.literal("Вы добавлены в сессию игрока " +
                            game_sess.owner.getGameProfile().getName()),
                    false);
        } catch (Exception e) {
            player.sendMessage(Text.literal(e.getMessage()));
        }


        return Command.SINGLE_SUCCESS;

    }
}
