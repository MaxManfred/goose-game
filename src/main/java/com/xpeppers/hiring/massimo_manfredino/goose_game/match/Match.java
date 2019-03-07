package com.xpeppers.hiring.massimo_manfredino.goose_game.match;

import com.xpeppers.hiring.massimo_manfredino.goose_game.board.Board;
import com.xpeppers.hiring.massimo_manfredino.goose_game.board.Cell;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MatchException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Match extends MessageProviderClient {

    private Map<String, Player> playersMap;

    private static Match instance;

    private Board board;

    private Match() throws MessageProviderClientException {
        super();

        playersMap = new HashMap<>();
        board = Board.getInstance();
    }

    public static Match getInstance() throws MessageProviderClientException {
        if (instance == null) {
            instance = new Match();
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
     * @return <code>true</code> if the player has been successfully added, <code>false</code> otherwise
     * @throws MatchException if the player name is null or empty
     */
    public boolean addPlayer(String name) throws MatchException {
        if (StringUtils.isEmpty(name)) {
            throw new MatchException(getMessageProvider().getMessage("exception.game-status.null.player.name"));
        }

        if (playersMap.keySet().stream().filter(n -> n.equals(name)).count() > 0) {
            System.out.println(String.format("%s: already existing player", name));
        } else {
//            create a new player and add to the list of players
            Player p = new Player(name);
            playersMap.put(name, p);

//            place the new player on the board
            Cell startCell = board.getStartCell();
            startCell.addStandingPlayer(p);

//            display message
            List<String> playerNames = playersMap.keySet().stream().sorted().collect(toList());
            System.out.println(String.format("players: %s", StringUtils.join(playerNames, ", ")));
        }

        return true;
    }

    /**
     * As a player, I want to move the marker on the board to make the game progress
     * If there are two participants "Pippo" and "Pluto" on space "Start"
     * the user writes: "move Pippo 4, 2"
     * the system responds: "Pippo rolls 4, 2. Pippo moves from Start to 6"
     * the user writes: "move Pluto 2, 2"
     * the system responds: "Pluto rolls 2, 2. Pluto moves from Start to 4"
     * the user writes: "move Pippo 2, 3"
     * the system responds: "Pippo rolls 2, 3. Pippo moves from 6 to 11"
     *
     * @param name  name of the player to move (player will be moved of dice1 + dice2 cells)
     * @param dice1 the dice1 score
     * @param dice2 the dice2 score
     * @return <code>true</code> if the player has been successfully moved, <code>false</code> otherwise
     * @throws MatchException if there are less than two players and the game cannot begin
     */
    public boolean movePlayer(String name, int dice1, int dice2) throws MatchException {
//        first make sure at least two players have been added
        if (playersMap.size() < 2) {
            throw new MatchException(getMessageProvider().getMessage("exception.match.not.enough.players.for.moving"));
        }

//        display message
        System.out.println(String.format("%s rolls %d, %d.", name, dice1, dice2));

        Player p = playersMap.get(name);

        if (p == null) {
            throw new MatchException(String.format(
                getMessageProvider().getMessage("exception.match.non.existing.player"), name
            ));
        }

        int oldCellIndex = p.getCell().getIndex();
        int newCellIndex = oldCellIndex + dice1 + dice2;
        board.getCell(newCellIndex).addStandingPlayer(p);

        return true;
    }
}
