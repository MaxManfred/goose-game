package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;

import java.util.stream.IntStream;

public class GooseCell extends RegularCell {

    private int[] validIndices = {5, 9, 14, 18, 23, 27};

    public GooseCell(int index) throws MessageProviderClientException {
        super(index);

        if (!IntStream.of(validIndices).anyMatch(x -> x == index)) {
//           index is invalid
            throw new IllegalArgumentException(getMessageProvider().getMessage("exception.cell.goose.invalid.index"));
        }
    }

    @Override
    public CellType getCellType() {
        return CellType.GOOSE_CELL;
    }
}