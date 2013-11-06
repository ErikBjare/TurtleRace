import se.lth.cs.ptdc.window.SimpleWindow;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class RacingEvent {
	private RaceTrack track;
	private Turtle turtles[];
    private Console console;
    private SimpleWindow window;
	
	public RacingEvent(RaceTrack track, int n_turtles, Console console, SimpleWindow w) {
		this.track = track;
        this.window = w;
        Turtle turtles[] = new Turtle[n_turtles];
        Color colors[] = generateColors(n_turtles);
        for (int t=0; t<n_turtles; t++) {
            turtles[t] = new Turtle(window, 100, 100, colors[t]);
        }
		this.turtles = turtles;
        this.console = console;
	}

    /**
     * Startar loppet
     */
	public void start() {
        Random random = new Random();

        int finishY = track.getFinishY();
		int startY = track.getStartY();

        int tracks[] = track.getTracks();
        for (int t=0; t<turtles.length; t++) {
            turtles[t].setTrackX(tracks[t]);
            turtles[t].jumpTo(tracks[t], startY);
            turtles[t].penDown();
        }
		
		boolean passedHalf = false;
        double trackWidth = track.getTrackWidth();

        int delay = 10;
		while (!anyPassed(finishY)) {
            for (int t=0; t<turtles.length; t++) {
                if (turtles[t].getX() <= turtles[t].getTrackX()-trackWidth/6) {
                    turtles[t].left(-1);
                } else if (turtles[t].getX() >= turtles[t].getTrackX()+trackWidth/6) {
                    turtles[t].left(1);
                } else {
                    turtles[t].left(random.nextInt(5)-2);
                }
                turtles[t].forward(random.nextInt(2+1));
            }
            window.delay(delay);

			if (!passedHalf && anyPassed((startY-finishY)/2 + finishY)) {
				Vector<Integer> leaders = getLeaders();
                if (leaders.size() == 1) {
                    console.print((leaders.get(0)+1) + " är först med att ha tagit sig halva sträckan!");
                } else {
                    console.print(leaders.toString() + " ligger lika och är först med att ha tagit sig halva sträckan!");
                }
				window.delay(2000);
				passedHalf = true;
			}
		}

        Vector<Integer> leaders = getLeaders();
        for (Integer i : leaders) {
            console.print("Sköldpadda " + (i+1) + " kom på förstaplats!");
        }
	}

    /**
     * Returnerar en array med indexen för de/den ledande sköldpaddorna/sköldpaddan
     */
    public Vector<Integer> getLeaders() {
        Vector<Integer> leaders = new Vector<Integer>();
        int leaderY = Integer.MAX_VALUE;
        for (int t=0; t<turtles.length; t++) {
            int tY = turtles[t].getY();
            if (tY < leaderY) {
                leaders.clear();
                leaders.add(t);
                leaderY = tY;
            } else if (tY == leaderY) {
                if (!leaders.contains(t)) {
                    leaders.add(t);
                }
            }
        }
        return leaders;
    }

    /**
     * Returnerar true on någon sköldpadda har passerat en viss position i Y-led, annars false
     *
     * @param pos Y-koordinaten som ska kollas för
     */
    public boolean anyPassed(int pos) {
        for (int t=0; t<turtles.length; t++) {
            if (passed(turtles[t], pos)) return true;
        }
        return false;
    }

    /**
     * Returnerar true om en sköldpadda har passerat en position i Y-led
     *
     * @param t Sköldpaddan som ska kollas
     * @param pos Y-koordinaten som ska kollas för
     */
    public boolean passed(Turtle t, int pos) {
        if (t.getY() < pos) return true;
        else return false;
    }

    /**
     * @param n Antalet färger att generera
     * @return En lista med färger av typen Color
     */
    public static Color[] generateColors(int n) {
        Color colors[] = new Color[n];
        for (int i=0; i<n; i++) {
            int red = Math.abs((int) (255 * Math.cos(i * Math.PI / 10)));
            int green = Math.abs((int) (255 * Math.cos((i + 2) * Math.PI / 10)));
            int blue = Math.abs((int) (255 * Math.cos((i + 4) * Math.PI / 10)));
            colors[i] = new Color(red, green, blue);
        }
        return colors;
    }
}