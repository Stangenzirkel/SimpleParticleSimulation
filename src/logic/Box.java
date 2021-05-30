package logic;

import logic.particles.Particle;

import java.util.Random;

public class Box {
    private Particle[] particles = new Particle [100];
    private int iteration = 0;

    public Box() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            particles[i] = Particle.randomPosParticle(800, 800);
        }
    }

    public Particle[] getParticles() {
        return particles;
    }

    public int getIteration() {
        return iteration;
    }
}
