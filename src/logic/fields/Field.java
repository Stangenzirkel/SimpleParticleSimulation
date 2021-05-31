package logic.fields;

import logic.particles.ActiveParticle;
import logic.particles.Particle;

public class Field {
    Particle[] particles = new Particle [100];
    int iteration = 0;

    public Field() {
    }

    public Particle[] getParticles() {
        return particles;
    }

    public void addRandomParticles(int n, int maxSpeed) {
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = ActiveParticle.randomPosParticle(300, 300, 305, 305, this, maxSpeed);
        }
    }

    public int getIteration() {
        return iteration;
    }

    public int getN() {
        return particles.length;
    }

    public void nextIteration() {
        iteration++;
        for (Particle particle: particles) {
            particle.move(10);
        }
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

    public int getAccelerationX(Particle particle) {
        return 0;
    }

    public int getAccelerationY(Particle particle) {
        return 0;
    }
}
