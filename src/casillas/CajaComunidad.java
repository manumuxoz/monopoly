package casillas;

import partida.Jugador;

public final class CajaComunidad extends Accion {
    public CajaComunidad(){}

    public CajaComunidad(int posicion, Jugador duenho) {
        super("CajaComunidad", posicion, duenho);
    }
    @Override
    public String infoCasilla(){
        return "";
    }
    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        return true;
    }
}