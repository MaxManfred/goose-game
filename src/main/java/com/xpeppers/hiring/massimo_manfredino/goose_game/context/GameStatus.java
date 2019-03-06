package com.xpeppers.hiring.massimo_manfredino.goose_game.context;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.GameStatusException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameStatus extends MessageProviderClient {

    private List<Player> players;

    private static GameStatus instance;

    private GameStatus() throws MessageProviderClientException {
        super();

        players = new LinkedList<>();
    }

    public static GameStatus getInstance() throws MessageProviderClientException {
        if (instance == null) {
            instance = new GameStatus();
        }

        return instance;
    }

    /**
     * As a player, I want to add me to the game so that I can play.
     * If there is no participant
     * the user writes: "add player Pippo"
     * the system responds: "players: Pippo"
     * the user writes: "add player Pluto"
     * the system responds: "players: Pippo, Pluto"
     *
     * @param name the player's name
     * @return <code>true</code> if the player has been succesfully added, <code>false</code> otherwise
     * @throws GameStatusException if the player name is null or empty
     */
    public boolean addPlayer(String name) throws GameStatusException {
        if (StringUtils.isEmpty(name)) {
            throw new GameStatusException(getMessageProvider().getMessage("exception.game-status.null.player.name"));
        }

        if (players.stream().filter(p -> p.getName().equals(name)).count() > 0) {
            System.out.println(String.format("%s: already existing player", name));
        } else {
            Player p = new Player(name);
            players.add(p);

            List<String> playerNames = players.stream().sorted().map(Player::getName).collect(toList());
            System.out.println(String.format("players: %s", StringUtils.join(playerNames, ", ")));
        }

        return true;
    }
}
