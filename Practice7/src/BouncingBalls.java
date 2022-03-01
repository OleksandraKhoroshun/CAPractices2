import ua.princeton.lib.*;
import java.util.Random;

public class BouncingBalls{

    public static void main(String[] args){
        Random rand = new Random();
        int N = StdRandom.uniform(50, 100);
        //int N =1;
        Ball[] balls = new Ball[N];
        for (int i = 0; i < N; i++)
            balls[i] = new Ball();
        while(true){
            StdDraw.clear();
            for (int i = 0; i < N; i++){
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.show(50);
        }
    }
}

