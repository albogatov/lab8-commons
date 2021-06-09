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
        ResponseData.appendLine("Empty");
        return true;
    }
}
