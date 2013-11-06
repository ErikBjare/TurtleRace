import se.lth.cs.ptdc.window.SimpleWindow;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class RacingEvent {
	private RaceTrack track;
	private Turtle turtles[];
    private Console console;
    private SimpleWindow window;

    /**
     * @param track En instans av RaceTrack att köra loppet på
     * @param n_turtles Antalet sköldpaddor att skapa
     * @param console Konsolen att skriva meddelanden till (instans av Console)
     * @param w Fönstret att rita på (instans av SimpleWindow)
     */
	public RacingEvent(RaceTrack track, int n_turtles,
                       Console console, SimpleWindow w) {
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
            for (Turtle t : turtles) {
                if (t.getX() <= t.getTrackX()-trackWidth/6) {
                    t.left(-1);
                } else if (t.getX() >= t.getTrackX()+trackWidth/6) {
                    t.left(1);
                } else {
                    t.left(random.nextInt(5)-2);
                }
                t.forward(random.nextInt(2+1));
            }
            SimpleWindow.delay(delay);

			if (!passedHalf && anyPassed((startY-finishY)/2 + finishY)) {
				Vector<Integer> leaders = getLeaders();
                String msg;
                if (leaders.size() == 1) {
                    msg = "Sköldpadda " +
                            leaders.get(0) +
                            " är först med att ha tagit sig halva sträckan!";
                } else {
                    msg = "Sköldpaddorna " +
                            leaders.toString() +
                            " ligger lika och är först med att ha tagit sig halva sträckan!";
                }
                console.print(msg);
				SimpleWindow.delay(2000);
				passedHalf = true;
			}
		}

        Vector<Integer> leaders = getLeaders();
        for (Integer i : leaders) {
            console.print("Sköldpadda " + i + " kom på förstaplats!");
        }
	}

    /**
     * @return Vektor med (index+1) för de(n) ledande sköldpaddan/sköldpaddorna
     */
    public Vector<Integer> getLeaders() {
        Vector<Integer> leaders = new Vector<Integer>();
        int leaderY = Integer.MAX_VALUE;
        for (int t=0; t<turtles.length; t++) {
            int tY = turtles[t].getY();
            if (tY < leaderY) {
                leaders.clear();
                leaders.add(t+1);
                leaderY = tY;
            } else if (tY == leaderY) {
                if (!leaders.contains(t+1)) {
                    leaders.add(t+1);
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