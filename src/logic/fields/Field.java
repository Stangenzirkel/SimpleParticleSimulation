package logic.fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.DoubleVector;
import logic.PointCoordinates;
import logic.cellmaps.CellMap;
import logic.particles.ActiveParticle;
import logic.particles.Particle;

import java.util.Arrays;
import java.util.function.BiFunction;

public class Field {
    Particle[] particles = new Particle [100];
    int iteration = 0;
    double maxSpeed = 0, minSpeed = Double.MAX_VALUE;

    public Field() {
    }

    public Particle[] getParticles() {
        return particles;
    }

    public void addRandomParticles(int n, int minX, int minY, int maxX, int maxY, int maxSpeed) {
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = ActiveParticle.randomPosParticle(minX, minY, maxX, maxY, this, maxSpeed);
        }
    }

    public void nextIteration() {
        iteration++;
        for (Particle particle: particles) {
            particle.setTarget();
        }

        for (Particle particle: particles) {
            particle.move();
            if (particle.getSpeed().getValue() < minSpeed) {
                minSpeed = particle.getSpeed().getValue();
            }

            if (particle.getSpeed().getValue() > maxSpeed) {
                maxSpeed = particle.getSpeed().getValue();
            }
        }
    }

    public int getIteration() {
        return iteration;
    }

    public int getN() {
        return particles.length;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMeanSpeed() {
        return Arrays.stream(particles).mapToDouble(x -> x.getSpeed().getValue()).reduce(0, Double::sum) / getN();
    }

    public int getFloor() {
        return -1;
    }

    public int getRoof() {
        return -1;
    }

    public int getLeftWall() {
        return -1;
    }

    public int getRightWall() {
        return -1;
    }

    public DoubleVector getAcceleration(Particle particle) {
        return DoubleVector.getNullVector();
    }

    public CellMap getPressureMap(PointCoordinates NWPoint, PointCoordinates SEPoint, int resolutionX, int resolutionY) {
        CellMap pressureMap = new CellMap(resolutionX, resolutionY);
        pressureMap.fill(0);
        double areaSizeX = SEPoint.getX() - NWPoint.getX(), areaSizeY = SEPoint.getY() - NWPoint.getY();

        for (Particle particle: particles) {
            if (particle.getX() >= NWPoint.getX() && particle.getX() < SEPoint.getX() &&
                    particle.getY() >= NWPoint.getY() && particle.getY() < SEPoint.getY()) {
                int x = (int) ((particle.getX() - NWPoint.getX()) / areaSizeX * resolutionX);
                int y = (int) ((particle.getY() - NWPoint.getY()) / areaSizeY * resolutionY);
                try {
                    pressureMap.addToValue(1, x, y);
                } catch (Exception e) {
                    System.out.println(x);
                    System.out.println(y);
                    System.out.println(particle.getX());
                    System.out.println(particle.getY());
                    System.out.println();
                }

            }
        }
        return pressureMap;
    }

    public CellMap getTemperatureMap(PointCoordinates NWPoint, PointCoordinates SEPoint, int resolutionX, int resolutionY) {
        CellMap pressureMap = new CellMap(resolutionX, resolutionY);
        pressureMap.fill(0);
        CellMap temperatureMap = new CellMap(resolutionX, resolutionY);
        double areaSizeX = SEPoint.getX() - NWPoint.getX(), areaSizeY = SEPoint.getY() - NWPoint.getY();

        for (Particle particle: particles) {
            if (particle.getX() >= NWPoint.getX() && particle.getX() < SEPoint.getX() &&
                    particle.getY() >= NWPoint.getY() && particle.getY() < SEPoint.getY()) {
                int x = (int) ((particle.getX() - NWPoint.getX()) / areaSizeX * resolutionX);
                int y = (int) ((particle.getY() - NWPoint.getY()) / areaSizeY * resolutionY);
                try {
                    pressureMap.addToValue(1, x, y);
                    temperatureMap.addToValue(particle.getSpeed().getValue(), x, y);
                } catch (Exception e) {
                    System.out.println(x);
                    System.out.println(y);
                    System.out.println(particle.getX());
                    System.out.println(particle.getY());
                    System.out.println();
                }

            }
        }

        temperatureMap.forEach(new BiFunction<Double, Double, Double>() {
            @Override
            public Double apply(Double aDouble, Double aDouble2) {
                return aDouble2 != 0 ? aDouble / aDouble2 : aDouble;
            }
        }, pressureMap);
        return temperatureMap;
    }
}
