package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.match.Player;

import java.util.List;

public interface Cell {

    /**
     * Returns the index of this cell
     *
     * @return the index of this cell.
     */
    int getIndex();

    /**
     * Sets the index of this cell. Notice that index muste have a value between 0 (Start cell) and 63 (End cell).
     *
     * @param index the index of this cell.
     */
    void setIndex(int index);

    /**
     * Gets the cell type (see @{@link CellType}).
     *
     * @return the cell type
     */
    CellType getCellType();

//    /**
//     * Sets the cell type (see @{@link CellType}).
//     *
//     * @param cellType the cell type
//     */
//    void setCellType(CellType cellType);

    /**
     * Returns the list of players standing on this cell.
     *
     * @return the list of players standing on this cell.
     */
    List<Player> getStandingPlayers();

    /**
     * As a player, when I land on a space occupied by another player, I send him to my previous position so that the
     * game can be more entertaining.
     *
     * @param player    a new player to add to the list of players on this cell
     */
    void addStandingPlayer(Player player);
}
