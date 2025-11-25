package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Transporte extends Propiedad {

    public Transporte() {}

    public Transporte(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Transporte", posicion, 500000, duenho, 250000);
    }
}