package commons.commands;

import commons.app.Command;
import commons.app.ResponseData;
import commons.app.User;
import commons.elements.Worker;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс команды update
 */
public class Update extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Update() {
        cmdLine = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному";
        needsObject = true;
        argumentAmount = 2;
        editsCollection = true;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем.
     * @param argument           необходимый для исполнения аргумент.
     * @param interactiveStorage объект для взаимодействия с коллекцией.
     */
    public boolean execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, Worker worker, InetAddress address, int port, DataBaseCenter dbc, User user) {
        final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Boolean result = null;
        try {
            result = singleThreadPool.submit(() -> {
                long id = Long.parseLong(argument);
                if (interactiveStorage.findById(id) && dbc.updateWorker(worker, id, user)) {
                    interactiveStorage.update(id, worker);
                    dbc.retrieveCollectionFromDB(interactiveStorage);
                    ResponseData.appendln("Сотрудник обновлен");
//                    ui.messageToClient("Сотрудник обновлен", address, port);
                    return true;
                } else {
                    ResponseData.appendln("Сотрудника с таким идентификатором нет");
//                    ui.messageToClient("Сотрудника с таким идентификатором нет", address, port);
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
        return result;
//        Thread response = new Thread(() -> {
//            long id = Long.parseLong(argument);
//            if (interactiveStorage.findById(id) && dbc.updateWorker(worker, id, user)) {
//                interactiveStorage.update(id, worker);
//                dbc.retrieveCollectionFromDB(interactiveStorage);
//                ui.messageToClient("Сотрудник обновлен", address, port);
//            } else ui.messageToClient("Сотрудника с таким идентификатором нет", address, port);
//            if (ui.isInteractionMode()) {
//                ui.messageToClient("Awaiting further client instructions.", address, port);
//            }
//        });
//        response.start();
    }
}
