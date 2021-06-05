package commons.app;

import commons.elements.Worker;

import java.io.Serializable;
import java.util.HashSet;

public class Response implements Serializable {
    private HashSet<Worker> workers;
    private ResponseCode responseCode;
    private String responseBody;
    private String[] responseBodyArgs;

    public Response() {

    }

    public Response(ResponseCode responseCode,
                    HashSet<Worker> workers) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.workers = workers;
        this.responseBodyArgs = responseBodyArgs;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String[] getResponseBodyArgs() {
        return responseBodyArgs;
    }

    public HashSet<Worker> getCollection() {
        return workers;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + "]";
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseBodyArgs(String[] args) {
        this.responseBodyArgs = args;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setWorkers(HashSet<Worker> workers) {
        this.workers = workers;
    }
}