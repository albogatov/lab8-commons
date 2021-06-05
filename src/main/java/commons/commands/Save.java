package commons.commands;

import commons.app.Command;
import commons.app.ResponseData;
import commons.app.User;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public boolean execute(UserInterface ui, InteractionInterface interactiveStorage, InetAddress address, int port, User user) {
        final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Boolean result = null;
        try {
            result = singleThreadPool.submit(() -> {
                interactiveStorage.save();
                ResponseData.appendln("Коллекция сохранена в файл");
//                ui.messageToClient("Коллекция сохранена в файл", address, port);
                return true;
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
//            interactiveStorage.save();
//            ui.messageToClient("Коллекция сохранена в файл", address, port);
//            if (ui.isInteractionMode()) {
//                ui.messageToClient("Awaiting further client instructions.", address, port);
//            }
//        });
//        response.start();
    }

    public boolean execute(InteractionInterface interactiveStorage) {
        interactiveStorage.save();
        return true;
    }
}
