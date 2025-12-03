package casilla;

import partida.Jugador;

public final class CajaComunidad extends Accion {
    public CajaComunidad(){}

    public CajaComunidad(int posicion, Jugador duenho) {
        super("CajaComunidad", posicion, duenho);
    }
}