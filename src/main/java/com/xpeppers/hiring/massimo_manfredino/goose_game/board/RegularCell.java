package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.match.Player;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;

import java.util.LinkedList;
import java.util.List;

public class RegularCell extends MessageProviderClient implements Cell {

    private int index;

    private List<Player> standingPlayers;

    private boolean prankEnabled;

    protected RegularCell(int index) throws MessageProviderClientException {
        super();
        setIndex(index);
        standingPlayers = new LinkedList<>();

//        if -Dgg.enable.prank=true, no two players can share the same cell, hence the player occupying the cell
//        is sent to the cell where the new incoming player was before (prank).
        prankEnabled = Boolean.parseBoolean(System.getProperty("gg.enable.prank", "false"));
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
        Cell previousPlayerCell = player.getCell();

        if(previousPlayerCell != null) {
//            remove player from his previous cell (he is in transit now)
            previousPlayerCell.getStandingPlayers().remove(0);
            player.setCell(null);

//            display message
            System.out.println(String.format(
                "%s moves from %s to %s.",
                player.getName(), getDisplayableIndex(previousPlayerCell.getIndex()), getDisplayableIndex(getIndex())
            ));
        }

//        if prank is enabled, remove the player occupying this cell and send her back to the cell incoming user was before
        if (prankEnabled && !standingPlayers.isEmpty() && previousPlayerCell != null) {
//            he is in trantit now
            Player removedPlayer = standingPlayers.remove(0);
            removedPlayer.setCell(null);

            previousPlayerCell.addStandingPlayer(removedPlayer);

//            display message
            System.out.println(String.format(
                "On %s there is %s, who returns to %s.",
                getDisplayableIndex(getIndex()), removedPlayer.getName(), getDisplayableIndex(previousPlayerCell.getIndex())
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
