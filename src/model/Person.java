package model;

/**
 * Класс Person
 */
public class Person {

    private String passportID;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;

    public Person(String passportID, Color eyeColor,
                  Color hairColor, Country nationality) {

        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }
}