package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;

public class BridgeCell extends RegularCell {

    public BridgeCell() throws MessageProviderClientException {
        super(6);
    }

    @Override
    public CellType getCellType() {
        return CellType.BRIDGE_CELL;
    }
}
