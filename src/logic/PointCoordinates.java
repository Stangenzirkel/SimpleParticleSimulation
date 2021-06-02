package logic;

import logic.fields.Field;
import logic.particles.Particle;

import java.util.Random;

/**
 * Author: Yuri Buyanov
 * Date: 01/06/2021 18:25
 */
public class PointCoordinates {
    private double x, y;

    public PointCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static PointCoordinates getRandom(int minX, int minY, int maxX, int maxY) {
        return new PointCoordinates(minX + new Random().nextInt(maxX - minX), minY + new Random().nextInt(maxY - minY));
    }

    public static PointCoordinates getNullPointCoordinates() {
        return new PointCoordinates(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(DoubleVector vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public void subtract(DoubleVector vector) {
        x -= vector.getX();
        y -= vector.getY();
    }

    public DoubleVector getVector(PointCoordinates other) {
        return new DoubleVector(x - other.getX(), y - other.getY());
    }

    @Override
    public String toString() {
        return "PointCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
