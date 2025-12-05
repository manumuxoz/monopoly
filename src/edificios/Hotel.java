package edificios;

import casillas.Solar;
import partida.Jugador;

import java.util.ArrayList;

public final class Hotel extends Edificio {

    public Hotel() {
    }

    public Hotel(Jugador duenho, Solar solar, ArrayList<Edificio> edificiosCreados) {
        super(duenho, solar, "hotel", edificiosCreados);
    }
    @Override
    String imprimirCoste() {
        return String.valueOf(getSolar().getAlquilerHotel());
    }
}
