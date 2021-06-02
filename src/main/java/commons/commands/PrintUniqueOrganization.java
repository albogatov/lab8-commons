package commons.commands;

import commons.app.Command;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.List;

/**
 * Класс команды print_unique_organization.
 */
public class PrintUniqueOrganization extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public PrintUniqueOrganization() {
        cmdLine = "print_unique_organization";
        description = "вывести уникальные значения поля organization всех элементов в коллекции";
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
            List<String> result = interactiveStorage.printUniqueOrganization();
            StringBuilder display = new StringBuilder();
            result.forEach(display::append);
            ui.messageToClient(display.toString(), address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
