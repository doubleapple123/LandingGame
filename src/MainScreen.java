import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class MainScreen extends Application {
    private final int SCREEN_HEIGHT = 900;
    private final int SCREEN_WIDTH = 900;


    //rect properties
    private int START_X = 200;
    private int START_Y = 100;
    private int RECT_W = 50;
    private int RECT_H = 100;
    private int FLOOR_RECT_H = 70;

    private int FLOOR_W = SCREEN_WIDTH;
    private int FLOOR_H = FLOOR_RECT_H;

    public static void main(String[] args) {
        Planet planet = new Planet();

        launch(args);
    }
  
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Something");
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);

        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(SCREEN_HEIGHT,SCREEN_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        //proof of concept boundary detection
        Rectangle floor = new Rectangle();
        floor.setWidth(FLOOR_W);
        floor.setHeight(FLOOR_H);
        floor.setFill(Color.BLUE);
        floor.setX(0);
        floor.setY(SCREEN_HEIGHT-FLOOR_H);

        Rectangle rect = new Rectangle();
        rect.setWidth(RECT_W);
        rect.setHeight(RECT_H);
        rect.setFill(Color.BLACK);
        rect.setX(START_X);
        rect.setY(START_Y);

        root.getChildren().add(rect);
        root.getChildren().add(floor);

        final long startNanoTime = System.nanoTime();


        new AnimationTimer(){
            int increment = 0;
            @Override
            public void handle(long l) {
                double t = (l - startNanoTime) / 1000000000.0;

                increment -= t;
                increment /= 10;
                rect.setY(START_Y-=increment);
                Shape intersect = Shape.intersect(rect,floor);
                if(intersect.getBoundsInLocal().getWidth() != -1){
                    root.getChildren().remove(rect);
                }
            }
        }.start();


        stage.setScene(scene);
        stage.show();
    }
}

