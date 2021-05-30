package logic.particles;

import javafx.scene.paint.Color;

import java.util.Random;

public class Particle {
    private double x, y;
    private Color color = Color.WHITE;
    private int size = 2;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Particle randomPosParticle(int maxX, int maxY) {
        return new Particle((double) new Random().nextInt(maxX), (double) new Random().nextInt(maxY));
    }
}
