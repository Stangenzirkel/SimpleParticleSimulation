package logic.particles;

import javafx.scene.paint.Color;
import logic.fields.Field;

import java.util.Random;

public class Particle {
    double x, y;
    Field field;

    public double getSpeed() {
        return 0;
    }

    public double getSpeedX() {
        return 0;
    }

    public double getSpeedY() {
        return 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public int getSize() {
        return 2;
    }

    public int getMass() {
        return 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Particle(double x, double y, Field field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }

    public static Particle randomPosParticle(int maxX, int maxY, Field field) {
        return new Particle(new Random().nextInt(maxX), new Random().nextInt(maxY), field);
    }

    public double getDistance (Particle particle) {
        return Math.sqrt(Math.abs(getX() - particle.getX()) * Math.abs(getX() - particle.getX())
                + Math.abs(getY() - particle.getY()) * Math.abs(getY() - particle.getY()));
    }

    public void move(int iterationsPerSecond) {}
}
