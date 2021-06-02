package commons.commands;

import commons.app.Command;
import commons.app.CommandCenter;
import commons.app.User;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.io.*;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Класс команды execute_script.
 */
public class ExecuteScript extends Command {
    /**
     * Сет, содержащий пути ко всем скриптам вызванным на разных уровнях.
     */
    private static final HashSet<String> paths = new HashSet<>();
    /**
     * Переменная, отображающая результат выполнения скрипта.
     */
    private static boolean success;

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public ExecuteScript() {
        cmdLine = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
        needsObject = false;
        argumentAmount = 1;
        editsCollection = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param argument           необходимый для исполнения аргумент.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        try {
            paths.add(argument);
            UserInterface scriptInteraction = new UserInterface(new FileReader(argument), new OutputStreamWriter(System.out), false);
            scriptInteraction.connectToServer(ui.getConnection());
            success = true;
            String line;
            while (scriptInteraction.hasNextLine()) {
                line = scriptInteraction.read().trim();
                String[] args = line.split("\\s+");
                if (args[0].equals("save")) {
                    scriptInteraction.messageToClient("Данная команда недоступна пользователю", address, port);
                } else {
                    Command cmd = CommandCenter.getInstance().getCmd(args[0]);
                    if (cmd.getClass().toString().contains(".Login") || cmd.getClass().toString().contains(".Register"))
                        throw new InvalidParameterException();
                    cmd.setUser(user);
                    if (!(cmd == null)) {
                        if (cmd.getArgumentAmount() == 0) {
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, interactiveStorage, dbc);
                        }
                        if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                            Worker worker = scriptInteraction.readWorker(scriptInteraction);
                            cmd.setObject(worker);
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, interactiveStorage, worker, dbc);
                        }
                        if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                            if (!paths.contains(args[1]))
                                paths.add(args[1]);
                            else throw new InvalidAlgorithmParameterException();
                            cmd.setArgument(args[1]);
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, args[1], interactiveStorage, dbc);
                        }
                        if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                            try {
                                cmd.setArgument(args[1]);
                                Worker worker = scriptInteraction.readWorker(scriptInteraction);
                                cmd.setObject(worker);
                                CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, args[1], interactiveStorage, worker, dbc);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                scriptInteraction.messageToClient("Неверно указан аргумент команды", address, port);
                            }
                        }
                    } else scriptInteraction.messageToClient("Введена несуществующая команда", address, port);
                }
            }
            paths.clear();
            if (success) {
                ui.messageToClient("Скрипт выполнен", address, port);
                ui.messageToClient("Awaiting further client instructions.", address, port);
            } else {
                ui.messageToClient("Скрипт не выполнен", address, port);
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        } catch (InvalidParameterException e) {
            ui.messageToClient("Неверный скрипт", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        } catch (FileNotFoundException e) {
            ui.messageToClient("В качестве аргумента указан путь к несуществуюшему файлу", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();

        } catch (NoSuchElementException e) {
            ui.messageToClient("Скрипт некорректен, проверьте верность введенных команд", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        } catch (InvalidAlgorithmParameterException e) {
            ui.messageToClient("Выполнение скрипта остановлено, т.к. возможна рекурсия", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
            success = false;
            paths.clear();
        }
    }
}

