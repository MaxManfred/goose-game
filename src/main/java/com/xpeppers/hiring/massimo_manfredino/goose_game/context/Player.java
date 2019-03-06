package com.xpeppers.hiring.massimo_manfredino.goose_game.context;

/**
 * A goose game player.
 */
public class Player implements Comparable<Player> {

    private String name;

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Player o) {
        return name.compareTo(o.getName());
    }
}
