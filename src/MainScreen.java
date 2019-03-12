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
    private final int CENTER_X = SCREEN_WIDTH/2;
    private final int CENTER_Y = SCREEN_HEIGHT/2;

    //rect properties
    private int startX = 200;
    private int startY = 100;

    private int rect_w = 50;
    private int rect_h = 100;

    private static int planetRadius;
    private static int planetMass;

    private boolean spacebar = false;
    private boolean left_arrow = false;
    private boolean right_arrow = false;
    private boolean other_key = false;
    private ArrayList<String> user_input_code = new ArrayList<>();

    public static void main(String[] args) {
        Planet planet = new Planet();

        planetRadius = planet.getSize();
        planetMass = planet.getMass();

        Player player = new Player(planetRadius);

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
        circ.setRadius(planetRadius);
        circ.setCenterY(CENTER_Y);
        circ.setCenterX(CENTER_X);
        circ.setFill(Color.TRANSPARENT);

        Image planet = new Image("PlanetImg1.png",planetRadius*2,planetRadius*2,false,false);

        //add planet and spaceship to screen
        root.getChildren().add(rect);
        root.getChildren().add(circ);

        //final long startNanoTime = System.nanoTime();

        //user Inputs
        scene.setOnKeyPressed(e -> {
           String code = e.getCode().toString();
           if(!user_input_code.contains(code));{
               user_input_code.add(code);

                if(code.equals("SPACE")){
                    spacebar = true;
                }
                else if(code.equals("RIGHT")){
                    right_arrow = true;
                }
                else if(code.equals("LEFT")){
                    left_arrow = true;
                }
                else{
                    other_key = true;
                }
            }
        });

        scene.setOnKeyReleased(e ->  {
            String code = e.getCode().toString();
            user_input_code.remove(code);

            if(code.equals("SPACE")){
                spacebar = false;
            }
            else if(code.equals("RIGHT")){
                right_arrow = false;
            }
            else if(code.equals("LEFT")){
                left_arrow = false;
            }
            else{
                other_key = false;
            }

        });

        new AnimationTimer(){
            int Y_POS = startY;

            @Override
            public void handle(long l) {
                //double t = (l - startNanoTime) / 1000000000.0;
                gc.drawImage(planet,CENTER_X - planetRadius,CENTER_Y - planetRadius); //draws image onto the screen

                Y_POS += 1; //y-position of rec moved down 1 every frame
                rect.setY(Y_POS); //sets y-pos of rectangle

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

