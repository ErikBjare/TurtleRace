import se.lth.cs.ptdc.window.SimpleWindow;
import java.awt.Color;
import java.lang.Math;

public class TurtleRace {

	public static void main(String[] args) {
		SimpleWindow window = new SimpleWindow(500, 650, "Turtlerace");

        int n_turtles = 9;

        Color colors[] = new Color[n_turtles];
        for (int i=0; i<n_turtles; i++) {
            int red = Math.abs((int) (255 * Math.cos(i * Math.PI / n_turtles)));
            int green = Math.abs((int) (255 * Math.cos((i + 2) * Math.PI / n_turtles)));
            int blue = Math.abs((int) (255 * Math.cos((i + 4) * Math.PI / n_turtles)));
            colors[i] = new Color(red, green, blue);
        }

        Turtle turtles[] = new Turtle[n_turtles];
        for (int t=0; t<n_turtles; t++) {
            turtles[t] = new Turtle(window, 100, 100, colors[t]);
        }
		
		RaceTrack track = new RaceTrack(window, 460, 40, n_turtles);
		track.draw(window);

        Console console = new Console(window, 8, 520);
		RacingEvent race = new RacingEvent(track, turtles, console, window);

        boolean started = false;
        while (!started) {
            window.waitForEvent();

            switch (window.getEventType()) {
                case SimpleWindow.MOUSE_EVENT:
                    race.start();
                    started = true;
                    break;
            }
        }
	}
	
}

class Console {
    SimpleWindow window;
    int cursorX;
    int cursorY;

    public Console(SimpleWindow w, int cursorX, int cursorY) {
        this.window = w;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        draw();
    }

    /**
     * Ritar upp konsol-fönstret
     */
    public void draw() {
        int width = window.getWidth();

        int oldLineWidth = window.getLineWidth();
        window.setLineWidth(3);
        window.moveTo(0, cursorY);
        window.lineTo(width, cursorY);

        cursorY += 20;
        window.moveTo(width/2-50, cursorY);
        window.writeText("Meddelanden");

        cursorY += 10;
        window.setLineWidth(1);
        window.moveTo(0, cursorY);
        window.lineTo(width, cursorY);

        window.setLineWidth(oldLineWidth);
    }

    /**
     * Skriver ut ett meddelande till konsolen
     *
     * @param msg: Meddelandet att skriva ut
     */
    public void print(String msg) {
        int oldX = window.getX();
        int oldY = window.getY();
        cursorY += 20;
        window.moveTo(cursorX, cursorY);
        window.writeText(msg);
        window.moveTo(oldX, oldY);
    }
}