package commons.commands;

import commons.app.Command;
import commons.utils.UserInterface;

import java.net.InetAddress;

public class Login extends Command {
    public Login() {
        cmdLine = "login";
        needsObject = false;
        argumentAmount = 2;
    }

    public void execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {
        if (success)
            ui.messageToClient("Вход в систему успешен!", clientAddress, clientPort);
        else ui.messageToClient("Вход в систему не удался.", clientAddress, clientPort);
    }
}
