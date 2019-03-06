package com.xpeppers.hiring.massimo_manfredino.goose_game;

import com.xpeppers.hiring.massimo_manfredino.goose_game.command.CommandParser;
import com.xpeppers.hiring.massimo_manfredino.goose_game.exception.*;
import com.xpeppers.hiring.massimo_manfredino.goose_game.message.MessageProvider;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    private static MessageProvider messageProvider;

    private static CommandParser commandParser;

    public static void main(String[] arguments) throws GooseGameException {
//        get message provider
        try {
            messageProvider = MessageProvider.getInstance();
        } catch (MessageProviderException e) {
            throw new GooseGameException(String.format("Unable to load message bundle: %s", e.getMessage()));
        }

//        get command parser
        try {
            commandParser = CommandParser.getInstance();
        } catch (MessageProviderClientException e) {
            throw new GooseGameException(String.format("Unable to create command parser: %s", e.getMessage()));
        }

//        display game ASCII art
        printGameASCIIArt();

//        greet the user
        System.out.println(messageProvider.getMessage("main.greeting")); // cit.
        System.out.println(messageProvider.getMessage("main.help"));
        System.out.print(messageProvider.getMessage("main.prompt"));

//        create a scanner object to read input
        scanner = new Scanner(System.in);

        String currentCommand;
        while (!messageProvider.getMessage("command.quit").equals(currentCommand = scanner.nextLine())) {
//            process current command
            try {
                commandParser.parseCommand(currentCommand);
            } catch (CommandParserException | GameStatusException e) {
                System.out.println(e.getMessage());

//                throw new GooseGameException(e.getMessage());
            }

            System.out.print(messageProvider.getMessage("main.prompt"));
        }

//        say goodbye to user
        System.out.println("\n");
        System.out.println(messageProvider.getMessage("main.goodbye"));

//        quit
        System.exit(0);
    }

    private static void printGameASCIIArt() {
        System.out.println("\n");
        System.out.println("   ___                         ___");
        System.out.println("  / _ \\___   ___  ___  ___    / _ \\__ _ _ __ ___   ___");
        System.out.println(" / /_\\/ _ \\ / _ \\/ __|/ _ \\  / /_\\/ _` | '_ ` _ \\ / _ \\");
        System.out.println("/ /_\\\\ (_) | (_) \\__ \\  __/ / /_\\\\ (_| | | | | | |  __/");
        System.out.println("\\____/\\___/ \\___/|___/\\___| \\____/\\__,_|_| |_| |_|\\___|");
        System.out.println("\n");
    }
}
