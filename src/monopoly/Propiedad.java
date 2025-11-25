package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Propiedad extends Casilla {
    private float alquilerAcumulado;

    public Propiedad() {}

    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler) {
        super(nombre, tipo, posicion, valor, duenho, alquiler);
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!avatares.contains(solicitante.getAvatar())) {
            System.out.println("Error: El avatar " + solicitante.getAvatar().getId() + " no se encuentra en " + nombre + ".");
            return;
        }

        if (!duenho.equals(banca) && !duenho.equals(solicitante)) {
            System.out.println("Esta casilla ya tiene dueño: " + duenho.getNombre() + ".");
            return;
        }

        if (tipo.equals("Especiales") || tipo.equals("Suerte") || tipo.equals("Caja")) { //Comprobamos tipo
            System.out.println("Error: Esta casilla es de tipo '" + tipo + "'. Solo se pueden comprar casillas de tipo 'Solar', 'Transporte' o 'Servicios'.");
            return;
        }

        if (solicitante.getFortuna() >= valor) { //Comprobamos fortuna
            solicitante.sumarGastos(valor);
            solicitante.sumarFortuna(-valor);
            banca.eliminarPropiedad(this);
            banca.sumarPremios(valor); //Recauda el dinero de la propiedad (Banca)
            solicitante.anhadirPropiedad(this);
            this.setDuenho(solicitante);
            solicitante.sumarPatrimonio(valor);
            System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + nombre +
                    " por " + valor + "$. Su fortuna actual es " + solicitante.getFortuna() + "$.");
        } else
            System.out.println(solicitante.getNombre() + " no dispone de suficiente fortuna para comprar " + nombre);

    }

}
