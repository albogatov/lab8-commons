package commons.network;

import java.io.Serializable;

public enum ResponseCode implements Serializable {
    OK,
    ERROR,
    CLIENT_EXIT,
    SERVER_EXIT
}