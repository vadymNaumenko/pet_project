package de.pet_project.domain.enums.game;

public enum MinAge {
    EIGHT_PLUS("8+"),
    TEN_PLUS("10+"),
    TWELVE_PLUS("12+"),
    FOURTEEN("14+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+");

    public final String age;
    MinAge(String age) {
        this.age = age;
    }

}
