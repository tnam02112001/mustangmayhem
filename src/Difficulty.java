import java.util.TimerTask;

public class Difficulty extends TimerTask {
    public static int DifficultyFactor = 0;
    public void run() {
            DifficultyFactor += 50;

    }
}
