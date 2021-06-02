package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды print_ascending.
 */
public class PrintAscending extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public PrintAscending() {
        cmdLine = "print_ascending";
        description = "вывести элементы коллекции в порядке возрастания";
        needsObject = false;
        argumentAmount = 0;
        editsCollection = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        Thread response = new Thread(() -> {
            ui.messageToClient(interactiveStorage.printAscending(), address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
