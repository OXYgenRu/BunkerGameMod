package net.bunkergame.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.bunkergame.GameSession;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import javax.swing.text.StyledEditorKit;

public class SetGameSupport {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("bunkergame").then(CommandManager.literal("mysession").then(CommandManager.literal("set").then(CommandManager.literal("gamesupport").then(CommandManager.argument("flag", BoolArgumentType.bool()).executes(context -> setGameSupport(context.getSource(), BoolArgumentType.getBool(context, "flag"))))))));
    }

    private static int setGameSupport(ServerCommandSource context, boolean flag) {
        final ServerPlayerEntity player = context.getPlayer();
        assert player != null;
        GameSession game_sess = GameSession.getSession(player);
        if (game_sess == null) {
            player.sendMessage(Text.literal("Сессия игрока " + player.getGameProfile().getName() + " не найдена"));
            return Command.SINGLE_SUCCESS;
        }
        try {
            game_sess.game_support = flag;
            player.sendMessage(Text.literal("Флаг поддержки игры успешно изменен"),
                    false);
        } catch (Exception e) {
            player.sendMessage(Text.literal(e.getMessage()));
        }
        return Command.SINGLE_SUCCESS;
    }


}


