package models;

/**
 * Координаты рабочего места.
 */
public class Coordinates {
    private Double x; // Значение поля должно быть больше -26, Поле не может быть null
    private float y;

    /**
     * Создаёт координаты.
     * @param x координата X (> -26, не null)
     * @param y координата Y
     * @throws IllegalArgumentException если x null или <= -26
     */
    public Coordinates(Double x, float y) {
        if (x == null || x <= -26) {
            throw new IllegalArgumentException("x должен быть > -26 и не null");
        }
        this.x = x;
        this.y = y;
    }

    public Double getX() { return x; }
    public float getY() { return y; }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + "}";
    }
}