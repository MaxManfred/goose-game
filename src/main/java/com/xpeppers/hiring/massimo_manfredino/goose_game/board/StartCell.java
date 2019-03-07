package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.board.CellType;
import com.xpeppers.hiring.massimo_manfredino.goose_game.board.RegularCell;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;

public class StartCell extends RegularCell {

    public StartCell() throws MessageProviderClientException {
        super(0);
    }

    @Override
    public CellType getCellType() {
        return CellType.START_CELL;
    }
}
