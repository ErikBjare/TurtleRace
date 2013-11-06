import se.lth.cs.ptdc.window.SimpleWindow;
import java.awt.Color;
import java.lang.Math;

public class TurtleRace {

	public static void main(String[] args) {
		SimpleWindow window = new SimpleWindow(600, 650, "Turtlerace"); // Y: 650

        int n_turtles = 20;

		RaceTrack track = new RaceTrack(window, 460, 40, n_turtles);
		track.draw();

        Console console = new Console(window, 8, 520);
		RacingEvent race = new RacingEvent(track, n_turtles, console, window);

        window.waitForMouseClick();
        race.start();
    }
}