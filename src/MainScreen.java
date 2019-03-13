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
import java.util.Random;

public class MainScreen extends Application {
    private final int SCREEN_HEIGHT = 900;
    private final int SCREEN_WIDTH = 900;
    private final int CENTER_X = SCREEN_WIDTH/2;
    private final int CENTER_Y = SCREEN_HEIGHT/2;
    private final double GRAVITY_CONST = 5;

    private int rect_w = 5;
    private int rect_h = 5;

    //planet properties
    private static int planetRadius;
    private static int planetMass;

    //accelerations on player
    private double totalAccel; //the total acceleration of the player towards the planet
    private double xAccel;
    private double yAccel;

    //player properties
    private double totalDist;
    private double angle; //angle of spaceship on unit the circle in degrees
    private double respectiveX; //x and y respective to the center of planet
    private double respectiveY;


    public boolean spacebar = false;
    public boolean left_arrow = false;
    public boolean right_arrow = false;
    public boolean other_key = false;


    private ArrayList<String> user_input_code = new ArrayList<>();
    private ArrayList<Image> list_of_planets = new ArrayList<>();


    public static void main(String[] args) {
        Planet planet = new Planet();
        planetRadius = planet.getSize();
        planetMass = planet.getMass();

        launch(args);
    }

    public Image getRandomPlanet(){
        Random rand = new Random();
        int pick_planet = rand.nextInt(4);

        Image planet1 = new Image("Assets/PlanetImg1.png",planetRadius*2,planetRadius*2,false,false);
        Image planet2 = new Image("Assets/PlanetImg2.png",planetRadius*2,planetRadius*2,false,false);
        Image planet3 = new Image("Assets/PlanetImg3.png",planetRadius*2,planetRadius*2,false,false);
        Image planet4 = new Image("Assets/PlanetImg4.png",planetRadius*2,planetRadius*2,false,false);
        list_of_planets.add(planet1);
        list_of_planets.add(planet2);
        list_of_planets.add(planet3);
        list_of_planets.add(planet4);

        return list_of_planets.get(pick_planet);
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

        //circle for the planet
        Circle circ = new Circle();
        circ.setRadius(planetRadius);
        circ.setCenterY(CENTER_Y);
        circ.setCenterX(CENTER_X);
        circ.setFill(Color.TRANSPARENT);

        //add planet and spaceship to screen
        root.getChildren().add(rect);
        root.getChildren().add(circ);

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

        //final long startNanoTime = System.nanoTime(); // gets current system time
        Image planet = getRandomPlanet();

        new AnimationTimer(){
            
            Player player = new Player(planetRadius);

            @Override
            public void handle(long l) {
                //double t = (l - startNanoTime) / 1000000000.0; // t is a time counter. increments by 1 every second

                gc.drawImage(planet,CENTER_X - planetRadius,CENTER_Y - planetRadius); //draws image onto the screen

                //physics

                //distance and force
                totalDist = Math.sqrt(Math.pow(CENTER_X - player.getxPos(), 2) + Math.pow(CENTER_Y - player.getyPos(), 2));
                totalAccel = GRAVITY_CONST * planetMass / Math.pow(totalDist, 2);
                respectiveX = player.getxPos() - CENTER_X;
                respectiveY = CENTER_Y - player.getyPos();

                angle = Math.toDegrees(Math.atan2(respectiveY, respectiveX));


                //acceleration update
                xAccel = -totalAccel * Math.cos(Math.toRadians(angle));
                yAccel = totalAccel * Math.sin(Math.toRadians(angle));

                //velocity update
                player.setxVel(player.getxVel() + xAccel);
                player.setyVel(player.getyVel() + yAccel);

                //calculations and drawings above this line, position changes and checks below this line

                //position update
                player.setxPos(player.getxPos() + player.getxVel());
                player.setyPos(player.getyPos() + player.getyVel());

                System.out.println("X: " + player.getxPos() + "\nY: " + player.getyPos());

                //Y_POS += 1; //y-position of rec moved down 1 every frame

                rect.setX(player.getxPos()); //sets y-pos of rectangle
                rect.setY(player.getyPos());

                //checks below this line

                Shape intersect = Shape.intersect(rect,circ);

                if(intersect.getBoundsInLocal().getWidth() != -1){ //checks for intersection between object (rect) and (circ) on previous line
                }
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }


}

