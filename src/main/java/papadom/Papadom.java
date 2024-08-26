package papadom;

import papadom.Exceptions.*;
import papadom.Storage.Storage;
import papadom.Storage.TaskList;
import papadom.commands.*;

import java.util.Scanner;
/**
 * Main class for the Papadom chatbot.
 * This class handles user input and executes the corresponding commands.
 */
public class Papadom {
    /**
     * Enum representing the different types of commands the chatbot can recognize.
     */
    enum CommandType {
        LIST, BYE, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;
        /**
         * Converts a string command to a corresponding CommandType enum.
         *
         * @param command The string representation of the command.
         * @return The CommandType enum corresponding to the command.
         */
        public static CommandType fromString(String command) {
            return switch (command.toLowerCase()) {
                case "list" -> LIST;
                case "bye" -> BYE;
                case "mark" -> MARK;
                case "unmark" -> UNMARK;
                case "todo" -> TODO;
                case "deadline" -> DEADLINE;
                case "event" -> EVENT;
                case "delete" -> DELETE;
                default -> UNKNOWN;
            };
        }
    }
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage("./src/main/java/papadom/Storage/tasks.txt");
    private static final Parser parser = new Parser();
    private static final TaskList taskList = new TaskList(storage);
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Runs the Papadom chatbot, handling user input and executing commands in a loop.
     */
    private static void run() {
        ui.welcomeMessage();
        storage.createFileIfNotPresent();

        while (true) {
            Command command = null;
            try {
                String text = scanner.nextLine();
                String commandText = text.split(" ")[0];
                CommandType commandType = CommandType.fromString(commandText);
                switch (commandType) {
                    case LIST:
                        command = new ListCommand();
                        break;
                    case BYE:
                        command = new ExitCommand();
                        break;
                    case MARK:
                        command = new MarkCommand(text);
                        break;
                    case UNMARK:
                        command = new UnmarkCommand(text);
                        break;
                    case TODO:
                        command = new AddTodoCommand(text);
                        break;
                    case DEADLINE:
                        command = new AddDeadlineCommand(text);
                        break;
                    case EVENT:
                        command = new AddEventCommand(text);
                        break;
                    case DELETE:
                        command = new DeleteEventCommand(text);
                        break;
                    default:
                        throw new UnknownCommandException();
                }
                command.execute(taskList, ui, storage);
                if (command instanceof ExitCommand) {
                    return;
                }
            } catch (Exception e) {
                ui.output(e.getMessage());
            }
        }
    }
    /**
     * Main entry point for the Papadom application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Papadom.run();
    }
}
