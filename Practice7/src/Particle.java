import ua.princeton.lib.*;

import java.util.Random;

public class Particle{
    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double radius; // radius
    private final double mass; // mass
    private int count; // number of collisions

    public Particle()
    {
        Random rand = new Random();
        this.rx = rand.nextDouble();

        this.ry = rand.nextDouble();

        double speed = StdRandom.uniform(0.005, 0.006);

        int direction = StdRandom.uniform(0, 360);

        vx = speed * (float) Math.cos(Math.toRadians(direction));
        vy = -speed * (float) Math.sin(Math.toRadians(direction));

        radius = StdRandom.uniform(0.01, 0.02);

        mass   = StdRandom.uniform(0.01, 0.1);
    }

    public int count() {
        return count;
    }

    public void move(double dt) {
       // if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) {vx = -vx;}
       // if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) { vy = -vy; }
        rx = rx + vx*dt;
        ry = ry + vy*dt;
    }
    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
    public double timeToHit(Particle that) {
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        if (dvdv == 0) return Double.POSITIVE_INFINITY;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;

    }
    public double timeToHitVerticalWall() {
        if (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;
        else return Double.POSITIVE_INFINITY;
    }
    public double timeToHitHorizontalWall() {
        if (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else return Double.POSITIVE_INFINITY;
    }
    public void bounceOff(Particle that) {
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        double dist = this.radius + that.radius;
        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;
        this.count++;
        that.count++;
    }
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }
    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }
}

