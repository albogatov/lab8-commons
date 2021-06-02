package commons.elements;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Перечисляемый тип OrganizationType.
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL("COMMERCIAL"),
    PRIVATE_LIMITED_COMPANY("PRIVATE_LIMITED_COMPANY"),
    OPEN_JOINT_STOCK_COMPANY("OPEN_JOINT_STOCK_COMPANY");

    private String text;
    private static final List<OrganizationType> possibleValues = Arrays.asList(OrganizationType.values());

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
    public static List<OrganizationType> getPossibleValues() {
        return possibleValues;
    }

    /**
     * Стандартный конструктор.
     *
     * @param text перечисление в виде строки.
     */
    OrganizationType(String text) {
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