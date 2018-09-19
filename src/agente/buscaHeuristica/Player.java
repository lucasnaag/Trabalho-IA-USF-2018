package agente.buscaHeuristica;

public class Player {
    public int x;
    public int y;
    public Double distancia;
    private Boolean isAlive;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
        this.distancia = 0.0;
    }
}
