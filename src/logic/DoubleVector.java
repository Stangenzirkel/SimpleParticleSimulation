package logic;

import java.util.Random;
import java.util.Vector;

/**
 * Author: Yuri Buyanov
 * Date: 01/06/2021 17:56
 */
public class DoubleVector {
    private double x, y;

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static DoubleVector getNullVector() {
        return new DoubleVector(0, 0);
    }

    public static DoubleVector getRandom(int maxValue) {
        double direction = new Random().nextDouble() * Math.PI * 2;
        double speed = maxValue - new Random().nextDouble() * 2 * maxValue;
        double x = Math.cos(direction) * speed;
        double y = Math.sin(direction) * speed;
        return new DoubleVector(x, y);
    }

    public double getValue() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getDirection() {
        return Math.atan(y / x);
    }

    public void add(DoubleVector other) {
        x += other.getX();
        y += other.getY();
    }

    public void subtract(DoubleVector other) {
        x -= other.getX();
        y -= other.getY();
    }

    public void multiply(double a) {
        x *= a;
        y *= a;
    }

    public void divide(double a) {
        x /= a;
        y /= a;
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

    @Override
    public String toString() {
        return "DoubleVector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
