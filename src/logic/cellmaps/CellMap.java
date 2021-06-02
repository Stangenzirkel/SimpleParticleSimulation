package logic.cellmaps;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CellMap {
    double [][] map;
    int height, width;
    double max, min;
    boolean haveMax = false, haveMin = false;

    final double EMPTY = 123456789;
    double EMPTYReplacer = 0;

    public CellMap(int width, int height) {
        this.width = width;
        this.height = height;

        map = new double[height][width];
        fillEmpty();
    }

    public void fill(double value) {
        for (int y = 0; y < map.length; y++) {
            Arrays.fill(map[y], value);
        }
        haveMax = false;
        haveMin = false;
    }

    public void fillEmpty() {
        for (int y = 0; y < map.length; y++) {
            Arrays.fill(map[y], EMPTY);
        }
        haveMax = false;
        haveMin = false;
    }

    public void setValue(double value, int x, int y) {
        map[y][x] = value;
        haveMax = false;
        haveMin = false;

    }

    public void addToValue(double value, int x, int y) {
        setValue(value + getValue(x, y), x, y);
        haveMax = false;
        haveMin = false;
    }

    public double getValue(int x, int y) {
        return isEmpty(x, y) ? EMPTYReplacer : map[y][x];
    }

    public boolean isEmpty(int x, int y) {
        return Math.abs(map[y][x] - EMPTY) < 1;
    }

    public void setEmpty(int x, int y) {
        setValue(EMPTY, x, y);
        haveMax = false;
        haveMin = false;
    }

    public void setEMPTYReplacer(double EMPTYReplacer) {
        this.EMPTYReplacer = EMPTYReplacer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getMax() {
//        if (haveMax)
//            return max;

        double max = Double.MIN_VALUE;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!isEmpty(x, y) && max < getValue(x, y)) {
                    max = getValue(x, y);
                }
            }
        }

        haveMax = true;
        return max;
    }

    public double getMin() {
//        if (haveMin)
//            return min;

        double min = Double.MAX_VALUE;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!isEmpty(x, y) && min > getValue(x, y)) {
                    min = getValue(x, y);
                }
            }
        }

        haveMin = true;
        return min;
    }

    public double getMean() {
        double out = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                out += map[y][x];
            }
        }

        return out / map.length;
    }

    public void forEach(Function<Double, Double> function) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                map[y][x] = function.apply(getValue(x, y));
            }
        }
        haveMax = false;
        haveMin = false;
    }

    public void forEach(BiFunction<Double, Double, Double> function, CellMap other) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x] = function.apply(map[y][x], other.getValue(x, y));
            }
        }
        haveMax = false;
        haveMin = false;
    }

    @Override
    public String toString() {
        return "CellMap" + hashCode() + "{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }

    public void SOUT() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                System.out.print(isEmpty(x, y) ? "EMPTY" : String.format("%.3f", getValue(x, y)));
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
