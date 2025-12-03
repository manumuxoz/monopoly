package casilla;

import partida.Jugador;

public class Impuesto extends Casilla {
    public Impuesto(){}

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, posicion, impuesto, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        float impuesto = getImpuesto();

        sumarVecesEnCasilla(1);
        if (!actual.enBancarrota(impuesto, getDuenho()) && actual.getFortuna() >= impuesto) {
            actual.sumarGastos(impuesto);
            actual.sumarFortuna(-impuesto);
            actual.sumarTasasImpuestos(impuesto);

            // Añadir al bote del Parking
            System.out.println("El jugador " + actual.getNombre() + " paga " + (int)impuesto + "€ que se depositan en el Parking.");
            return true;
        } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto)
            actual.setDeudaAPagar(impuesto);

        return false;
    }

    @Override
    public String infoCasilla() {
        return "{" +
                "\n\ttipo: " + getTipo() +
                ",\n\tapagar: " + getImpuesto() +
                "\n}";
    }
}
