package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.net.InetAddress;

/**
 * Класс команды save.
 */
public class Save extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Save() {
        cmdLine = "save";
        description = "сохранить коллекцию в файл";
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
    public void execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, User user) {
        Thread response = new Thread(() -> {
            interactiveStorage.save();
            ui.messageToClient("Коллекция сохранена в файл", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }

    public void execute(InteractionInterface interactiveStorage) {
        interactiveStorage.save();
    }
}
