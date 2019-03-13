import java.util.Random;

public class Player {

    private final int SCREEN_HEIGHT = 900;
    private final int SCREEN_WIDTH = 900;
    private final int CENTER_X = SCREEN_WIDTH/2;
    private final int CENTER_Y = SCREEN_HEIGHT/2;

    private final double SPAWN_HEIGHT = 70;
    private final double STARTING_VELOCITY = 2;
    private final double ROTATION_ACCEL = 1;
    private final double THRUST = 2;

    private double dir; //direction facing, measured in degrees (0 being right, 90 up, 180 left, 270 down)
    private double rotVel; //rotational velocity (Degrees per frame)
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;

    Random rand = new Random();

    //later to be added: rotation speed, rotation acceleration, engine acceleration

    Player(int planetRadius){ //input planet radius to determine how high to spawn

        dir = 0; //can be randomized later
        rotVel = 0;

        double degree = rand.nextFloat() * 360;
        double radian = Math.toRadians(degree);

        xPos = CENTER_X + ((SPAWN_HEIGHT + planetRadius) * Math.cos(radian));
        yPos = CENTER_Y + ((SPAWN_HEIGHT + planetRadius) * Math.sin(radian));

        //if statement flips coin to determine whether ship starts clockwise or counter clockwise
        if(rand.nextInt(2) == 0){
            xVel = STARTING_VELOCITY * -Math.sin(radian);
            yVel = STARTING_VELOCITY * Math.cos(radian);
        } else{
            xVel = -STARTING_VELOCITY * -Math.sin(radian);
            yVel = -STARTING_VELOCITY * Math.cos(radian);
        }
    }

    //setter methods, used to update speed and position
    public void setxPos(double x){ xPos = x;}

    public void setyPos(double y){ yPos = y;}

    public void setxVel(double x){ xVel = x;}

    public void setyVel(double y){ yVel = y;}

    //getter methods
    public double getxPos(){ return xPos;}

    public double getyPos(){ return yPos;}

    public double getxVel(){ return xVel;}

    public double getyVel(){ return yVel;}

    public double getTotalVel(){ return Math.sqrt( xVel * xVel + yVel * yVel);}

    public double getDir(){ return dir;}

}
