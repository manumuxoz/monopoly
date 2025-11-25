package monopoly;
import partida.*;

public class Parking extends Accion {
    public Parking() {}

    public Parking(int posicion, Jugador duenho) {
        super("Parking", posicion, duenho);
    }

    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);
}
