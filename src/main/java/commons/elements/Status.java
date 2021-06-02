package commons.elements;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Перечисляемый тип Status.
 */
public enum Status implements Serializable {
    FIRED("FIRED"),
    HIRED("HIRED"),
    RECOMMENDED_FOR_PROMOTION("RECOMMENDED_FOR_PROMOTION"),
    REGULAR("REGULAR"),
    PROBATION("PROBATION");

    private String text;
    private static final List<Status> possibleValues = Arrays.asList(Status.values());

    /**
     * Метод для задания параметров перечисления.
     *
     * @param text перечисление в виде строки.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Метод, возвращающий возможные для ввода значения перечисления.
     *
     * @return список значений.
     */
    public static List<Status> getPossibleValues() {
        return possibleValues;
    }

    /**
     * Стандартный конструктор.
     *
     * @param text перечисление в виде строки.
     */
    Status(String text) {
        setText(text);
    }

    /**
     * Переопределенный метод, возвращающий возможное для ввода значение.
     *
     * @return текст.
     */
    @Override
    public String toString() {
        return this.text;
    }
}