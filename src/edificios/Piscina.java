package edificios;

import casillas.Solar;
import partida.Jugador;

import java.util.ArrayList;

public final class Piscina extends Edificio {

    public Piscina(){
    }

    public Piscina(Jugador duenho, Solar solar, ArrayList<Edificio> edificiosCreados) {
        super(duenho, solar, "piscina", edificiosCreados);
    }
    @Override
    String imprimirCoste() {
        return String.valueOf(getSolar().getAlquilerPiscina());
    }
}
