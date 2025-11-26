package monopoly;

import partida.Avatar;
import partida.Jugador;

import java.util.ArrayList;

public class Especial extends Casilla{
    public Especial() {
    }

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);
    }

    //Método que devuelve la descripción de las casillas 'Especiales'
    public String imprimirEspeciales(String nombre) {
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";
        if  (nombre.equals("Parking")) {
            for (Avatar avatar : avatares) {
                sb.append(separador).append(avatar.getJugador().getNombre());
                separador = ", ";
            }
            return "{\n\tbote: " + impuesto + "\n\tjugadores: " +  sb + "]\n}";
        } else if (nombre.equals("Carcel")) {
            for (Avatar av : avatares) {
                if (av.getJugador().getEnCarcel()) {
                    sb.append(separador).append(av.getJugador().getNombre()).append(", ").append(av.getJugador().getTiradasCarcel());
                    separador = "], [";
                }
            }
            return "{\n\tsalir: 500000,\n\tjugadores: " +  sb + "]\n}";
        }
        return "";
    }
}
