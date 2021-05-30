package logic.fields;

import logic.particles.ActiveParticle;

public class BoxField extends Field{
    public BoxField() {
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

    public int getRoof() {
        return 20;
    }

    public int getLeftWall() {
        return 20;
    }

    public int getRightWall() {
        return 880;
    }
}
