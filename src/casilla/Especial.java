package casilla;

import partida.Avatar;
import partida.Jugador;

public final class Especial extends Casilla {
    public Especial() {
    }

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);
    }

    //Método que devuelve la descripción de las casillas 'Especiales'
    public String imprimirEspeciales(String nombre) { //Ajustar porque PArking es hija de ACCION
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";
        if  (nombre.equals("Parking")) {
            for (Avatar avatar : getAvatares()) {
                sb.append(separador).append(avatar.getJugador().getNombre());
                separador = ", ";
            }
            return "{\n\tbote: " + getImpuesto() + "\n\tjugadores: " +  sb + "]\n}";
        } else if (nombre.equals("Carcel")) {
            for (Avatar av : getAvatares()) {
                if (av.getJugador().getEnCarcel()) {
                    sb.append(separador).append(av.getJugador().getNombre()).append(", ").append(av.getJugador().getTiradasCarcel());
                    separador = "], [";
                }
            }
            return "{\n\tsalir: 500000,\n\tjugadores: " +  sb + "]\n}";
        }
        return "";
    }

    @Override
    public String infoCasilla() {
        return imprimirEspeciales(getNombre());
    }
}
