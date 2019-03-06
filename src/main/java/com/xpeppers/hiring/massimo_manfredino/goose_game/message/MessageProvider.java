package com.xpeppers.hiring.massimo_manfredino.goose_game.message;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class MessageProvider {

    private static MessageProvider instance;

    private static final String MESSAGE_BUNDLE_FILE_NAME = "messages.properties";

    private static Properties messageBundle;

    private MessageProvider() {
    }

    public static MessageProvider getInstance() throws MessageProviderException {
        if (instance == null) {
            instance = new MessageProvider();
            instance.getClass().getClassLoader();

//            load message bundle
            messageBundle = new Properties();

            InputStream inputStream =
                MessageProvider.class.getClassLoader().getResourceAsStream(MESSAGE_BUNDLE_FILE_NAME);
            if (inputStream != null) {
                try {
                    messageBundle.load(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    throw new MessageProviderException(
                        String.format("Unable to load message bundle: %s", e.getMessage())
                    );
                }
            }
        }

        return instance;
    }

    public String getMessage(String key) {
        return messageBundle.getProperty(key);
    }

    public Set<String> getKeys() {
        return messageBundle.stringPropertyNames();
    }
}
