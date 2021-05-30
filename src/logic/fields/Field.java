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

    public void addRandomParticles(int n) {
        for (int i = 0; i < n; i++) {
            particles[i] = ActiveParticle.randomPosParticle(780, 800, this);
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
}
