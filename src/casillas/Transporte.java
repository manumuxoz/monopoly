package casillas;

import partida.Jugador;

public final class Transporte extends Propiedad {

    public Transporte() {}

    public Transporte(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Transporte", posicion, 500000, duenho, 250000);
    }


    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        float impuesto = getImpuesto();
        Jugador duenho = getDuenho();

        sumarFrecuenciaVisita();
        if (!duenho.equals(banca) && !duenho.equals(actual)) {
            int numCasillasTransporte = actual.contarCasillasTransporte();
            if (!actual.enBancarrota(impuesto, duenho) && actual.getFortuna() >= impuesto * numCasillasTransporte) {
                actual.sumarGastos(impuesto * numCasillasTransporte);
                actual.sumarFortuna(-impuesto * numCasillasTransporte);
                actual.sumarPagoAlquileres(impuesto * numCasillasTransporte);
                sumarAlquilerAcumulado(impuesto * numCasillasTransporte);
                Jugador propietario = duenho;
                propietario.sumarFortuna(impuesto*numCasillasTransporte);
                propietario.sumarCobroAlquileres(impuesto*numCasillasTransporte);
                System.out.println("El jugador" + actual.getNombre() + " paga " +  (int)impuesto*numCasillasTransporte + "€ de transporte a " + propietario.getNombre() + ".");
                return true;
            } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto * numCasillasTransporte) actual.setDeudaAPagar(impuesto);
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
    @Override
    public float alquiler(){
        return getImpuesto();
    }
    @Override
    public float valor(){
        return getValor();
    }
}