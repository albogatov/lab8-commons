package commons.elements;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Перечисляемый тип Position.
 */
public enum Position implements Serializable {
    LABORER("LABORER"),
    ENGINEER("ENGINEER"),
    CLEANER("CLEANER");


    private String text;
    private static final List<Position> possibleValues = Arrays.asList(Position.values());

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
    public static List<Position> getPossibleValues() {
        return possibleValues;
    }

    /**
     * Стандартный конструктор.
     *
     * @param text перечисление в виде строки.
     */
    Position(String text) {
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

