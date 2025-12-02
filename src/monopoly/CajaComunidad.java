package monopoly;

import partida.Jugador;

public class CajaComunidad extends Accion{
    public CajaComunidad(){}

    public CajaComunidad(int posicion, Jugador duenho) {
        super("CajaComunidad", posicion, duenho);
    }


}