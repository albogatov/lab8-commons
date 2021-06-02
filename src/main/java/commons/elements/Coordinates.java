package commons.elements;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Coordinates.
 */
public class Coordinates implements Serializable {
    /**
     * Поле, содержащее координату X. Максимальное значение - 627.
     */
    private final int x;
    /**
     * Поле, содержащее координату Y. Максимальное значение - 990.
     */
    private final long y;

    /**
     * Стандартный конструктор.
     *
     * @param x Координата x.
     * @param y Координата y.
     */
    public Coordinates(int x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Переопределенный метод toString для корректного строкового представления координат.
     *
     * @return Координаты в строковом виде.
     */
    @Override
    public String toString() {
        String textX = String.valueOf(x);
        String textY = String.valueOf(y);
        return textX + " " + textY;
    }

    /**
     * Метод, возвращающий отдельно координату X.
     *
     * @return Координата X.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Метод, возвращающий отдельно координату X.
     *
     * @return Координата Y.
     */
    public long getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        if (this == obj)
            return true;
        Coordinates other = (Coordinates) obj;
        return Objects.equals(x, other.getX()) && Objects.equals(y, other.getY());
    }
}
