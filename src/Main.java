import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.fields.*;
import logic.particles.Particle;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    private int windowSizeX, windowSizeY;
    private Canvas canvas;

    private int UPS = 30;
    private Timer timer;

    private Field field;
    private int maxSpeed = 30;
    private int drawMode = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("sim");
        stage.setFullScreen(true);
        windowSizeX = (int) Screen.getPrimary().getBounds().getWidth();
        windowSizeY = (int) Screen.getPrimary().getBounds().getHeight();
        stage.setResizable(false);

        Group root = new Group();
        Scene scene = new Scene(root, windowSizeX, windowSizeY);
        scene.setFill(Color.WHITE);

        canvas = new Canvas();
        canvas.setWidth(windowSizeX);
        canvas.setHeight(windowSizeY);
        canvas.setFocusTraversable(true);
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
            field.nextIteration();
            drawAll();
            } else if (keyEvent.getCode() == KeyCode.DIGIT1) {
                drawMode = 1;
                drawAll();
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                drawMode = 2;
                drawAll();
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                drawMode = 3;
                drawAll();
            } else if (keyEvent.getCode() == KeyCode.DIGIT4) {
                drawMode = 4;
                drawAll();
            }
        });

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();

        field = new BoxField(windowSizeX, windowSizeY);
        field.addRandomParticles(10000, maxSpeed);
        // update();

        for (int i = 0; i < 1000; i++) {
            field.nextIteration();
        }
        drawAll();
    }

    private void update() {
        try {
            timer.cancel();
        } catch (Exception e) {
            System.out.println(e);
        }

        field.nextIteration();
        drawAll();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 1000 / UPS);
    }

        public void fillAll(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, windowSizeX, windowSizeY);
        gc.setFill(color);
        gc.fillRect(0, 0, windowSizeX, windowSizeY);
    }

    private void drawParticles(int mod) {
        fillAll(Color.BLACK);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Particle particle: field.getParticles()) {
            gc.setFill(getColorOfParticle(particle, mod));
            gc.fillRect(particle.getX() - particle.getSize() / 2,
                        particle.getY() - particle.getSize() / 2,
                            particle.getSize(),
                            particle.getSize());
        }
    }

    private void drawAll() {
        if (drawMode == 1) {
            drawParticles(1);
        } else if (drawMode == 2){
            drawParticles(2);
        } else if (drawMode == 3) {
            drawTemperatureMap(32, 18);
        } else if (drawMode == 4) {
            drawPressureMap(32, 18);
        }
    }

    private void drawTemperatureMap(int resolutionX, int resolutionY) {
        assert field instanceof BoxField;

        fillAll(Color.BLACK);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double [][] temperatureMap = new double[resolutionY][resolutionX];
        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                temperatureMap[y][x] = 0;
            }
        }

        double [][] pressureMap = new double[resolutionY][resolutionX];
        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                pressureMap[y][x] = 0;
            }
        }

        double maxSpeed = 0, minSpeed = Double.MAX_VALUE;

        for (Particle particle: field.getParticles()) {
            if (particle.getSpeed() > maxSpeed) {
                maxSpeed = particle.getSpeed();
            }

            if (particle.getSpeed() < minSpeed) {
                minSpeed = particle.getSpeed();
            }

            // - 1 это костыль для частиц на границе
            int x = (int) (resolutionX * ((particle.getX() - 1) / field.getRightWall()));
            int y = (int) (resolutionY * ((particle.getY() - 1) / field.getFloor()));

            try {
                pressureMap[y][x]++;
                temperatureMap[y][x] = (temperatureMap[y][x] * (pressureMap[y][x] - 1) + particle.getSpeed()) / pressureMap[y][x];
            } catch (Exception e) {
                System.out.println(particle.getX());
                System.out.println(particle.getY());
            }
        }

        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                if (pressureMap[y][x] != 0) {
                    gc.setFill(Color.hsb(300 - temperatureMap[y][x] / maxSpeed * 300, 1, 1));
                    gc.fillRect(x * windowSizeX / resolutionX, y * windowSizeY / resolutionY, windowSizeX / resolutionX,
                            windowSizeY / resolutionY);
                }
            }
        }
    }

    private void drawPressureMap(int resolutionX, int resolutionY) {
        assert field instanceof BoxField;

        fillAll(Color.BLACK);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double [][] pressureMap = new double[resolutionY][resolutionX];
        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                pressureMap[y][x] = 0;
            }
        }

        for (Particle particle: field.getParticles()) {
            // - 1 это костыль для частиц на границе
            int x = (int) (resolutionX * ((particle.getX() - 1) / field.getRightWall()));
            int y = (int) (resolutionY * ((particle.getY() - 1) / field.getFloor()));

            try {
                pressureMap[y][x]++;
            } catch (Exception e) {
                System.out.println(particle.getX());
                System.out.println(particle.getY());
            }
        }

        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                if (pressureMap[y][x] != 0) {
                    gc.setFill(Color.hsb(0, 0, pressureMap[y][x] / field.getN()));
                    gc.fillRect(x * windowSizeX / resolutionX, y * windowSizeY / resolutionY, windowSizeX / resolutionX,
                            windowSizeY / resolutionY);
                }
            }
        }
    }

    private Color getColorOfParticle(Particle particle, int mod) {
        if (mod == 1) {
            return particle.getColor();
        } else if (mod == 2) {
            return Color.hsb(300 - particle.getSpeed() / maxSpeed * 300, 1, 0.5 + particle.getSpeed() / maxSpeed / 2);
        }

        return Color.WHITE;
    }
}
