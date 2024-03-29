package commons.commands;

import commons.app.Command;
import commons.network.ResponseData;
import commons.app.User;
import commons.elements.Status;
import commons.utils.InteractionInterface;
import commons.utils.UserInterface;
import commons.utils.DataBaseCenter;

import java.net.InetAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс команды count_by_status.
 */
public class CountByStatus extends Command {
    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public CountByStatus() {
        cmdLine = "count_by_status";
        description = "вывести количество элементов, значение поля status которых равно заданному";
        needsObject = false;
        argumentAmount = 1;
        editsCollection = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui                 объект, через который ведется взаимодействие с пользователем
     * @param argument           необходимый для исполнения аргумент
     * @param interactiveStorage объект для взаимодействия с коллекцией
     */
    public boolean execute(UserInterface ui, String argument, InteractionInterface interactiveStorage, InetAddress address, int port, DataBaseCenter dbc, User user) {
        final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        Boolean result = null;
        try {
            result = singleThreadPool.submit(() -> {
                try {
                    Status status = Status.valueOf(argument.toUpperCase());
                    long resultOperation = interactiveStorage.countByStatus(status);
                    ResponseData.appendLine("CountByStatusMessage");
                    ResponseData.appendArgs("" + resultOperation);
//                    ui.messageToClient("Элементов с таким статусом: " + resultOperation, address, port);
                    return true;
                } catch (IllegalArgumentException e) {
//                    ui.messageToClient("Неверный аргумент команды", address, port);
                    ResponseData.appendLine("CommandError");
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
