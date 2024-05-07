package net.bunkergame;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

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
}
