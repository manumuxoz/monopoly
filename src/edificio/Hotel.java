package edificio;

import casilla.Solar;
import partida.Jugador;

import java.util.ArrayList;

public class Hotel extends Edificio {

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
