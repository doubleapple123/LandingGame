import java.util.Random;

public class Planet {

    private static final int MAX_SIZE = 500;
    private static final int MIN_SIZE = 200;
    private static final int MEAN_SIZE = (MAX_SIZE + MIN_SIZE) / 2;
    private static final double STDV = 100;

    private int size;
    private int mass;

    Random rand = new Random();

    //size of planet is based on standard deviation of max and min sizes, mass is standard deviation from size as the mean
    //planets have a set max and min size as well as the standard deviation, size and mass will be randomly calculated from these parameters
    //creates planet obj with these new random parameters

    Planet(){
        size = gaussRandom(MEAN_SIZE);
        //System.out.println("size = " + size + "    " + (size - MEAN_SIZE));
        mass = gaussRandom(size);
        //System.out.println("mass = " + mass + "    " + (mass - MEAN_SIZE) + "\n");
    }

    public int gaussRandom(int mean){
        double gauss = 0;

        while(gauss < MIN_SIZE || gauss >MAX_SIZE){
            gauss = (STDV * rand.nextGaussian() + mean);
        }

        return (int)gauss;
    }

    public int getSize(){
        return size;
    }

    public int getMass(){
        return mass;
    }
}
