import java.awt.Color;
import java.util.Random;

import se.lth.cs.ptdc.window.SimpleWindow;

public class Turtle {
	private double x;
	private double y;
	private int alpha = 90;
	private boolean penstatus;
    private int trackX = 0;
	protected SimpleWindow window;
	private Color color;
	
	/**
	 * Skapar en sköldpadda som ritar i ritfönstret w. Från början befinner sig
	 * sköldpaddan i punkten x,y med pennan lyft och huvudet pekande rakt uppåt
	 * i fönstret (i negativ y-riktning)
	 */
	public Turtle(SimpleWindow w, int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.window = w;
		this.color = color;
	}
	
	/** Sänker pennan */
	public void penDown() {
		penstatus = true;
	}

	/** Lyfter pennan */
	public void penUp() {
		penstatus = false;
	}

	/** Går rakt framåt n pixlar i den riktning som huvudet pekar */
	public void forward(int n) {
		Color savedColor = window.getLineColor();
		window.setLineColor(color);
		window.moveTo((int) Math.round(x),(int) Math.round(y));
		x += n * Math.cos(Math.toRadians((double) alpha));
		y -= n * Math.sin(Math.toRadians((double) alpha));
		if (penstatus) {
			window.lineTo((int) Math.round(x), (int) Math.round(y));
		}
		window.setLineColor(savedColor);
	}

	/** Vrider beta grader åt vänster runt pennan */
	public void left(int beta) {
		alpha += beta;
	}

	/**
	 * Går till punkten newX,newY utan att rita. Pennans läge (sänkt eller lyft)
	 * och huvudets riktning påverkas inte
	 */
	public void jumpTo(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	/** Återställer huvudriktningen till den ursprungliga */
	public void turnNorth() {
		alpha = 90;
	}

	/** Tar reda på x-koordinaten för sköldpaddans aktuella position */
	public int getX() {
		return (int) Math.round(x);
	}

	/** Tar reda på y-koordinaten för sköldpaddans aktuella position */
	public int getY() {
		return (int) Math.round(y);
	}

    public int getTrackX() {
        return this.trackX;
    }

    public void setTrackX(int x) {
        this.trackX = x;
    }

	/** Tar reda på sköldpaddans riktning, i grader från positiv x-led */
	public int getDirection() {
		return alpha;
	}
}