package casillas;

import cartas.CartaCajaComunidad;
import cartas.CartaSuerte;
import partida.Jugador;
import static monopoly.Juego.countCajaComunidad;
import static monopoly.Juego.countSuerte;

public final class CajaComunidad extends Accion {
    public CajaComunidad(){}

    public CajaComunidad(int posicion, Jugador duenho) {
        super("Caja", posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        sumarFrecuenciaVisita();
        actual.setCarta(new CartaCajaComunidad(countCajaComunidad, actual));
        countCajaComunidad = (countCajaComunidad + 1)%6;
        return true;
    }
}