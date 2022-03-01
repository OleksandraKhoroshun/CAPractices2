import ua.princeton.lib.*;
import java.util.PriorityQueue;
import java.util.Random;

public class CollisionSystem{
    private PriorityQueue<Event> pq; // the priority queue
    private double t = 0.0; // simulation clock time
    private Particle[] particles; // the array of particles

    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();
    }

    private void predict(Particle a,double limit){
        if (a == null) return;

        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.add(new Event(t + dt, a, particles[i]));
        }

        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
     if (t + dtX <= limit) pq.add(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.add(new Event(t + dtY, null, a));
    }
    private void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show(20);
        if (t < limit) {
            pq.add(new Event(t + 1.0 / 0.5, null, null));
        }
    }

    public void simulate(double limit) {
        pq = new PriorityQueue<>();
        for(int i = 0; i < particles.length; i++) predict(particles[i],limit);
        pq.add(new Event(0, null, null));
        while(!pq.isEmpty()){
            Event event = pq.poll();
            if(!event.isValid()) continue;
            Particle a = event.a;
            Particle b = event.b;
            for(int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t);
            t = event.time;
            if (a != null && b != null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else if (a == null && b == null) redraw(limit);
            predict(a,limit);
            predict(b,limit);
        }

    }

    private class Event implements Comparable<Event>{
        private double time; // time of event
        private Particle a, b; // particles involved in event
        private int countA, countB; // collision counts for a and b

        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a    = a;
            this.b    = b;
            if (a != null) countA = a.count();
            else           countA = -1;
            if (b != null) countB = b.count();
            else           countB = -1;
        }

        public int compareTo(Event that){
            return Double.compare(this.time, that.time);
        }
        public boolean isValid(){
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }
    public static void main(String[] args) {

        StdDraw.setCanvasSize(600, 600);

        Particle[] particles;

        Random rand = new Random();
            int n = StdRandom.uniform(5, 50);
            particles = new Particle[n];
            for (int i = 0; i < n; i++)
                particles[i] = new Particle();

        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(100000);
    }
}
