package com.xpeppers.hiring.massimo_manfredino.goose_game.command;

import com.xpeppers.hiring.massimo_manfredino.goose_game.match.Match;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.CommandParserException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.GameStatusException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

public class CommandParser extends MessageProviderClient {

    private static CommandParser instance;

    private static Match match;

    private CommandParser() throws MessageProviderClientException {
        super();
        match = Match.getInstance();
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
    public boolean parseCommand(String consoleInput) throws CommandParserException, GameStatusException {
        if (StringUtils.isEmpty(consoleInput)) {
            throw new CommandParserException(
                getMessageProvider().getMessage("exception.command-parser.unrecognized.command")
            );
        }

        String command = consoleInput.trim();

        if (getMessageProvider().getMessage("command.help").equals(command)) {
//            case 'help'
            System.out.println("\n");

//            list all possible commands
            getMessageProvider().getKeys()
                .stream()
                .filter(key -> key.startsWith("command."))
                .map(key -> getMessageProvider().getMessage(key))
                .sorted()
                .forEach(System.out::println);

            System.out.println("\n");
        } else if (command.startsWith(getMessageProvider().getMessage("command.add.player"))) {
//            case 'add player PLAYER_NAME'
            String[] tokens = command.split(" ");
            if(tokens.length == 2) {
                throw new CommandParserException(
                    getMessageProvider().getMessage("exception.game-status.null.player.name")
                );
            }
            if(tokens.length > 3) {
                throw new CommandParserException(
                    getMessageProvider().getMessage("exception.game-status.invalid.player.name")
                );
            } else {
                match.addPlayer(tokens[2]);
            }
        } else {
            throw new CommandParserException(
                getMessageProvider().getMessage("exception.command-parser.unrecognized.command")
            );
        }

        return true;
    }
}
