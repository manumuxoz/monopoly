package monopoly;

import partida.Jugador;

public class Servicios extends Propiedad {
    public Servicios() {}

    public Servicios(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Servicios", posicion, 500000, duenho, 50000);
    }

    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);

}
