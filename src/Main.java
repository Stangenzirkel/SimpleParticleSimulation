import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.PointCoordinates;
import logic.cellmaps.CellMap;
import logic.fields.*;
import logic.particles.Particle;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    private int windowSizeX, windowSizeY;
    private Canvas canvas;

    private final int FPS = 30;
    private final int UPS = 150;
    private Timer UPSTimer;
    private Timer FPSTimer;

    private Field field;
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

        field = new Field(windowSizeX, windowSizeY);
        field.addRandomParticles(10, 0, 0, 30, 30, 1);
        updateWithTimer();
        drawWithTimer();
    }

    private void updateWithTimer() {
        try {
            UPSTimer.cancel();
        } catch (Exception e) {
            System.out.println(e);
        }

        field.nextIteration();
        UPSTimer = new Timer();
        UPSTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateWithTimer();
            }
        }, 1000 / UPS);
        // System.out.println(field.getMeanSpeed());
    }

    private void drawWithTimer() {
        try {
            FPSTimer.cancel();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        drawAll();
        FPSTimer = new Timer();
        FPSTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                drawWithTimer();
            }
        }, 1000 / FPS);
        // System.out.println(field.getMeanSpeed());
    }


    private void drawAll() {
        clearAll(Color.BLACK);
        Platform.runLater(()->{
            if (drawMode == 1) {
                drawParticlesFromType();
            } else if (drawMode == 2){
                drawParticlesFromSpeed();
            } else if (drawMode == 3) {
                drawTemperatureMap(16, 9);
            } else if (drawMode == 4) {
                drawPressureMap(16, 9);
            }
        });
    }

    private void drawIteration() {
        for (int i = 0; i < 1000; i++) {
            field.nextIteration();
        }
        drawAll();
    }

    
    public void clearAll(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, windowSizeX, windowSizeY);
        gc.setFill(color);
        gc.fillRect(0, 0, windowSizeX, windowSizeY);
    }

    private void drawParticlesFromType() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Particle particle: field.getParticles()) {
            gc.setFill(getColorOfParticle(particle, 1));
            gc.fillRect(particle.getX() - particle.getSize() / 2,
                        particle.getY() - particle.getSize() / 2,
                            particle.getSize(),
                            particle.getSize());
        }
    }

    private void drawParticlesFromSpeed() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Particle particle: field.getParticles()) {
            gc.setFill(getColorOfParticle(particle, 2));
            gc.fillRect(particle.getX() - particle.getSize() / 2,
                    particle.getY() - particle.getSize() / 2,
                    particle.getSize(),
                    particle.getSize());
        }
    }

    private Color getColorOfParticle(Particle particle, int mod) {
        if (mod == 1) {
            return particle.getColor();
        } else if (mod == 2) {
            return Color.hsb(300 - (particle.getSpeed().getValue() - field.getMinSpeed()) / (field.getMaxSpeed() - field.getMinSpeed()) * 300, 1, 1);
        }

        return Color.WHITE;
    }

    private void drawTemperatureMap(int resolutionX, int resolutionY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        CellMap map = field.getTemperatureMap(PointCoordinates.getNullPointCoordinates(),
                new PointCoordinates(windowSizeX, windowSizeY),
                resolutionX, resolutionY);

        map.setEMPTYReplacer(999);

        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                if (!map.isEmpty(x, y)) {
                    gc.setFill(Color.hsb(300 - (map.getValue(x, y) - map.getMin())/ (map.getMax() - map.getMin()) * 300, 1, 1));
                    gc.fillRect(x * windowSizeX / resolutionX, y * windowSizeY / resolutionY, windowSizeX / resolutionX,
                            windowSizeY / resolutionY);
                }
            }
        }
        // map.SOUT();
    }

    private void drawPressureMap(int resolutionX, int resolutionY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        CellMap map = field.getPressureMap(PointCoordinates.getNullPointCoordinates(),
                new PointCoordinates(windowSizeX, windowSizeY),
                resolutionX, resolutionY);

        for (int y = 0; y < resolutionY; y++) {
            for (int x = 0; x < resolutionX; x++) {
                if (!map.isEmpty(x, y)) {
                    gc.setFill(Color.hsb(0, 0, map.getValue(x, y) / map.getMax()));
                    gc.fillRect(x * windowSizeX / resolutionX, y * windowSizeY / resolutionY, windowSizeX / resolutionX,
                            windowSizeY / resolutionY);
                }
            }
        }
    }
}
