package casilla;

import partida.Jugador;

public class Servicios extends Propiedad {
    public Servicios() {}

    public Servicios(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Servicios", posicion, 500000, duenho, 50000);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Jugador duenho = getDuenho();
        float impuesto = getImpuesto();

        sumarVecesEnCasilla(1);
        if (!duenho.equals(banca) && !duenho.equals(actual)) {
            int numCasillasServicio = duenho.contarCasillasServicio();
            int valorImpuesto = tirada * 4 * 50000;
            if(numCasillasServicio == 2) valorImpuesto = tirada * 10 * 50000;
            if (!actual.enBancarrota(impuesto, duenho) && actual.getFortuna() >= impuesto) {
                actual.sumarGastos(valorImpuesto);
                actual.sumarFortuna(-valorImpuesto);
                actual.sumarPagoAlquileres(valorImpuesto);
                sumarAlquilerAcumulado(valorImpuesto);
                Jugador propietario = duenho;
                propietario.sumarFortuna(valorImpuesto);
                propietario.sumarCobroAlquileres(valorImpuesto);
                System.out.println("Se han pagado " + valorImpuesto + "€ de servicio a " + propietario.getNombre() + ".");
                return true;
            } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto)
                actual.setDeudaAPagar(impuesto);
        }
        return false;
    }

    @Override
    public String infoCasilla() {
        return "{" +
                "\n\ttipo: " + getTipo() +
                ",\n\tvalor: " + getValor() +
                ",\n\talquiler: " + getImpuesto() +
                "\n}";
    }

    @Override
    public String casEnVenta(){
        return "\n{" +
                "\n\tnombre: " + getNombre() +
                "\n\ttipo: " + getTipo() +
                ",\n\tvalor: " + getValor() +
                "\n}";
    }
}
