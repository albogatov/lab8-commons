package commons.commands;

import commons.app.Command;
//import server.interaction.InteractionInterface;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.elements.Worker;
import commons.utils.DataBaseCenter;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Класс команды add.
 */
public class Add extends Command implements Serializable {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Add() {
        cmdLine = "add";
        description = "Добавить новый элемент в коллекцию.";
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
            if (dbc.addWorker(worker, user)) {
                interactiveStorage.add(worker);
                ui.messageToClient("Сотрудник успешно добавлен", address, port);
                dbc.retrieveCollectionFromDB(interactiveStorage);
            } else ui.messageToClient("Такой сотрудник уже добавлен", address, port);
            if (ui.isInteractionMode()) {
                ui.messageToClient("Awaiting further client instructions.", address, port);
            }
        });
        response.start();
    }
}
