package agente.buscaHeuristica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class AgenteHeuristica extends JPanel {

    // objetos
    private JFrame tela;

    // constantes
    private static final int MAX_X = 800;
    private static final int MIN_X = 0;
    private static final int MAX_Y = 600;
    private static final int MIN_Y = 0;
    private static final int DIAMETRO = 50;
    private static final int CAMPO_VISAO = 3;
    private static final long TIME = 250;

    //variaveis
    private List<Player> players;
    int x;
    int y;
    boolean end;
    private int[][] inimigos;
    private boolean direcaoY;
    private boolean direcaoX;

    AgenteHeuristica() {
        players = new ArrayList<>();
        x = 0; y = 0;
        end = false;
        direcaoY = true;
        direcaoX = true;

        tela = new JFrame("Busca com Heuristica - Agente Inteligente");

        criaTela(tela);

         // Inicializa os inimigos e salva na lista
        geraInimigos();
    }

    private void geraInimigos() {
        Random ran = new Random();
        for (int i = 0; i < inimigos.length; i = i + DIAMETRO) {
            for (int j = 0; j < inimigos[i].length; j = j + DIAMETRO) {

                if (ran.nextInt() % 10 == 0) {
                    inimigos[i][j] = 1;
                    players.add(new Player(i, j));
                } else
                    inimigos[i][j] = 0;
            }
        }
    }

    private void criaTela(JFrame tela) {
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.tela.getContentPane().add(this);
        this.tela.setSize(MAX_X, MAX_Y);
        this.tela.setVisible(true);
        this.tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.tela.setResizable(false);
        inimigos = new int[MAX_X][MAX_Y];
    }

    /**
     * Calcula a distancia entre o inimigo e o agente
     * @param agente player agente inteligente
     * @param inimigo player inimigo
     * @return distancia entre o inimigo e o agente
     */
    private double distancia(Player agente, Player inimigo) {
        return Math.sqrt(Math.pow(Math.abs(inimigo.x - agente.x), 2) + Math.pow(Math.abs(inimigo.y - agente.y), 2));
    }

    /**
     * Realiza o próximo movimento de acordo com a distancia do inimigo
     * @param agente player agente inteligente
     */
    void anda(Player agente) {
        int xMenor = agente.x - (CAMPO_VISAO * DIAMETRO) > MIN_X ? agente.x - (CAMPO_VISAO * DIAMETRO) : MIN_X;
        int xMaior = agente.x + (CAMPO_VISAO * DIAMETRO) < MAX_X ? agente.x + (CAMPO_VISAO * DIAMETRO) : MAX_X;
        int yMenor = agente.y - (CAMPO_VISAO * DIAMETRO) > MIN_Y ? agente.y - (CAMPO_VISAO * DIAMETRO) : MIN_Y;
        int yMaior = agente.y + (CAMPO_VISAO * DIAMETRO) < MAX_Y ? agente.y + (CAMPO_VISAO * DIAMETRO) : MAX_Y;

        Player menorDistancia;
        try {
            Thread.sleep(TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Player> aux = new ArrayList<>();
        for (Player enemy : players) {
            if (inimgoProximo(enemy, xMenor, xMaior, yMenor, yMaior))
                aux.add(enemy);
        }
        if (!aux.isEmpty()) {
            aux.forEach(inimigo -> inimigo.distancia = distancia(agente, inimigo));
            menorDistancia = aux.stream().min(Comparator.comparing(inimigo -> inimigo.distancia)).orElse(null);
            if (agente.x != menorDistancia.x) {
                if (agente.x < menorDistancia.x) andaDireita();
                else andaEsquerda();
            } else if (agente.y != menorDistancia.y) {
                if (agente.y < menorDistancia.y) andaBaixo();
                else andaCima();
            } else players.remove(menorDistancia);
        } else {
            if (direcaoX) {
                if (!andaDireita()) {
                    andaVertical();
                    direcaoX = !direcaoX;
                }
            } else if (!andaEsquerda()) {
                andaVertical();
                direcaoX = !direcaoX;
            }
        }

        if (players.isEmpty()) end = true;
    }

    /**
     * Faz o agente andar na vertical quando estiver no modo de busca cega
     */
    private void andaVertical() {
        if (direcaoY) {
            if (!andaBaixo()) {
                andaCima();
                direcaoY = !direcaoY;
            }
        } else if (!andaCima()) {
            andaBaixo();
            direcaoY = !direcaoY;
        }
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
        return player.x >= xMenor && player.x <= xMaior && player.y <= yMaior && player.y >= yMenor;
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

    boolean andaDireita() {
        boolean andou = true;

        if ((x + DIAMETRO) < MAX_X)
            x = x + DIAMETRO;
        else
            andou = false;

        repaint();
        return andou;
    }

    boolean andaEsquerda() {
        boolean andou = true;

        if ((x - DIAMETRO) >= MIN_X)
            x = x - DIAMETRO;
        else
            andou = false;

        repaint();
        return andou;
    }

    boolean andaCima() {
        boolean andou = true;

        if ((y - DIAMETRO) >= MIN_Y)
            y = y - DIAMETRO;
        else
            andou = false;

        repaint();
        return andou;
    }

    boolean andaBaixo() {
        boolean andou = true;

        if ((y + DIAMETRO) < MAX_Y)
            y = y + DIAMETRO;
        else
            andou = false;

        repaint();
        return andou;
    }

}