package commons.commands;

import commons.app.Command;
import commons.network.ResponseData;
import commons.utils.UserInterface;

import java.net.InetAddress;

public class Register extends Command {
    public Register() {
        cmdLine = "register";
        needsObject = false;
        argumentAmount = 2;
    }

    public boolean execute(UserInterface ui, boolean success, InetAddress clientAddress, int clientPort) {
        if (success) {
            ResponseData.appendLine("RegisterSuccess");
            return true;
        } else {
            ResponseData.appendLine("RegisterError");
            return false;
        }
    }
}
