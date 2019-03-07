package com.xpeppers.hiring.massimo_manfredino.goose_game.message;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderException;

public abstract class MessageProviderClient {

    protected MessageProvider messageProvider;

    protected MessageProviderClient() throws MessageProviderClientException {
        init();
    }

    protected MessageProvider getMessageProvider() {
        return messageProvider;
    }

    protected void init() throws MessageProviderClientException {
        try {
            messageProvider = MessageProvider.getInstance();
        } catch (MessageProviderException e) {
            throw new MessageProviderClientException(String.format("Unable to load message bundle: %s", e.getMessage()));
        }
    }
}
