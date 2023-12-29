package de.pet_project.domain.enums.game;

public enum NumberOfPlayers {

    ONE("1"),
    ONE_TWO("1-2"),
    ONE_FOUR("1-4"),
    ONE_EIGHT("1-8"),
    TWO_TEN("2-10");

    public final String number;
    NumberOfPlayers(String number) {
        this.number = number;
    }
}
