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

    Planet(){
        size = gaussRandom(MEAN_SIZE);
        mass = gaussRandom(size);
    }

    private int gaussRandom(int mean){
        double gauss = 0;

        while(gauss < MIN_SIZE || gauss >MAX_SIZE){
            gauss = (STDV * rand.nextGaussian() + mean);
        }
        System.out.println((int)gauss + "    " + ((int)gauss - MEAN_SIZE));
        return (int)gauss;
    }

    public int getSize(){
        return size;
    }

    public int getMass(){
        return mass;
    }
}
