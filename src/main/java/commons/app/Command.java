package commons.app;

import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Абстрактный класс, от которого наследуются все команды.
 */
public abstract class Command implements Serializable {
    /**
     * Поле, содержащее строку для вызова команды.
     */
    protected String cmdLine;
    /**
     * Поле, содержащее описание команды.
     */
    protected String description;
    protected boolean needsObject;
    protected int argumentAmount;
    protected String argument;
    protected User user;
    protected Worker object;
    protected boolean editsCollection;

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, User user) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {

    }

    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {

    }

    public void execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc, User user) {

    }

    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc, User user) {

    }

    public void execute(InteractionInterface interactionInterface) {

    }

    public void execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {

    }

    /**
     * Стандартный конструктор.
     */
    public Command() {

    }

    /**
     * Возвращает строку, вызывающую команду.
     *
     * @return Строка вызова команды.
     */
    public String getCommand() {
        return cmdLine;
    }

    /**
     * Возвращает описание команды.
     *
     * @return Описание команды.
     */
    public String getHelp() {
        return description;
    }

    public boolean getNeedsObject() {
        return needsObject;
    }

    public int getArgumentAmount() {
        return argumentAmount;
    }

    public void setObject(Worker object) {
        this.object = object;
    }

    public Worker getObject() {
        return this.object;
    }

    public void setArgument(String arg) {
        this.argument = arg;
    }

    public String getArgument() {
        return this.argument;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public boolean isEditsCollection() {
        return this.editsCollection;
    }
}
