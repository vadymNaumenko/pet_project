package de.pet_project.domain.enums;

public enum State {
    PENDING("Pending"),//В ОЖИДАНИИ,
    ACTIVE("Active"),//АКТИВНЫЙ,
    COMPLETED("Completed");//ЗАВЕРШЕННЫЙ

    public final String state;
    State(String state) {
        this.state = state;
    }
}
