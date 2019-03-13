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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class MainScreen extends Application {
    private final int SCREEN_HEIGHT = 900;
    private final int SCREEN_WIDTH = 900;
    private final int CENTER_X = SCREEN_WIDTH/2;
    private final int CENTER_Y = SCREEN_HEIGHT/2;
    private final double GRAVITY_CONST = 5;
    private final int SHIP_HEIGHT = 70;
    private final int SHIP_WIDTH = 30;

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

    Player player = new Player(planetRadius);

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

    public void rotate(GraphicsContext gc, double angle, double px, double py){ //FUNCTION CREDITED TO (jewelsea) on stackoverflow
        // Variable px and py are the pivot points
        Rotate r = new Rotate(angle,px,py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) { //FUNCTION CREDITED TO (jewelsea) on stackoverflow
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }

    public void rotateRect(Rectangle rect, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px+SHIP_WIDTH/2, py-SHIP_HEIGHT/2);
        rect.getTransforms().add(r);
        rect.setX(player.getxPos()); //sets y-pos of rectangle
        rect.setY(player.getyPos());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Something");
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);

        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvasPLANET = new Canvas(SCREEN_HEIGHT,SCREEN_WIDTH);
        Canvas canvasSHIP = new Canvas(SCREEN_HEIGHT,SCREEN_WIDTH);

        GraphicsContext gcPLANET = canvasPLANET.getGraphicsContext2D();
        GraphicsContext gcSHIP = canvasSHIP.getGraphicsContext2D();
        root.getChildren().add(canvasPLANET);
        root.getChildren().add(canvasSHIP);

        //rectangle for spaceship
        Rectangle rect = new Rectangle();
        rect.setWidth(SHIP_WIDTH);
        rect.setHeight(SHIP_HEIGHT);
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
        Image spaceShip = new Image("Assets/RocketShip.png",SHIP_WIDTH,SHIP_HEIGHT,false,false);

        new AnimationTimer(){
            

            double rotateAmount = 0; // variable saves how much rotation
            double rectRotate = 0;

            @Override
            public void handle(long l) {
                gcSHIP.clearRect(0,0,SCREEN_HEIGHT,SCREEN_WIDTH); //clears screen every frame
                //double t = (l - startNanoTime) / 1000000000.0; // t is a time counter. increments by 1 every second

                gcPLANET.drawImage(planet,CENTER_X - planetRadius,CENTER_Y - planetRadius); //draws image onto the screen

                if(left_arrow){

                    rotateRect(rect,rectRotate,player.getxPos(),player.getyPos());
                    rectRotate = -0.8;
                    rotateAmount -= 0.8; //sets the rotation amount in degrees, left_arrow = negative
                }
                else{

                    rotateRect(rect,rectRotate,player.getxPos(),player.getyPos());
                    rectRotate = 0;
                }
                if(right_arrow){

                    rotateRect(rect,rectRotate,player.getxPos(),player.getyPos());
                    rotateAmount += 0.8; //set the amount of rotation in degrees, right_arrow = positive
                    rectRotate = 0.8;
                }
                else{

                    rotateRect(rect,rectRotate,player.getxPos(),player.getyPos());
                    rectRotate = 0;
                }
                drawRotatedImage(gcSHIP,spaceShip,rotateAmount,player.getxPos(),player.getyPos());

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

                //rect.setX(player.getxPos()); //sets y-pos of rectangle
                //rect.setY(player.getyPos());

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

