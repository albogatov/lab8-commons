package commons.app;

import commons.commands.*;
import commons.elements.Worker;
import commons.utils.ConnectionSource;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс отвечающий за распознование и вызов команд.
 */
public class CommandCenter {
    private static final Logger logger = Logger.getLogger(
            ConnectionSource.class.getName());
    private static InetAddress clientAddress;
    private static int clientPort;
    private final ReentrantReadWriteLock collectionLock = new ReentrantReadWriteLock();
    /**
     * Объект центра управления командами.
     */
    public static volatile CommandCenter commandCenter;
    /**
     * Список всех возможных команд.
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    /**
     * Конструктор центра комманд, где добавляются все возможные команды.
     */
    private CommandCenter() {
        addCmd(new Add());
        addCmd(new Help());
        addCmd(new AddIfMin());
        addCmd(new Info());
        addCmd(new Show());
        addCmd(new Save());
        addCmd(new Update());
        addCmd(new RemoveById());
        addCmd(new Clear());
        addCmd(new ExecuteScript());
        addCmd(new Exit());
        addCmd(new RemoveGreater());
        addCmd(new RemoveLower());
        addCmd(new CountByStatus());
        addCmd(new PrintAscending());
        addCmd(new PrintUniqueOrganization());
    }

    /**
     * Метод передачи команды в конструктор.
     *
     * @param cmd Команда.
     */
    public void addCmd(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    /**
     * Метод, распознающий команду в строке, введенной пользователем.
     *
     * @param cmdLine Строка, содержащая команду.
     * @return Объект класса соответсвующей команды.
     */
    public Command getCmd(String cmdLine) {
        return commands.getOrDefault(cmdLine, null);
    }

    /**
     * Метод, возвращающий единственный объект класса. Реализация шаблона "Синглтон".
     *
     * @return Объект центра управления командами.
     */
    public static CommandCenter getInstance() {
        if (commandCenter == null) {
            synchronized (CommandCenter.class) {
                if (commandCenter == null) {
                    commandCenter = new CommandCenter();
                }
            }
        }
        return commandCenter;
    }


    /**
     * Метод, возврашающий полный список всех команд.
     *
     * @return Список команд.
     */
    public List<Command> retrieveAllCommands() {
        return commands.keySet().stream().map(commands::get).collect(Collectors.toList());
    }

    /**
     * Метод, вызывающий исполнение команды.
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public boolean executeCommand(UserInterface ui, Command cmd, InteractionInterface interactiveStorage) {
        logger.log(Level.INFO, "Executing server command initiated by user's actions");
        return cmd.execute(ui, interactiveStorage, clientAddress, clientPort, cmd.getUser());
    }

    public boolean executeCommand(UserInterface ui, Command cmd, InteractionInterface interactiveStorage, DataBaseCenter dataBaseCenter) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing user command with no arguments");
        boolean result = cmd.execute(ui, interactiveStorage, clientAddress, clientPort, dataBaseCenter, cmd.getUser());
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public boolean executeCommand(UserInterface ui, Command cmd, String argument, InteractionInterface interactiveStorage, DataBaseCenter dataBaseCenter) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing user command with a string argument");
        boolean result = cmd.execute(ui, argument, interactiveStorage, clientAddress, clientPort, dataBaseCenter, cmd.getUser());
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public boolean executeCommand(UserInterface ui, Command cmd, InteractionInterface interactiveStorage, Worker worker, DataBaseCenter dbc) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing user command with an object argument");
        boolean result = cmd.execute(ui, interactiveStorage, worker, clientAddress, clientPort, dbc, cmd.getUser());
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public boolean executeCommand(UserInterface ui, Command cmd, String argument, InteractionInterface interactiveStorage, Worker worker, DataBaseCenter dbc) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing user command with two arguments");
        boolean result = cmd.execute(ui, argument, interactiveStorage, worker, clientAddress, clientPort, dbc, cmd.getUser());
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public boolean executeCommand(UserInterface ui, Command cmd, boolean success) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing user command with two string arguments");
        boolean result = cmd.execute(ui, success, clientAddress, clientPort);
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public boolean executeServerCommand(Command cmd, InteractionInterface interactiveStorage) {
        if (!cmd.isEditsCollection()) {
            collectionLock.readLock().lock();
        } else collectionLock.writeLock().lock();
        logger.log(Level.INFO, "Executing server command");
        boolean result = cmd.execute(interactiveStorage);
        if (collectionLock.isWriteLocked())
            collectionLock.writeLock().unlock();
        collectionLock.readLock().unlock();
        return result;
    }

    public static void setClientAddress(InetAddress address) {
        logger.log(Level.INFO, "Tying client's address to the command center");
        clientAddress = address;
    }

    public static void setClientPort(int port) {
        logger.log(Level.INFO, "Tying client's port to the command center");
        clientPort = port;
    }

    public static InetAddress getClientAddress() {
        return clientAddress;
    }

    public static int getClientPort() {
        return clientPort;
    }
}
