package papadom;

import papadom.Exceptions.*;
import papadom.Storage.Storage;
import papadom.Storage.TaskList;
import papadom.commands.*;

import java.util.Scanner;

public class Papadom {
    enum Command {
        LIST, BYE, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;
        public static Command fromString(String command) {
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
    private static final Storage storage = new Storage("src/main/java/papadom.Storage.papadom.Storage/tasks.txt");
    private static final Parser parser = new Parser();
    private static final TaskList taskList = new TaskList();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ui.welcomeMessage();
        storage.createFileIfNotPresent();

        while (true) {
            try {
                String text = scanner.nextLine();
                String commandText = text.split(" ")[0];
                Command command = Command.fromString(commandText);
                switch (command) {
                case LIST:
                    ListCommand listCommand = new ListCommand();
                    listCommand.execute(taskList, ui, storage);
                    break;
                case BYE:
                    ExitCommand exitCommand = new ExitCommand();
                    exitCommand.execute(taskList, ui, storage);
                    return;
                case MARK:
                    MarkCommand markCommand = new MarkCommand(text);
                    markCommand.execute(taskList, ui, storage);
                    break;
                case UNMARK:
                    UnmarkCommand unmarkCommand = new UnmarkCommand(text);
                    unmarkCommand.execute(taskList, ui, storage);
                    break;
                case TODO:
                    AddTodoCommand addTodoCommand = new AddTodoCommand(text);
                    addTodoCommand.execute(taskList, ui, storage);
                    break;
                case DEADLINE:
                    AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(text);
                    addDeadlineCommand.execute(taskList, ui, storage);
                    break;
                case EVENT:
                    AddEventCommand addEventCommand = new AddEventCommand(text);
                    addEventCommand.execute(taskList, ui, storage);
                    break;
                case DELETE:
                    DeleteEventCommand deleteEventCommand = new DeleteEventCommand(text);
                    deleteEventCommand.execute(taskList, ui, storage);
                    break;
                default:
                    throw new UnknownCommandException();
                }
            } catch (Exception e) {
                ui.output(e.getMessage());
            }
        }
    }
}
