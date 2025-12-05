package casillas;

import partida.Jugador;

public final class Suerte extends Accion {
    public Suerte(){}

    public Suerte(int posicion, Jugador duenho) {
        super("Suerte", posicion, duenho);
    }
}
