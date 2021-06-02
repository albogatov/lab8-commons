package commons.utils;

import commons.elements.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, реализующий взаимодействие с пользователем.
 */
public class UserInterface {
    private static final Logger logger = Logger.getLogger(
            ConnectionSource.class.getName());
    private final Console console;
    /**
     * Сканнер.
     */
    private final Scanner scanner;
    /**
     * Куда идет запись.
     */
    private final Writer writer;
    /**
     * Режим взаимодействия.
     */
    private final boolean interactionMode;
    private DatagramSocket datagramSocket;

    /**
     * Стандартный конструктор.
     *
     * @param r  откуда считывать.
     * @param w  куда записывать.
     * @param im режим взаимодействия (true - интерактивный).
     */
    public UserInterface(Reader r, Writer w, boolean im) {
        this.writer = w;
        this.interactionMode = im;
        this.scanner = new Scanner(r);
        this.console = System.console();
    }

    /**
     * Метод, считывающий введенную пользователем строку.
     *
     * @return введенная строка.
     */
    public String read() {
        return scanner.nextLine();
    }

    /**
     * Метод, проверяющий наличие несчитанных строк.
     *
     * @return true, если они есть, иначе false.
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Метод, пишущий сообщение на вывод.
     *
     * @param message сообшение.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public void write(String message) throws IOException {
        writer.write(message);
        writer.flush();
    }

    /**
     * Метод, демонстрирующий сообщение пользователю.
     *
     * @param message сообщение.
     */
    public void displayMessage(String message) {
        try {
            write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, принимающий от пользователя необходимый для ввода аргумент.
     *
     * @param message сообщение для пользователя.
     * @return введенный аргумент.
     */
    public String readUnlimitedArgument(String message, boolean nullable) {
        String line = null;
        if (!nullable) {
            if (interactionMode) {
                while (line == null) {
                    displayMessage("Ввод данного поля не может быть пустым");
                    displayMessage(message);
                    line = scanner.nextLine();
                    line = line.isEmpty() ? null : line;
                }
            } else {
                line = scanner.nextLine();
                line = line.isEmpty() ? null : line;
            }
            if (!interactionMode && line == null)
                throw new InvalidParameterException("Скрипт содержит некорректный ввод данных");
        } else {
            if (interactionMode) {
                displayMessage(message);
            }
            line = scanner.nextLine();
            line = line.isEmpty() ? null : line;
        }
        return line;
    }

    /**
     * Метод, принимающий от пользователя необходимый для ввода численный и ограниченный условиями аргумент
     *
     * @param message сообщение для пользователя.
     * @param min     минимальное значение.
     * @param max     максимальное значение.
     * @return введенный аргумент.
     */
    public String readLimitedArgument(String message, long min, long max, boolean nullable) throws InterruptedIOException {
        String line = null;
        if (!nullable) {
            if (interactionMode) {
                try {
                    while (line == null || Long.parseLong(line) < min || Long.parseLong(line) > max) {
                        displayMessage("Ввод данного поля не может быть пустым и должен быть в указанном диапазоне: [" + min + ":" + max + "]");
                        displayMessage(message);
                        line = scanner.nextLine();
                        line = line.isEmpty() ? null : line;
                    }
                } catch (NumberFormatException e) {
                    throw new InterruptedIOException();
                }
            } else {
                line = scanner.nextLine();
                line = line.isEmpty() ? null : line;
                try {
                    Long.parseLong(line);
                } catch (NumberFormatException e) {
                    throw new InvalidParameterException("Скрипт содержит некорректный ввод данных");
                }
                if (line == null || Long.parseLong(line) < min || Long.parseLong(line) > max) {
                    throw new InvalidParameterException("Скрипт содержит некорректный ввод данных");
                }
            }
        } else {
            if (interactionMode) {
                do {
                    displayMessage(message);
                    line = scanner.nextLine();
                    if (line.isEmpty())
                        break;
                } while (Long.parseLong(line) < min || Long.parseLong(line) > max);
                line = line.isEmpty() ? null : line;
            } else {
                line = scanner.nextLine();
                if (Long.parseLong(line) < min || Long.parseLong(line) > max) {
                    throw new InvalidParameterException("Скрипт содержит некорректный ввод данных");
                }
            }
        }
        return line;
    }

//    public String readPassword() {
//        String line = null;
//        while (line == null) {
//            displayMessage("Пожалуйста, введите пароль.");
//            line = new String(console.readPassword());
//            line = line.isEmpty() ? null : line;
//        }
//        return line;
//    }

    /**
     * Метод, считывающий сотрудника (объект коллекции) из строки.
     *
     * @return объект коллекции.
     */
    public Worker readWorker(UserInterface ui) {
//        boolean check = false;
        String name;
        do {
            name = readUnlimitedArgument("Введите имя рабочего:", false);
        }
        while (!ValueVerificationTool.verifyName(name, interactionMode, ui));
//        ZonedDateTime creationDate = ZonedDateTime.now();
        String salaryString;
        do {
            try {
                salaryString = readLimitedArgument("Введите оклад рабочего:", 1, Integer.MAX_VALUE, false);
            } catch (InterruptedIOException e) {
                salaryString = null;
            }
        } while (!ValueVerificationTool.verifySalary(salaryString, interactionMode, ui));
        Integer salary = Integer.parseInt(salaryString);
        String endDateLine;
        LocalDate endDate;
        do {
            endDateLine = readUnlimitedArgument("Введите дату расторжения контракта (при наличии) в формате (YYYY-MM-DD):", true);
        } while (!ValueVerificationTool.verifyDate(endDateLine, interactionMode, ui));
        if (endDateLine == null)
            endDate = null;
        else endDate = LocalDate.parse(endDateLine, DateTimeFormatter.ISO_LOCAL_DATE);
        String xLine;
        String yLine;
        do {
            try {
                xLine = readLimitedArgument("Введите x координату сотрудника:", Integer.MIN_VALUE, 627, false);
            } catch (InterruptedIOException e) {
                xLine = null;
            }
        } while (!ValueVerificationTool.verifyIntValue(xLine, interactionMode, ui, false));
        do {
            try {
                yLine = readLimitedArgument("Введите y координату сотрудника:", Long.MIN_VALUE, 990, false);
            } catch (InterruptedIOException e) {
                yLine = null;
            }
        } while (!ValueVerificationTool.verifyLongValue(xLine, interactionMode, ui, false));
        int x = Integer.parseInt(xLine);
        long y = Long.parseLong(yLine);
        Coordinates coordinates = new Coordinates(x, y);
        Status status;
        String statusLine;
        do {
            statusLine = readUnlimitedArgument("Введите статус сотрудника, возможны значения: " + Status.getPossibleValues(), true);
        } while (!ValueVerificationTool.verifyStatus(statusLine, interactionMode, ui));
        if (statusLine == null)
            status = null;
        else status = Status.valueOf(statusLine.toUpperCase());
        Position position;
        String positionLine;
        do {
            positionLine = readUnlimitedArgument("Введите должность сотрудника, возможны значения: " + Position.getPossibleValues(), true);
        } while (!ValueVerificationTool.verifyPosition(positionLine, interactionMode, ui));
        if (positionLine == null)
            position = null;
        else position = Position.valueOf(positionLine.toUpperCase());
        String orgName = readUnlimitedArgument("Укажите организацию сотрудника:", true);
        if (orgName != null)
            orgName = orgName.toUpperCase();
        Organization organization;
        String annualTurnoverLine;
        Long annualTurnover;
        do {
            try {
                annualTurnoverLine = readLimitedArgument("Введите годовую выручку компании:", 1, Long.MAX_VALUE, true);
            } catch (InterruptedIOException e) {
                annualTurnoverLine = null;
            }
        } while (!ValueVerificationTool.verifyLongValue(annualTurnoverLine, interactionMode, ui, true));
        if (annualTurnoverLine == null)
            annualTurnover = null;
        else annualTurnover = Long.parseLong(annualTurnoverLine);
        OrganizationType type;
        String orgTypeLine;
        do {
            orgTypeLine = readUnlimitedArgument("Введите тип организации, возможны значения: " + OrganizationType.getPossibleValues(), true);
        } while (!ValueVerificationTool.verifyOrgType(orgTypeLine, interactionMode, ui));
        if (orgTypeLine == null)
            type = null;
        else type = OrganizationType.valueOf(orgTypeLine.toUpperCase());
        Address postalAddress;
        String street = readUnlimitedArgument("Укажите улицу расположения организации:", false);
        String zipCode = readUnlimitedArgument("Укажите почтовый индекс:", false);
        postalAddress = new Address(street, zipCode);
        organization = new Organization(annualTurnover, type, postalAddress, orgName);
        return new Worker(name, coordinates, salary, endDate, position, status, organization);
    }

    public void connectToServer(DatagramSocket datagramSocket) {
        logger.log(Level.INFO, "Connecting user interface");
        this.datagramSocket = datagramSocket;
    }

    public DatagramSocket getConnection() {
        logger.log(Level.INFO, "Retrieving socket used by the UI");
        return datagramSocket;
    }

    public void messageToClient(String message, InetAddress address, int port) {
        logger.log(Level.INFO, "Starting to send an answer to the client");
        try {
            byte[] toBeSent = SerializationTool.serializeObject(message);
            DatagramPacket out = null;
            if (toBeSent != null) {
                out = new DatagramPacket(toBeSent, toBeSent.length, address, port);
            }
            logger.log(Level.INFO, "Sending answer");
            datagramSocket.send(out);
        } catch (IOException e) {
            logger.log(Level.INFO, "Answer sending failed");
            e.printStackTrace();
        }
    }

    public boolean isInteractionMode() {
        return interactionMode;
    }
}
