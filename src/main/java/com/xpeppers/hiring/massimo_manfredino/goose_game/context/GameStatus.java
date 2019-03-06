package com.xpeppers.hiring.massimo_manfredino.goose_game.context;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.GameStatusException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

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

    public boolean addPlayer(String name) throws GameStatusException {
        if (StringUtils.isEmpty(name)) {
            throw new GameStatusException(getMessageProvider().getMessage("exception.game-status.invalid.player.name"));
        }

        return true;
    }
}
