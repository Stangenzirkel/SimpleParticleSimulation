package logic.fields;

import logic.particles.Particle;

/**
 * Author: Yuri Buyanov
 * Date: 31/05/2021 01:10
 */
public class BoxFieldWithGravity extends BoxField{
    public BoxFieldWithGravity(int width, int height) {
        super(width, height);
    }

    public int getAccelerationX(Particle particle) {
        return 0;
    }

    public int getAccelerationY(Particle particle) {
        return 10;
    }
}
