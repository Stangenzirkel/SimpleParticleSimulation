package logic.particles;

import logic.DoubleVector;
import logic.PointCoordinates;
import logic.fields.Field;
import javafx.scene.paint.Color;
import java.util.Random;

public class ActiveParticle extends Particle {
    DoubleVector speed = DoubleVector.getNullVector();
    Color color = Color.RED;

    public ActiveParticle(PointCoordinates coordinates, Field field) {
        super(coordinates, field);
        target = coordinates;
    }

    @Override
    public DoubleVector getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(DoubleVector speed) {
        this.speed = speed;
    }

    public DoubleVector acceleration() {
        DoubleVector acceleration = field.getAcceleration(this);
        return acceleration;
    }

    @Override
    public void setTarget() {
        speed.add(acceleration());
        target.add(speed);
    }

    @Override
    public void move() {
        coordinates = target;
        if (coordinates.getX() <= field.getLeftWall()) {
            coordinates.setX(field.getLeftWall() + 1);
            speed.setX(Math.abs(speed.getX()));
        }

        if (coordinates.getX() >= field.getRightWall()) {
            coordinates.setX(field.getRightWall() - 1);
            speed.setX(-Math.abs(speed.getX()));
        }

        if (coordinates.getY() <= field.getRoof()) {
            coordinates.setY(field.getRoof() + 1);
            speed.setY(Math.abs(speed.getY()));
        }

        if (coordinates.getY() >= field.getFloor()) {
            coordinates.setY(field.getFloor() - 1);
            speed.setY(-Math.abs(speed.getY()));
        }
    }

    public static Particle randomPosParticle(int minX, int minY, int maxX, int maxY, Field field, int maxSpeed) {
        ActiveParticle activeParticle = new ActiveParticle(PointCoordinates.getRandom(minX, minY, maxX, maxY), field);
        activeParticle.setSpeed(DoubleVector.getRandom(maxSpeed));
        return activeParticle;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public int getMass() {
        return 5;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
