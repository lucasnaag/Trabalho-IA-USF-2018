import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class AgenteHeuristica extends JPanel implements Runnable {

    public class Player {
        private int x;
        private int y;
        private Boolean isAlive;
        private Double distancia;

        public Player(int x, int y) {
            this.x = x;
            this.y = y;
            this.isAlive = true;
            this.distancia = 0.0;
        }
    }

    // objetos
    private JFrame tela;
    private Thread t;

    // constantes
    private static final int X = 0;
    private static final int Y = 1;
    private static final int MAX_X = 800;
    private static final int MIN_X = 0;
    private static final int MAX_Y = 600;
    private static final int MIN_Y = 0;
    private static final int DIAMETRO = 50;
    private static final int TIME = 1;


    //variaveis
    private List<Player> players = new ArrayList<>();
    public int x = 0;
    public int y = 0;
    private int[][] inimigos;

    public static void main(String[] args) {
        new AgenteHeuristica();
    }

    public AgenteHeuristica() {
        tela = new JFrame("buscaCega.Agente Heuristica");

        this.setDoubleBuffered(true);
        this.setLayout(null);

        tela.getContentPane().add(this);

        tela.setSize(MAX_X, MAX_Y);
        tela.setVisible(true);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setResizable(false);

        inimigos = new int[MAX_X][MAX_Y];
        t = new Thread(this);
        t.start();


        Random ran = new Random();
        /*
         * Inicializa os inimigos e salva na lista
         */
        for (int i = 0; i < inimigos.length; i = i + DIAMETRO) {
            for (int j = 0; j < inimigos[i].length; j = j + DIAMETRO) {

                if (ran.nextInt() % 10 == 0) {
                    inimigos[i][j] = 1;
                    players.add(new Player(i, j));
                } else
                    inimigos[i][j] = 0;
            }
        }


        // acoes 1 esq 2 dir 3 cima 4 baixo
        // Chama busca com Heuristica

    }

    /**
     * calcula a posição dos inimigos
     */
    private double distancia(Player agente, Player inimigo) {
        return Math.sqrt(Math.pow(Math.abs(inimigo.x - agente.x), 2) + Math.pow(Math.abs(inimigo.y - agente.y), 2));
    }

    private void anda(Player agente) {
        int xMenor = agente.x - 2 > MIN_X ? agente.x - 2 : MIN_X;
        int xMaior = agente.x + 2 < MAX_X ? agente.x + 2 : MAX_X;
        int yMenor = agente.y - 2 > MIN_Y ? agente.y - 2 : MIN_Y;
        int yMaior = agente.y + 2 < MAX_Y ? agente.y + 2 : MAX_Y;
        Player menorDistancia;

        List<Player> aux = players.stream()
                .filter(enemy -> inimgoProximo(enemy, xMenor, xMaior, yMenor, yMaior))
                .collect(Collectors.toList());
        if (!aux.isEmpty()) {
            aux.forEach(inimigo -> inimigo.distancia = distancia(agente, inimigo));
            menorDistancia = aux.stream().min(Comparator.comparing(inimigo -> inimigo.distancia)).get();
            if (agente.x != menorDistancia.x) {
                if (agente.x < menorDistancia.x) andaDireita();
                else andaEsquerda();
            } else if (agente.y != menorDistancia.y) {
                if (agente.y < menorDistancia.y) andaBaixo();
                else andaCima();
            }
        } else {
            switch (andarilho()) {
                case 1:
                    andaDireita();
                    break;
                case 2:
                    andaBaixo();
                    break;
                case 3:
                    andaEsquerda();
                    break;
                case 4:
                    andaCima();
                    break;
            }
        }
    }

    private int andarilho() {
        Random ran = new Random();
        return ran.nextInt() % 4 + 1;
    }

    /**
     * Verifica o inimigo está dentro do campo de visão
     *
     * @param player the player
     * @param xMenor the x
     * @param xMaior the x
     * @param yMenor the y
     * @param yMaior the y
     * @return the return
     */

    private Boolean inimgoProximo(Player player, int xMenor, int xMaior, int yMenor, int yMaior) {
        return player.x > xMenor && player.x < xMaior && player.y < yMaior && player.y > yMenor;
    }


    public void paint(Graphics g) {

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        g.setColor(Color.RED);

        g.fillOval(x, y, DIAMETRO, DIAMETRO);
        inimigos[x][y] = 0;

        System.out.println(" x" + x + " y " + y);

        for (int i = 0; i < inimigos.length; i = i + DIAMETRO) {
            for (int j = 0; j < inimigos[i].length; j = j + DIAMETRO) {
                if (inimigos[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillOval(i, j, DIAMETRO, DIAMETRO);
                }
            }
        }
    }

    void andaDireita() {
        x = x + DIAMETRO;
        repaint();

    }

    void andaEsquerda() {
        x = x - DIAMETRO;
        repaint();

    }

    void andaCima() {
        y = y - DIAMETRO;
        repaint();

    }


    void andaBaixo() {
        y = y + DIAMETRO;
        repaint();

    }


    @Override
    public void run() {
        while (true) {
            anda(new Player(x, y));
        }
    }
}