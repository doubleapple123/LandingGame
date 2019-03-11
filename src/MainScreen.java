import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    private int CIRC_RADIUS = 100;

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

        //rectangle for spaceship
        Rectangle rect = new Rectangle();
        rect.setWidth(RECT_W);
        rect.setHeight(RECT_H);
        rect.setFill(Color.BLACK);
        rect.setX(START_X+105);
        rect.setY(START_Y);

        Circle circ = new Circle();
        circ.setRadius(CIRC_RADIUS);
        circ.setCenterY(SCREEN_HEIGHT/2);
        circ.setCenterX(SCREEN_WIDTH/2);
        circ.setFill(Color.TRANSPARENT);

        Image planet = new Image("PlanetImg1.png",200,200,false,false);

        root.getChildren().add(rect);
        root.getChildren().add(circ);

        //final long startNanoTime = System.nanoTime();

        new AnimationTimer(){
            int Y_POS = START_Y;

            @Override
            public void handle(long l) {
                //double t = (l - startNanoTime) / 1000000000.0;
                gc.drawImage(planet,SCREEN_WIDTH/2-100,SCREEN_HEIGHT/2-100);
                Y_POS += 1;
                rect.setY(Y_POS);
                Shape intersect = Shape.intersect(rect,circ);
                if(intersect.getBoundsInLocal().getWidth() != -1){
                    Y_POS = START_Y;
                }
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }
}