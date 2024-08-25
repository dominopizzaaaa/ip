package papadom.commands;
import papadom.Storage.*;
import papadom.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws Exception;
    public abstract boolean isExit();
}
