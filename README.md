# goose-game
XPeppers hiring test

To run the game, make sure you have JDK 8 and Maven 3 installed.
Then build the Maven project by 

    mvn clean package command

under target folder, you'll find a file named

> goose-game-1.0.0-jar-with-dependencies.jar

Execute the command

    java -Dgg.enable.prank=true|false -jar target/goose-game-1.0.0-jar-with-dependencies.jar 

to launch the game.

Notice that the value of the system property gg.enable.prank will activate the prank or leave it unactivated.

Once the game is started, type 

    help 

to get a list of available commands or

    quit
    
to exit. The rules are the one illustrated at [The Goose Game Kata](https://github.com/xpeppers/goose-game-kata)

