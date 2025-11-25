package monopoly;

import partida.Jugador;

public class Suerte extends Accion {
    public Suerte(){}

    public Suerte(int posicion, Jugador duenho) {
        super("Suerte", posicion, duenho);
    }
}
