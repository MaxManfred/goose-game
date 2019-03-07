package com.xpeppers.hiring.massimo_manfredino.goose_game.board;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;

import java.util.HashMap;
import java.util.Map;

public class Board extends MessageProviderClient {

    private static Board instance;

    private Map<Integer, Cell> indexToCellMap;

    protected Board() throws MessageProviderClientException {
        super();
    }

    public static Board getInstance() throws MessageProviderClientException {
        if (instance == null) {
            instance = new Board();
        }

        return instance;
    }

    @Override
    protected void init() throws MessageProviderClientException {
        super.init();

//        create board
        indexToCellMap = new HashMap<>();
        indexToCellMap.put(0, new StartCell());
        indexToCellMap.put(63, new EndCell());

//        create regular cells
        for(int i = 1; i < 63; i++) {
            indexToCellMap.put(i, new RegularCell(i));
        }

//        then override
        indexToCellMap.put(5, new GooseCell(5));
        indexToCellMap.put(6, new BridgeCell());
        indexToCellMap.put(14, new GooseCell(14));
        indexToCellMap.put(18, new GooseCell(18));
        indexToCellMap.put(23, new GooseCell(23));
        indexToCellMap.put(27, new GooseCell(27));
    }

    public Cell getStartCell() {
        return indexToCellMap.get(0);
    }

    public Cell getEndCell() {
        return indexToCellMap.get(63);
    }

    public Cell getBridgeCell() {
        return indexToCellMap.get(6);
    }

    public Cell getCell(int index) {
        return indexToCellMap.get(index);
    }
}
