package commons.commands;

import commons.app.Command;
import commons.network.ResponseData;
import commons.utils.UserInterface;

import java.net.InetAddress;

public class Login extends Command {
    public Login() {
        cmdLine = "login";
        needsObject = false;
        argumentAmount = 2;
    }

    public boolean execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {
        if (success) {
            ResponseData.appendLine("LoginSuccess");
            return true;
        } else {
            ResponseData.appendLine("LoginError");
            return false;
        }
    }
}
