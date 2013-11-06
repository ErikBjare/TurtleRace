import se.lth.cs.ptdc.window.SimpleWindow;

public class Console {
    SimpleWindow window;
    int cursorX;
    int cursorY;

    /**
     * @param w Fönstret att rita på (instans av SimpleWindow)
     * @param cursorX Var fönstret ska börja i X-led
     * @param cursorY Var fönstret ska börja i Y-led
     */
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