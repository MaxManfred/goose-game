package com.xpeppers.hiring.massimo_manfredino.goose_game.command;

import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.CommandParserException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MatchException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.MessageProviderClientException;
import com.xpeppers.hiring.massimo_manfredino.goose_game.match.Match;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProviderClient;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser extends MessageProviderClient {

    private static final String ADD_PLAYER_COMMAND_STRING_PATTERN = "^add player (?<player>[A-Za-z]*)$";

    private static final String MOVE_PLAYER_COMMAND_STRING_PATTERN = "^move (?<player>[a-zA-Z]*)\\s?(?<dice1>[1-6])?,?\\s?(?<dice2>[1-6]?)$";

    private static Pattern addPlayerCommandPattern;

    private static Pattern movePlayerCommandPattern;

    private static CommandParser instance;

    private static Match match;

    private CommandParser() throws MessageProviderClientException {
        super();
        match = Match.getInstance();

//        create command pattern objects
        addPlayerCommandPattern = Pattern.compile(ADD_PLAYER_COMMAND_STRING_PATTERN);
        movePlayerCommandPattern = Pattern.compile(MOVE_PLAYER_COMMAND_STRING_PATTERN);
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
    public boolean parseCommand(String consoleInput) throws CommandParserException, MatchException {
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
                    .filter(key -> key.startsWith("command.description"))
                    .map(key -> getMessageProvider().getMessage(key))
                    .sorted()
                    .forEach(System.out::println);

            System.out.println("\n");
        } else {
            Matcher m = addPlayerCommandPattern.matcher(command);
            if (m.find()) {
//                case 'add player PLAYER_NAME'
                String playerName = m.group("player");
                match.addPlayer(playerName);
                return true;
            }

            m = movePlayerCommandPattern.matcher(command);
            if (m.find()) {
//                case move PLAYER_NAME or move PLAYER_NAME DICE_1, DICE_2

                String playerName = m.group("player");
                int dice1, dice2;
                if (!StringUtils.isEmpty(m.group("dice1")) && !StringUtils.isEmpty(m.group("dice2"))) {
                    dice1 = Integer.parseInt(m.group("dice1"));
                    dice2 = Integer.parseInt(m.group("dice2"));
                } else {
//                    generate random dice scores
                    Random r = new Random();
                    dice1 = r.nextInt(6) + 1;
                    dice2 = r.nextInt(6) + 1;
                }

                match.movePlayer(playerName, dice1, dice2);
                return true;
            }

//            invalid command
            throw new CommandParserException(
                getMessageProvider().getMessage("exception.command-parser.unrecognized.command")
            );
        }

        return true;
    }
}
