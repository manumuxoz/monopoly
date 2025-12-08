package cartas;

import casillas.Casilla;
import partida.Avatar;
import partida.Jugador;
import java.util.ArrayList;
import static monopoly.Juego.consola;

public class CartaCajaComunidad extends Carta{
    public CartaCajaComunidad(int num, Jugador duenho) {
        super("CajaComunidad", num, duenho);
    }

    @Override
    public boolean accion(Jugador actual, Jugador banca, ArrayList<ArrayList<Casilla>> posiciones, ArrayList<Jugador> jugadores) {
        return switch (getNum()) {
            case 0 -> {
                balneario(actual, banca);
                yield !actual.getEnBancarrota() && !(actual.getDeudaAPagar() > 0);
            }
            case 1 -> {
                veCarcel(actual, posiciones);
                yield true;
            }
            case 2 -> {
                colocateSalida(actual, posiciones);
                yield true;
            }
            case 3 -> {
                devolucionHacienda(actual);
                yield true;
            }
            case 4 -> {
                retrocedeSolar1(actual, posiciones);
                yield actual.getAvatar().getLugar().evaluarCasilla(actual, banca, 0);
            }
            case 5 -> {
                avanzaSolar20(actual, posiciones);
                yield actual.getAvatar().getLugar().evaluarCasilla(actual, banca, 0);
            }
            default -> true;
        };
    }


    //Acción 1 Caja:
    public void balneario(Jugador actual, Jugador banca) {
        consola.imprimir("Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.");

        actual.enBancarrota(150000, banca);

        if (!actual.getEnBancarrota() && actual.getFortuna() < 150000)
            actual.setDeudaAPagar(500000);
        else {
            actual.sumarGastos(500000);
            actual.sumarFortuna(-500000);
        }

    }

    //Caja 2:
    public void veCarcel(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        actual.encarcelar(posiciones);
    }

    //Acción 3 Caja:
    public void colocateSalida(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Colócate en la casilla de Salida. Cobra 2.000.000€.");

        Avatar avActual = actual.getAvatar();
        Casilla salida = posiciones.getFirst().getFirst();

        // Mover avatar
        avActual.getLugar().eliminarAvatar(avActual);
        avActual.setLugar(salida);
        actual.sumarVueltas(1);
        salida.anhadirAvatar(avActual);
        actual.sumarFortuna(2000000);
    }

    //Acción 4 Caja:
    public void devolucionHacienda(Jugador actual) {
        consola.imprimir("Devolución de Hacienda. Cobra 500.000€.");

        actual.sumarFortuna(500000);
        actual.sumarPremios(500000);
    }

    //Acción 5 Caja:
    public void retrocedeSolar1(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Retrocede hasta Solar1 para comprar antigüedades exóticas.");

        Avatar avActual = actual.getAvatar();
        Casilla solar1 = posiciones.getFirst().get(1);

        // Mover avatar
        avActual.getLugar().eliminarAvatar(avActual);
        avActual.setLugar(solar1);
        solar1.anhadirAvatar(avActual);
    }

    //Caja 6:
    public void avanzaSolar20(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Ve a Solar20 para disfrutar del San Fermín. Si pasas por la casilla de Salida, cobra 2.000.000€.");

        Casilla solar20 = posiciones.get(3).get(4);

        Avatar avActual = actual.getAvatar();

        if (avActual.getLugar().getPosicion() >  solar20.getPosicion()) {
            consola.imprimir("El jugador " + actual.getNombre() + " pasa por la casilla de salida y recibe 2000000$.\n");
            actual.setVueltas(actual.getVueltas() + 1);
            actual.sumarFortuna(2000000);
        }
        // Mostrar movimiento básico
        consola.imprimir("El avatar " + avActual.getId() + " avanza desde " + avActual.getLugar().getNombre() + " hasta " + solar20.getNombre() + ".");
        // Mover avatar
        avActual.getLugar().eliminarAvatar(avActual);
        avActual.setLugar(solar20);
        solar20.anhadirAvatar(avActual);
    }
}
