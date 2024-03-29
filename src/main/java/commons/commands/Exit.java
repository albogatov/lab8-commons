package commons.commands;

import commons.app.Command;
import commons.network.ResponseData;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;

/**
 * Класс команды exit.
 */
public class Exit extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Exit() {
        cmdLine = "exit";
        description = "выход из программы";
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
    public boolean execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc) {
        Thread response = new Thread(() -> {
            ResponseData.appendLine("ExitMessage");
//            ui.messageToClient("До новых встреч", address, port);
        });
        response.start();
        return true;
//        System.exit(0);
    }
}
