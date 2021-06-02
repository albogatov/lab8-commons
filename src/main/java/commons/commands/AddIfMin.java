package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды add_if_min.
 */
public class AddIfMin extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public AddIfMin() {
        cmdLine = "add_if_min";
        description = "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.";
        needsObject = true;
        argumentAmount = 1;
        editsCollection = true;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc, User user) {
        Thread response = new Thread(() -> {
            int size1 = interactiveStorage.getSize();
            interactiveStorage.addIfMin(worker);
            int size2 = interactiveStorage.getSize();
            if ((size2 > size1) && dbc.addWorker(worker, user)) {
                ui.messageToClient("Элемент успешно добавлен", address, port);
                dbc.retrieveCollectionFromDB(interactiveStorage);
            } else
                ui.messageToClient("Элемент не добавлен, т.к. он не подходит критерию или уже содержится в базе", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
