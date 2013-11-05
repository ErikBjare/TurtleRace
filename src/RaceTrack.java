import se.lth.cs.ptdc.window.SimpleWindow;

public class RaceTrack {
    private SimpleWindow window;
	private int yStart;
	private int yFinish;
    private int nTracks;
    private int tracks[];
    private int trackMargin = 50;
	
	public RaceTrack(SimpleWindow w, int yStart, int yFinish, int n_tracks) {
		this.window = w;
        this.yStart = yStart;
		this.yFinish = yFinish;
        this.nTracks = n_tracks;

        int trackWidth = getTrackWidth();
        tracks = new int[nTracks];
        for (int t=0; t<n_tracks; t++) {
            tracks[t] = (int)(trackMargin+(t+0.5)*trackWidth);
        }
	}
	
	public void draw(SimpleWindow window) {
        int lineWidth = 3;
        window.setLineWidth(lineWidth);
		window.moveTo(window.getWidth()/2-12, yFinish-10);
		window.writeText("Finish");
		window.moveTo(50, yFinish - lineWidth / 2);
		window.lineTo(window.getWidth() - 53, yFinish - lineWidth / 2);
		
		window.moveTo(window.getWidth()/2-12, yStart+20);
		window.writeText("Start");
		window.moveTo(50, yStart+lineWidth/2);
		window.lineTo(window.getWidth()-53, yStart+lineWidth/2);

        window.setLineWidth(1);
        int trackWidth = getTrackWidth();
        window.moveTo(tracks[0]-trackWidth/2, yStart);
        window.lineTo(tracks[0]-trackWidth/2, yFinish);
        for (int track : tracks) {
            window.moveTo(track+trackWidth/2, yStart);
            window.lineTo(track+trackWidth/2, yFinish);
        }

        window.setLineWidth(lineWidth);
	}

    public int[] getTracks() {
        return tracks;
    }

    public int getTrackWidthTotal() {
        return window.getWidth()-trackMargin*2;
    }

    public int getTrackWidth() {
        return getTrackWidthTotal()/nTracks;
    }

	public int getStartY() {
		return yStart;
	}
	
	public int getFinishY() {
		return yFinish;
	}
}