package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Impuesto extends Casilla{
    public Impuesto(){}

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, posicion, impuesto, duenho);
    }
}
