package papadom.commands;

import papadom.exceptions.NoTaskNumberException;
import papadom.exceptions.WrongTaskNumberException;
import papadom.storage.Storage;
import papadom.storage.TaskList;
import papadom.Ui;

/**
 * Represents a command to delete an event task from the task list.
 */
public class DeleteEventCommand extends Command {
    private final String TEXT;
    /**
     * Constructs a DeleteEventCommand with the specified text input.
     *
     * @param text The input string that specifies which event to delete.
     */
    public DeleteEventCommand(String text) {
        this.TEXT = text;
    }

    /**
     * Executes the command to delete an event task from the task list.
     *
     * @param taskList The list of tasks.
     * @param ui The user interface for outputting messages.
     * @param storage The storage system for saving the task list.
     * @throws WrongTaskNumberException If the task number is invalid.
     * @throws NoTaskNumberException If no task number is provided.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws WrongTaskNumberException, NoTaskNumberException {
        ui.output(taskList.deleteEvent(TEXT));
    }
}
