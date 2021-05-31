package logic.particles;

import logic.fields.Field;
import javafx.scene.paint.Color;
import java.util.Random;

public class ActiveParticle extends Particle {
    double speedX = 0, speedY = 0;
    Color color = Color.RED;

    public ActiveParticle(double x, double y, Field field) {
        super(x, y, field);
    }

    public double getDirection() {
        return Math.atan(speedY / speedX);
    }

    @Override
    public double getSpeed() {
        return Math.sqrt(speedX * speedX + speedY * speedY);
    }

    @Override
    public double getSpeedX() {
        return speedX;
    }

    @Override
    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double accelerationX() {
        double acceleration = field.getAccelerationX(this);
        return acceleration;
    }

    public double accelerationY() {
        double acceleration = field.getAccelerationY(this);
        return acceleration;
    }

    @Override
    public void move(int iterationsPerSecond) {
        speedX += accelerationX() / iterationsPerSecond;
        speedY += accelerationY() / iterationsPerSecond;
        x += speedX / iterationsPerSecond;
        y += speedY / iterationsPerSecond;

        if (x <= field.getLeftWall()) {
            x = field.getLeftWall();
            speedX = -speedX;
        }

        if (x >= field.getRightWall()) {
            x = field.getRightWall();
            speedX = -speedX;
        }

        if (y <= field.getRoof()) {
            y = field.getRoof();
            speedY = -speedY;
        }

        if (y >= field.getFloor()) {
            y = field.getFloor();
            speedY = -speedY;
        }
    }

    public static Particle randomPosParticle(int maxX, int maxY, Field field, int maxSpeed) {
        ActiveParticle activeParticle = new ActiveParticle(new Random().nextInt(maxX), new Random().nextInt(maxY), field);
        double direction = new Random().nextDouble() * Math.PI * 2;
        double speed = maxSpeed - new Random().nextDouble() * 2 * maxSpeed;
        activeParticle.setSpeedX(Math.cos(direction) * speed);
        activeParticle.setSpeedY(Math.sin(direction) * speed);
        return activeParticle;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public int getMass() {
        return 0;
    }

    @Override
    public int getSize() {
        return 4;
    }
}
