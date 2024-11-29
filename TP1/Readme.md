#  Space Invaders - README
## Description
This project is a basic implementation of the classic Space Invaders game, developed in Java. The game allows you to configure the difficulty level and an optional random seed for reproducible gameplay elements.

The program includes the following features:

- Difficulty level configuration.
- Option to provide a custom seed for reproducible results.
- Interactive gameplay via a console-based controller.

## Prerequisites
1- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.

   You can check your Java version by running:

```bash
java -version
 ```

2- **IDE or Console**: You can run the program from an IDE (e.g., IntelliJ IDEA, Eclipse, etc.) or directly from the terminal.


## Project Structure 

The project is organized into the following packages:

- **tp1**: Contains the Main class, the program's entry point.
- **tp1.control**: Contains the game controller logic.
- **tp1.logic**: Includes the game logic, such as Game and Level.
- **tp1.view**: Contains the messages displayed to the user.

## Running the Program
Follow these steps to run the game:

## 1. From an IDE

1. Open the project in your preferred IDE.  
2. Ensure that the Main.java file is set as the entry point for the program.  
3. Configure the program arguments:  

   1. **First Argument (Required):** Difficulty level. Allowed values:  
      - EASY (easy)  
      - NORMAL (normal)  
      - HARD (hard)  

   2. **Second Argument (Optional):** A numeric seed for random generation. If not provided, the program will use the current system time.  

4. Run the program.  

## 2. From the Terminal

1. Compile the program from the project root:

```bash
   javac -d bin src/tp1/*.java src/tp1/control/*.java src/tp1/logic/*.java src/tp1/view/*.java
```

2. Execute the program, providing the necessary arguments

```bash
   java -cp bin tp1.Main <LEVEL> [SEED]
```
  - Replace <LEVEL> with the desired difficulty level (EASY, NORMAL, HARD).
  - Replace [SEED] with an optional numeric seed.
## Game Messages

The program uses predefined messages to interact with the user:

- **USAGE**: Displays usage instructions when arguments are incorrect.
- **WELCOME**: Welcomes the user upon starting the game.
- **CONFIGURED_LEVEL**: Displays the configured difficulty level.
- **CONFIGURED_SEED**: Displays the configured seed.
- **SEED_NOT_A_NUMBER_ERROR**: Error displayed when the provided seed is not a valid number.
- **ALLOWED_LEVELS**: Lists the allowed difficulty levels.

## Notes
- Common Errors:
   - If an invalid level is provided, the program will display the list of allowed levels.
   - If the provided seed is not numeric, an error will prompt the user to correct it.
- Improvement Suggestions:
   - Expand the difficulty levels.
   - Implement graphics or a visual environment for the game.

Enjoy playing Space Invaders!
