package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды clear.
 */
public class Clear extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Clear() {
        cmdLine = "clear";
        description = "очистить коллекцию";
        needsObject = false;
        argumentAmount = 0;
        editsCollection = true;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        Thread response = new Thread(() -> {
            if (!(dbc.clearCollection(user)))
                ui.messageToClient("Что-то пошло не так, попробуйте еще раз", address, port);
            else {
                interactiveStorage.clear();
                ui.messageToClient("Коллекция очищена", address, port);
                dbc.retrieveCollectionFromDB(interactiveStorage);
            }
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
