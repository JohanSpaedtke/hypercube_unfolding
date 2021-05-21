package se.spaedtke;

import java.util.Objects;
import java.util.stream.IntStream;

public final class Point {
    private final int x, y, z;

    private Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point from(int[] coordinates) {
        return new Point(coordinates[0], coordinates[1], coordinates[2]);
    }

    public static Point from(int x) {
        return new Point(x, 0, 0);
    }

    public static Point from(int x, int y) {
        return new Point(x, y, 0);
    }

    public static Point from(int x, int y, int z) {
        return new Point(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public IntStream toIntStream() {
        return IntStream.of(x, y, z);
    }

    public Point translate(int[] delta) {
        return new Point(x + delta[0], y + delta[1], z + delta[2]);
    }
}
