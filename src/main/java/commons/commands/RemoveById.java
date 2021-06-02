package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.utils.InteractionInterface;

import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды remove_by_id.
 */
public class RemoveById extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveById() {
        cmdLine = "remove_by_id";
        description = "удалить элемент из коллекции по его id";
        needsObject = false;
        argumentAmount = 1;
        editsCollection = true;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param argument           необходимый для исполнения аргумент.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public void execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        Thread response = new Thread(() -> {
            try {
                long id = Long.parseLong(argument);
                if (interactiveStorage.findById(id))
                    if (interactiveStorage.findById(id) && dbc.removeWorker(id, user)) {
                        interactiveStorage.removeById(id);
                        dbc.retrieveCollectionFromDB(interactiveStorage);
                        ui.messageToClient("Сотрудник удален", address, port);
                    } else
                        ui.messageToClient("Сотрудник с таким id не найден или вы не имеете право его редактировать", address, port);
                if (ui.isInteractionMode()) {
                    ui.messageToClient("Awaiting further client instructions.", address, port);
                }
            } catch (NumberFormatException e) {
                ui.messageToClient("Введен неверный аргумент команды", address, port);
                if (ui.isInteractionMode()) {
                    ui.messageToClient("Awaiting further client instructions.", address, port);
                }
            }
        });
        response.start();
    }
}
