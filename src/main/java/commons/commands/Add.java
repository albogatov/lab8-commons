package commons.commands;

import commons.app.Command;
//import server.interaction.InteractionInterface;
import commons.app.ResponseData;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.elements.Worker;
import commons.utils.DataBaseCenter;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public boolean execute(UserInterface ui, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc, User user) {
        final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Boolean result = null;
        try {
            result = singleThreadPool.submit(() -> {
                if (dbc.addWorker(worker, user)) {
                    worker.setUsername(user.getLogin());
                    interactiveStorage.add(worker);
                    ResponseData.append("Сотрудник успешно добавлен");
//                    ui.messageToClient("Сотрудник успешно добавлен", address, port);
                    dbc.retrieveCollectionFromDB(interactiveStorage);
//                    ui.collectionToClient(interactiveStorage.getStorage().getCollection(), address, port);
                    return true;
                } else {
                    ResponseData.appendln("Ошибка при добавлении сотрудника");
//                    ui.messageToClient("Ошибка при добавлении сотрудника", address, port);
                    return false;
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result = false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            result = false;
        }
//        Thread response = new Thread(() -> {
//
////            if (ui.isInteractionMode()) {
////                ui.messageToClient("Awaiting further client instructions.", address, port);
////            }
//        });
//
//        response.start();
        return result;
    }
}
