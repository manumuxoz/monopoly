package casilla;
import partida.*;

public final abstract class Accion extends Casilla {
    public Accion(){}

    public Accion(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Accion", posicion, duenho);
    }
}
