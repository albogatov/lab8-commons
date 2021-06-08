package commons.commands;

import commons.app.Command;
//import server.interaction.InteractionInterface;
import commons.network.ResponseData;
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
//                    interactiveStorage.add(worker);
                    dbc.retrieveCollectionFromDB(interactiveStorage);
                    ResponseData.appendLine("AddSuccess");
                    System.out.println(interactiveStorage.getStorage().getCollection() + " DATA FROM CMD");
//                    ui.messageToClient("Сотрудник успешно добавлен", address, port);
//                    ui.collectionToClient(interactiveStorage.getStorage().getCollection(), address, port);
                    System.out.println("executed pool");
                    return true;
                } else {
                    dbc.retrieveCollectionFromDB(interactiveStorage);
                    ResponseData.appendLine("AddError");
//                    ui.messageToClient("Ошибка при добавлении сотрудника", address, port);
                    System.out.println("executed pool");
                    return false;
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            ResponseData.appendLine("CommandError");
            result = false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            ResponseData.appendLine("CommandError");
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
