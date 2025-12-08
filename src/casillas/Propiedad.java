package casillas;

import excepciones.Excepcion;
import excepciones.ExcepcionDinero;
import excepciones.ExcepcionDineroInsuficiente;
import excepciones.ExcepcionReglas;
import partida.Jugador;

public abstract class Propiedad extends Casilla {
    private float alquilerAcumulado;

    public Propiedad() {}

    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler) {
        super(nombre, tipo, posicion, valor, duenho, alquiler);
    }

    public void sumarAlquilerAcumulado(float valor){alquilerAcumulado+=valor;}
    public float getAlquilerAcumulado(){return alquilerAcumulado;}

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) throws Excepcion {
        if (!getAvatares().contains(solicitante.getAvatar()))
            throw new ExcepcionReglas("El avatar " + solicitante.getAvatar().getId() + " no se encuentra en " + getNombre() + ".");

        if (!getDuenho().equals(banca) && !getDuenho().equals(solicitante))
            throw new ExcepcionReglas("Esta casilla ya tiene dueño: " + getDuenho().getNombre() + ".");

        if (solicitante.getFortuna() < getValor())
            throw new ExcepcionDineroInsuficiente("El jugador " + solicitante.getNombre() + " no tiene suficiente dinero para comprar "
                    + getNombre() + ". Su fortuna actual es de " + solicitante.getFortuna() + "$.");

        solicitante.sumarGastos(getValor());
        solicitante.sumarFortuna(-getValor());
        banca.eliminarPropiedad(this);
        banca.sumarPremios(getValor()); //Recauda el dinero de la propiedad (Banca)
        solicitante.anhadirPropiedad(this);
        this.setDuenho(solicitante);
        solicitante.sumarPatrimonio(getValor());
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public abstract String casEnVenta();

    public boolean perteneceAJugador(Jugador jugador) {
        return jugador.equals(getDuenho());
    }

    abstract float alquiler();

    abstract float valor();

    //estaHipoteca, hipotecar, comprar(Jugador jugador)
}
