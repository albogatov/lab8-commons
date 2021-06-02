package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

/**
 * Класс команды remove_greater.
 */
public class RemoveGreater extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveGreater() {
        cmdLine = "remove_greater";
        description = "удалить из коллекции все элементы, превышающие заданный";
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
            List<Long> deletionIds = interactiveStorage.removeGreater(worker);
            interactiveStorage.removeGreater(worker);
            int size2 = interactiveStorage.getSize();
            if (size2 < size1) {
                for (Long deletionId : deletionIds) {
                    if (dbc.removeWorker(deletionId, user))
                        ui.messageToClient("Операция успешно выполнена", address, port);
                    else
                        ui.messageToClient("Не удалось удалить элемент", address, port);
                }
                dbc.retrieveCollectionFromDB(interactiveStorage);
            }
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
