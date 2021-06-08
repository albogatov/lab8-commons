package commons.utils;

import commons.network.Request;
import commons.app.User;

import java.net.SocketTimeoutException;

public interface ConnectionSource {

    void setArguments(String[] arguments);

    Request receive() throws SocketTimeoutException;

    boolean processRequest(Request request);

    boolean authoriseUser(User user, String existence);


}
