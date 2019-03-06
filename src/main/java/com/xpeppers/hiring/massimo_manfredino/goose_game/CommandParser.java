package com.xpeppers.hiring.massimo_manfredino.goose_game;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.CommandParserException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;
import java.util.Set;

public class CommandParser {

    private MessageProvider messageProvider;

    public CommandParser() throws CommandParserException {
        try {
            messageProvider = MessageProvider.getInstance();
        } catch (MessageProviderException e) {
            throw new CommandParserException(String.format("Unable to load message bundle: %s", e.getMessage()));
        }
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
                messageProvider.getMessage("exception.command-parser.unrecognized.command")
            );
        }

        String command = consoleInput.trim();

//        case 'help'
        if(messageProvider.getMessage("command.help").equals(command)) {
            System.out.println("\n");

//            list all possible commands
            messageProvider.getKeys()
                .stream()
                .filter(key -> key.startsWith("command."))
                .map(key -> messageProvider.getMessage(key))
                .sorted()
                .forEach(System.out::println);

            System.out.println("\n");
        }

        return true;
    }
}
