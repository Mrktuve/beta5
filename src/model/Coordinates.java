package model;

/**
 * Класс Coordinates
 */
public class Coordinates {

    private Double x; // > -26
    private float y;

    public Coordinates(Double x, float y) {
        if (x == null || x <= -26)
            throw new IllegalArgumentException("x должен быть > -26");

        this.x = x;
        this.y = y;
    }
}