package logic.particles;

import javafx.scene.paint.Color;
import logic.DoubleVector;
import logic.PointCoordinates;
import logic.fields.Field;

public class Particle {
    PointCoordinates coordinates;
    PointCoordinates target;
    Field field;

    public DoubleVector getSpeed() {
        return DoubleVector.getNullVector();
    }

    public PointCoordinates getCoordinates() {
        return coordinates;
    }

    public PointCoordinates getTarget() {
        return target;
    }

    public double getX() {
        return coordinates.getX();
    }

    public double getY() {
        return coordinates.getY();
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
                "x=" + coordinates.getX() +
                ", y=" + coordinates.getY() +
                ", speed=" + getSpeed() +
                '}';
    }

    public Particle(PointCoordinates coordinates, Field field) {
        this.coordinates = coordinates;
        this.field = field;
    }

    public static Particle randomPosParticle(int minX, int minY, int maxX, int maxY, Field field) {
        return new Particle(PointCoordinates.getRandom(minX, minY, maxX, maxY), field);
    }

    public double getDistance (Particle particle) {
        return coordinates.getVector(particle.getCoordinates()).getValue();
    }

    public void move() {}

    public void setTarget() {
    }

    public void setSpeed(DoubleVector speed) {}
}
