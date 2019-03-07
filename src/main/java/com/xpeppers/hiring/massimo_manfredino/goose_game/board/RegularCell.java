package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.match.Player;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RegularCell extends MessageProviderClient implements Cell {

    private int index;

    private List<Player> standingPlayers;

    private boolean prankEnabled;

    protected RegularCell(int index) throws MessageProviderClientException {
        super();
        setIndex(index);
        standingPlayers = new LinkedList<>();

//        if -Djava.enable.prank=true, no two players can share the same cell, hence the player occupying the cell
//        is sent to the cell where the new incoming player was before (prank).
        prankEnabled = Boolean.parseBoolean(System.getProperty("enable.prank", "false"));
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        if (index < 0 || index > 63) {
            throw new IllegalArgumentException(getMessageProvider().getMessage("exception.cell.invalid.index"));
        }
        this.index = index;
    }

    @Override
    public CellType getCellType() {
        return CellType.REGULAR_CELL;
    }

    @Override
    public List<Player> getStandingPlayers() {
        return standingPlayers;
    }

    @Override
    public void addStandingPlayer(Player player) {
//        check if player came from an other cell
        if(player.getCell() != null) {
//            display message
            System.out.println(String.format(
                "%s moves from %s to %s.",
                player.getName(), getDisplayableIndex(player.getCell().getIndex()), getDisplayableIndex(getIndex())
            ));
        }




//        the system responds: "Pippo rolls 1, 1. Pippo moves from 15 to 17. On 17 there is Pluto, who returns to 15"


        if (prankEnabled && !standingPlayers.isEmpty() && player.getCell() != null) {
            Cell oldPlayerCell = player.getCell();
//            move the current player on cell to the old cell of incoming player
            Player currentPlayer = standingPlayers.remove(0);
            currentPlayer.setCell(oldPlayerCell);
            oldPlayerCell.addStandingPlayer(currentPlayer);

//            display message
            System.out.println(String.format(
                "On %s there is %s, who returns to %s.",
                getDisplayableIndex(getIndex()), currentPlayer.getName(), getDisplayableIndex(oldPlayerCell.getIndex())
            ));
        }

//        put the incoming player to this cell
        standingPlayers.add(player);
        player.setCell(this);
    }

    private String getDisplayableIndex(int index) {
        switch (index) {
            case 0:
                return "Start";
            case 63:
                return "End";
            default:
                return String.valueOf(index);
        }
    }
}
