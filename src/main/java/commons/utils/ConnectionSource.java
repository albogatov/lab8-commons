package commons.utils;

import commons.app.Command;
import commons.app.Request;
import commons.app.User;

import java.net.SocketTimeoutException;

public interface ConnectionSource {

    void setArguments(String[] arguments);

    Request receive() throws SocketTimeoutException;

    void processRequest(Request request);

    boolean authoriseUser(User user, String existence);


}
