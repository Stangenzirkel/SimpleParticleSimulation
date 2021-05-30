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

    public int accelerationX() {
        return field.getAccelerationX(this);
    }

    public int accelerationY() {
        return field.getAccelerationY(this);
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

    public static Particle randomPosParticle(int maxX, int maxY, Field field) {
        ActiveParticle activeParticle = new ActiveParticle(new Random().nextInt(maxX), new Random().nextInt(maxY), field);
        activeParticle.setSpeedX(20 - new Random().nextInt(40));
        activeParticle.setSpeedY(20 - new Random().nextInt(40));
        return activeParticle;
    }

    @Override
    public Color getColor() {
        if (Math.abs(getSpeed()) / 150 > 1) {
            System.out.println(getSpeedX());
            System.out.println(getSpeedY());
            System.out.println(getSpeed());
        }
        return Color.rgb((int) (255 * Math.abs(getSpeed()) / 150), 0, 255 - (int) (255 * Math.abs(getSpeed()) / 150));
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
