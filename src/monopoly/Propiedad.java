package monopoly;

import partida.Jugador;

import java.util.ArrayList;

import static monopoly.Valor.*;
import static monopoly.Valor.BLUE;
import static monopoly.Valor.GREEN;
import static monopoly.Valor.RED;
import static monopoly.Valor.WHITE;
import static monopoly.Valor.YELLOW;

public abstract class Propiedad extends Casilla {
    private float alquilerAcumulado;

    public Propiedad() {}

    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler) {
        super(nombre, tipo, posicion, valor, duenho, alquiler);
    }

    public void sumarAlquilerAcumulado(float valor){alquilerAcumulado+=valor;}



    //Método que devuelve el nombre del color de un grupo pasado por argumento
    public String color(String colorGrupo) {
        return switch (colorGrupo) {
            case BLACK -> "Negro";
            case CYAN -> "Cian";
            case PURPLE -> "Morado";
            case WHITE -> "Blanco";
            case RED -> "Rojo";
            case YELLOW -> "Amarillo";
            case GREEN -> "Verde";
            case BLUE -> "Azul";
            default -> "";
        };
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!getAvatares().contains(solicitante.getAvatar())) {
            System.out.println("Error: El avatar " + solicitante.getAvatar().getId() + " no se encuentra en " + getNombre() + ".");
            return;
        }

        if (!getDuenho().equals(banca) && !getDuenho().equals(solicitante)) {
            System.out.println("Esta casilla ya tiene dueño: " + getDuenho().getNombre() + ".");
            return;
        }

        if (getTipo().equals("Especiales") || getTipo().equals("Suerte") || getTipo().equals("Caja")) { //Comprobamos tipo
            System.out.println("Error: Esta casilla es de tipo '" + getTipo() + "'. Solo se pueden comprar casillas de tipo 'Solar', 'Transporte' o 'Servicios'.");
            return;
        }

        if (solicitante.getFortuna() >= getValor()) { //Comprobamos fortuna
            solicitante.sumarGastos(getValor());
            solicitante.sumarFortuna(-getValor());
            banca.eliminarPropiedad(this);
            banca.sumarPremios(getValor()); //Recauda el dinero de la propiedad (Banca)
            solicitante.anhadirPropiedad(this);
            this.setDuenho(solicitante);
            solicitante.sumarPatrimonio(getValor());
            System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + getNombre() +
                    " por " + getValor() + "$. Su fortuna actual es " + solicitante.getFortuna() + "$.");
        } else
            System.out.println(solicitante.getNombre() + " no dispone de suficiente fortuna para comprar " + getNombre());

    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        // Solo mostrar si el dueño es la banca (está en venta)
        if (getDuenho().getNombre().equals("Banca")) {
            if (this.getTipo().equals("Solar"))
                return "\n{" +
                        "\n\tnombre: " + getNombre() +
                        "\n\ttipo: " + getTipo() +
                        ",\n\tgrupo: " + color(getGrupo().getColorGrupo()) +
                        ",\n\tvalor: " + getValor() +
                        "\n}";
            if (this.getTipo().equals("Transporte") || this.getTipo().equals("Servicios"))
                return "\n{" +
                        "\n\tnombre: " + getNombre() +
                        "\n\ttipo: " + getTipo() +
                        ",\n\tvalor: " + getValor() +
                        "\n}";
        }
        return "";
    }
}
