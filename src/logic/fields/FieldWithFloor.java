package logic.fields;

import logic.particles.ActiveParticle;
import logic.particles.Particle;

public class FieldWithFloor extends Field{
    public FieldWithFloor() {
        super();
    }

    public void addRandomParticles(int n) {
        for (int i = 0; i < n; i++) {
            particles[i] = ActiveParticle.randomPosParticle(800, 800, this);
        }
    }

    public int getFloor() {
        return 880;
    }
}
