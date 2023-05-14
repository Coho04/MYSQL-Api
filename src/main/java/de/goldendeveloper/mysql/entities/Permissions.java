package de.goldendeveloper.mysql.entities;

@SuppressWarnings("unused")
public enum Permissions {
    ALL(0),
    DELETE(1),
    INSERT(2),
    REFERENCES(3),
    SELECT(4),
    TRIGGER(5),
    UPDATE(6),
    EXECUTE(7);

    private final int value;

    Permissions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}