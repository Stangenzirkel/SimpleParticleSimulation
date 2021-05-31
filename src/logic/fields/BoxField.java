package logic.fields;

public class  BoxField extends Field{
    int width;
    int height;

    public BoxField(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public int getFloor() {
        return height;
    }

    public int getRoof() {
        return 0;
    }

    public int getLeftWall() {
        return 0;
    }

    public int getRightWall() {
        return width;
    }
}
