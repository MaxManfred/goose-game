package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;

public class EndCell extends RegularCell {

    public EndCell() throws MessageProviderClientException {
        super(63);
    }

    @Override
    public CellType getCellType() {
        return CellType.END_CELL;
    }
}
