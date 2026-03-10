package models;

import enums.Color;
import enums.Country;

/**
 * Информация о человеке.
 */
public class Person {
    private String passportID; // Строка не может быть пустой, Поле может быть null
    private Color eyeColor; // Поле может быть null
    private Color hairColor; // Поле не может быть null
    private Country nationality; // Поле может быть null

    /**
     * Создаёт Person.
     * @param passportID ID паспорта (может быть null, но не пустая строка)
     * @param eyeColor цвет глаз (может быть null)
     * @param hairColor цвет волос (не null)
     * @param nationality национальность (может быть null)
     * @throws IllegalArgumentException если hairColor null или passportID пустая
     */
    public Person(String passportID, Color eyeColor, Color hairColor, Country nationality) {
        if (passportID != null && passportID.isEmpty()) {
            throw new IllegalArgumentException("passportID не может быть пустой строкой");
        }
        if (hairColor == null) {
            throw new IllegalArgumentException("hairColor не может быть null");
        }
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    // Getters
    public String getPassportID() { return passportID; }
    public Color getEyeColor() { return eyeColor; }
    public Color getHairColor() { return hairColor; }
    public Country getNationality() { return nationality; }

    @Override
    public String toString() {
        return "Person{" +
                "passportID='" + passportID + '\'' +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                '}';
    }
}