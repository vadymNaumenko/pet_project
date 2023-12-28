package de.pet_project.domain.enums.game;

public enum Genre {
        RPG("RPG"), ARCADE("Arcade"), PUZZLE("Puzzle"), CHILDREN_S_GAME("Children's game"),
        INDIE("Indie"), CASUAL("Casual"), MUSICAL("Musical"), ADVENTURES("Adventures"),
        SIMULATORS("Simulators"), SLOW_MO("Slow Mo"), SPORTS("Sports"), SHOOTING("Shooting"),
        HORROR("Horror"), SHOOTER("Shooter"), EXTREME("Extreme"), ACTION("Action"), QUEST("Quest");

        public final String genre;
        Genre(String genre) {
                this.genre = genre;
        }
}
