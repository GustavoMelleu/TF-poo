import java.awt.Color;

public class GameColor {
    public static final Color[] COLORS = {
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
        Color.ORANGE, Color.PINK, Color.MAGENTA, Color.CYAN
    };

    public static Color getNextColor(Color currentColor) {
        int index = (java.util.Arrays.asList(COLORS).indexOf(currentColor) + 1) % COLORS.length;
        return COLORS[index];
    }
}

