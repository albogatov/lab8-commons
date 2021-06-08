package commons.commands;

import commons.app.Command;
import commons.network.ResponseData;
import commons.app.User;
import commons.utils.InteractionInterface;

import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public boolean execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Boolean result = null;
        try {
            result = singleThreadPool.submit(() -> {
                try {
                    long id = Long.parseLong(argument);
                    if (interactiveStorage.findById(id))
                        if (dbc.removeWorker(id, user)) {
//                            interactiveStorage.removeById(id);
                            dbc.retrieveCollectionFromDB(interactiveStorage);
                            ResponseData.appendLine("RemoveSuccess");
//                            ui.messageToClient("Сотрудник удален", address, port);
                            return true;
                        } else {
                            ResponseData.appendLine("RemoveError");
//                            ui.messageToClient("Сотрудник с таким id не найден или вы не имеете право его редактировать", address, port);
                            return false;
                        }
                    return false;
                } catch (NumberFormatException e) {
//                    ui.messageToClient("Введен неверный аргумент команды", address, port);
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
        return result;
    }
}
