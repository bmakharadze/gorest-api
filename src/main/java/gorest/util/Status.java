package gorest.util;

public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String get() {
        return status;
    }
}
