package fr.coding.utils;

public class Player {
    private final String name;
    private final long points;
    private final String time;
    private final boolean resolved;

    public Player(String name, long points, String time, boolean resolved) {
        this.name = name;
        this.points = points;
        this.time = time;
        this.resolved = resolved;
    }

    public String getName() {
        return name;
    }

    public long getPoints() {
        return points;
    }

    public String getTime() {
        return time;
    }

    public boolean isResolved() {
        return resolved;
    }
}
