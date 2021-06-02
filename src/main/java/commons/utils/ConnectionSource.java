package commons.utils;

import commons.app.Command;
import commons.app.User;

import java.net.SocketTimeoutException;

public interface ConnectionSource {

    void setArguments(String[] arguments);

    Command receive() throws SocketTimeoutException;

    void processRequest(Command command);

    boolean authoriseUser(User user, String existence);


}
