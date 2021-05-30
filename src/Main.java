import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
    private final int borderSize = 30;
    private final int textZoneWidth = 350;

    private int windowSizeX, windowSizeY;

    private Canvas canvas;

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

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();

        fillAll(Color.BLACK);
        drawTextLine("txt", 0);
        drawTextLine("txt", 1);
        drawTextLine("txt", 2);
    }

    private void drawTextLine(String line, int num) {
        int TEXTSIZE = 50, TEXTINDENT = 0;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.loadFont("file:./static/19187.ttf", TEXTSIZE));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(line, windowSizeX - textZoneWidth,
                borderSize + num * (TEXTSIZE + TEXTINDENT));
    }

        public void fillAll(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, windowSizeX, windowSizeY);
    }
}
