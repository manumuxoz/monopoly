package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Especial extends Casilla{
    public Especial() {
    }

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);
    }

    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);
}
