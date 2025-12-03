package casilla;

import partida.Jugador;

public abstract class Propiedad extends Casilla {
    private float alquilerAcumulado;

    public Propiedad() {}

    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler) {
        super(nombre, tipo, posicion, valor, duenho, alquiler);
    }

    public void sumarAlquilerAcumulado(float valor){alquilerAcumulado+=valor;}

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
    public abstract String casEnVenta();
}
