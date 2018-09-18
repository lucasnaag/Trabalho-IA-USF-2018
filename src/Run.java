import javax.swing.*;
import java.awt.*;

public class Run {


    public static void main(String[] args) {

        ImageIcon icon = new ImageIcon(Run.class.getResource("/tenor.gif"));
        AgenteHeuristica game = new AgenteHeuristica();
        while (!game.end) {
            game.anda(new Player(game.x, game.y));
        }
        JOptionPane.showMessageDialog(null, "Fim de Jogo", "Fim de Jogo",
                JOptionPane.INFORMATION_MESSAGE, icon);
    }
}
