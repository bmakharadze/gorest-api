package gorest.util;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String get() {
        return gender;
    }
}
