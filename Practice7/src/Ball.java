import ua.princeton.lib.*;

import java.util.Random;

public class Ball{
    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double radius; // radius

    public Ball(){
        /* initialize position and velocity */
        Random rand = new Random();
        this.rx = rand.nextDouble();
//this.rx = 1;
        this.ry = rand.nextDouble();
//this.ry =1;
        double speed = StdRandom.uniform(0.02, 0.09);

        int direction = StdRandom.uniform(0, 90);

        vx = speed * (float) Math.cos(Math.toRadians(direction));
        vy = -speed * (float) Math.sin(Math.toRadians(direction));

        //vx = StdRandom.uniform(-0.015, 0.015);
        //vy = StdRandom.uniform(-0.015, 0.015);
        radius = StdRandom.uniform(0.01, 0.02);

    }
    public void move(double dt){
        if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) {vx = -vx;}
        if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) { vy = -vy; }
        rx = rx + vx*dt;
        ry = ry + vy*dt;
    }
    public void draw(){
        StdDraw.filledCircle(rx, ry, radius);
    }
}
