package logic.fields;

import logic.DoubleVector;
import logic.particles.Particle;

/**
 * Author: Yuri Buyanov
 * Date: 31/05/2021 01:10
 */
public class BoxFieldWithGravity extends BoxField{
    public BoxFieldWithGravity(int width, int height) {
        super(width, height);
    }

    public DoubleVector getAcceleration(Particle particle) {
        return new DoubleVector(0, 0.1);
    }
}
