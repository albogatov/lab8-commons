package commons.commands;

import commons.app.Command;
import commons.utils.UserInterface;

import java.net.InetAddress;

public class Register extends Command {
    public Register() {
        cmdLine = "register";
        needsObject = false;
        argumentAmount = 2;
    }

    public void execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {
        if (success)
            ui.messageToClient("Регистрация успешна!", clientAddress, clientPort);
        else ui.messageToClient("Регистрация не удалась.", clientAddress, clientPort);
    }
}
