package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.network.ResponseData;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды show
 */
public class Show extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Show() {
        cmdLine = "show";
        description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
    public boolean execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        Thread response = new Thread(() -> {
//            if (interactiveStorage.getSize() == 0)
//                ui.messageToClient("Коллекция пуста", address, port);
//            else {
//                ui.messageToClient("Коллекция: " + "\n" + interactiveStorage.show(), address, port);
//            }
//            interactiveStorage.getStorage().getCollection().stream().forEach(worker -> System.out.println(worker.displayWorker()));
//            ui.messageToClient("Collection Incoming", address, port);
//            ui.collectionToClient(interactiveStorage.getStorage().getCollection(), address, port);
//            if (ui.isInteractionMode()) {
//                ui.messageToClient("Awaiting further client instructions.", address, port);
//            }
            ResponseData.appendLine("Empty");
        });
        response.start();
        return true;
    }
}
