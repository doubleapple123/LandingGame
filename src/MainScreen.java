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

import java.util.ArrayList;

public class MainScreen extends Application {
    private final int SCREEN_HEIGHT = 900;
    private final int SCREEN_WIDTH = 900;

    //rect properties
    private int startX = 200;
    private int startY = 100;

    private int rect_w = 50;
    private int rect_h = 100;

    private int circ_radius = 100;

    private boolean spacebar = false;
    private boolean left_arrow = false;
    private boolean right_arrow = false;
    private ArrayList<String> user_input_code = new ArrayList<>();

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
        rect.setWidth(rect_w);
        rect.setHeight(rect_h);
        rect.setFill(Color.BLACK);
        rect.setX(startX +105);
        rect.setY(startY);

        //circle for the planet
        Circle circ = new Circle();
        circ.setRadius(circ_radius);
        circ.setCenterY(SCREEN_HEIGHT/2);
        circ.setCenterX(SCREEN_WIDTH/2);
        circ.setFill(Color.TRANSPARENT);

        Image planet = new Image("PlanetImg1.png",200,200,false,false);

        //add planet and spaceship to screen
        root.getChildren().add(rect);
        root.getChildren().add(circ);

        //final long startNanoTime = System.nanoTime();

        //user Inputs
        scene.setOnKeyPressed(e -> {
           String code = e.getCode().toString();
           if(!user_input_code.contains(code));{
               user_input_code.add(code);
            }
        });

        scene.setOnKeyReleased(e ->  {
            String code = e.getCode().toString();
            user_input_code.remove(code);
        });

        new AnimationTimer(){
            int Y_POS = startY;

            @Override
            public void handle(long l) {
                //double t = (l - startNanoTime) / 1000000000.0;
                gc.drawImage(planet,SCREEN_WIDTH/2-100,SCREEN_HEIGHT/2-100); //draws image onto the screen

                Y_POS += 1; //y-position of rec moved down 1 every frame
                rect.setY(Y_POS); //sets y-pos of rectangle

                //user input set-up
                if(user_input_code.contains("SPACE")){
                    System.out.println("SPACEBAR");
                    spacebar = true;
                }
                if(user_input_code.contains("LEFT")){
                    System.out.println("LEFT");
                    left_arrow = true;
                }
                if(user_input_code.contains("RIGHT")){
                    System.out.println("RIGHT");
                    right_arrow = true;
                }

                Shape intersect = Shape.intersect(rect,circ);
                if(intersect.getBoundsInLocal().getWidth() != -1){ //checks for intersection between object (rect) and (circ) on previous line
                    Y_POS = startY;
                }
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }
}

