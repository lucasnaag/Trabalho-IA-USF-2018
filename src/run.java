public class run {
    public static void main(String[] args) {
        AgenteHeuristica game = new AgenteHeuristica();

        while (!game.end) {
            game.anda(new Player(game.x, game.y));
        }

    }
}
