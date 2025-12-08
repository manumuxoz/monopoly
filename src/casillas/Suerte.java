package casillas;

import partida.Jugador;
import cartas.*;
import static monopoly.Juego.countSuerte;

public final class Suerte extends Accion {
    public Suerte(){}

    public Suerte(int posicion, Jugador duenho) {
        super("Suerte", posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador Banca, int tirada){
        sumarFrecuenciaVisita();
        actual.setCarta(new CartaSuerte(countSuerte, actual));
        countSuerte = (countSuerte + 1)%7;
        return true;
    }
}
