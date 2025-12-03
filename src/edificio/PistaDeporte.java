package edificio;

import casilla.Solar;
import partida.Jugador;

import java.util.ArrayList;

public class PistaDeporte extends Edificio {

    public PistaDeporte(){
    }

    public PistaDeporte(Jugador duenho, Solar solar, ArrayList<Edificio> edificiosCreados) {
        super(duenho, solar, "pista", edificiosCreados);
    }

    @Override
    String imprimirCoste() {
        return String.valueOf(getSolar().getAlquilerPistaDeporte());
    }
}
