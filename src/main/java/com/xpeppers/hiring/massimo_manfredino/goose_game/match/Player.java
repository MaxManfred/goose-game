package com.xpeppers.hiring.massimo_manfredino.goose_game.match;

import com.xpeppers.hiring.massimo_manfredino.goose_game.board.Cell;

/**
 * A goose game player.
 */
public class Player implements Comparable<Player> {

    /**
     * Name of the player.
     */
    private String name;

    /**
     * Current cell of the player
     */
    protected Cell cell;

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public int compareTo(Player o) {
        return name.compareTo(o.getName());
    }
}
