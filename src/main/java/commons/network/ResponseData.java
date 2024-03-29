package commons.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseData {
    private static StringBuilder stringBuilder = new StringBuilder();
    private static List<String> argList = new ArrayList<>();

    public static void append(Object toOut) {
        stringBuilder.append(toOut);
    }

    public static void appendLine() {
        stringBuilder.append("\n");
    }

    public static void appendLine(Object toOut) {
        stringBuilder.append(toOut + "\n");
    }

    public static void appendArgs(String... args) {
        argList.addAll(Arrays.asList(args));
    }

    public static String getAndClear() {
        String toReturn = stringBuilder.toString().trim();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }

    public static String[] getArgsAndClear() {
        String[] argsAsArray = new String[argList.size()];
        argsAsArray = argList.toArray(argsAsArray);
        argList.clear();
        return argsAsArray;
    }
}
