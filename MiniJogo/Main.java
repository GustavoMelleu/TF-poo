import javax.swing.SwingUtilities;

public class Main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MiniSenhaGame game = new MiniSenhaGame();
            game.setVisible(true);
        });
    }
}


