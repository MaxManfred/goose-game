package com.xpeppers.hiring.massimo_manfredino.goose_game.command;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.CommandParserException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

public class CommandParser extends MessageProviderClient {

    private static CommandParser instance;

    private CommandParser() throws MessageProviderClientException {
    }

    public static CommandParser getInstance() throws MessageProviderClientException {
        if (instance == null) {
            instance = new CommandParser();
        }

        return instance;
    }

    /**
     * Parses a console input typed by the user and tries to understand the provided command.
     *
     * @param consoleInput a console input typed by the user in order to execute a system command.
     * @return <code>true</code> if command is succesfully parsed and executed, <code>false</code> otherwise
     */
    public boolean parseCommand(String consoleInput) throws CommandParserException, MessageProviderException {
        if (StringUtils.isEmpty(consoleInput)) {
            throw new CommandParserException(
                    getMessageProvider().getMessage("exception.command-parser.unrecognized.command")
            );
        }

        String command = consoleInput.trim();

//        case 'help'
        if (getMessageProvider().getMessage("command.help").equals(command)) {
            System.out.println("\n");

//            list all possible commands
            getMessageProvider().getKeys()
                    .stream()
                    .filter(key -> key.startsWith("command."))
                    .map(key -> getMessageProvider().getMessage(key))
                    .sorted()
                    .forEach(System.out::println);

            System.out.println("\n");
        }

        return true;
    }
}
