package cartas;

import casillas.Casilla;
import partida.Avatar;
import partida.Jugador;
import static monopoly.Juego.consola;

import java.util.ArrayList;

public class CartaSuerte extends Carta {
    public CartaSuerte(int num, Jugador duenho) {
        super("Suerte", num, duenho);
    }

    @Override
    public boolean accion(Jugador actual, Jugador banca, ArrayList<ArrayList<Casilla>> posiciones, ArrayList<Jugador> jugadores) {
        return switch (getNum()) {
            case 0 -> {
                avanzaSolar19(actual, posiciones);
                yield actual.getAvatar().getLugar().evaluarCasilla(actual, banca, 0);
            }
            case 1 -> {
                veCarcel(actual, posiciones);
                yield true;
            }
            case 2 -> {
                boteLoteria(actual);
                yield true;
            }
            case 3 -> {
                elegidoPresidente(actual, banca, jugadores);
                yield !actual.getEnBancarrota() && !(actual.getDeudaAPagar() > 0);
            }
            case 4 -> {
                retrocedeTres(actual, posiciones);
                yield actual.getAvatar().getLugar().evaluarCasilla(actual, banca, -3);
            }
            case 5 -> {
                multa(actual, banca);
                yield !actual.getEnBancarrota() && !(actual.getDeudaAPagar() > 0);
            }
            case 6 -> {
                avanzaTransporte(actual, posiciones);
                yield actual.getAvatar().getLugar().evaluarCasilla(actual, banca, 0);
            }
            default -> true;
        };
    }
    
    //Acción Suerte 1:
    public void avanzaSolar19(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€");

        Casilla solar19 = posiciones.get(3).get(2);
        Avatar avActual = actual.getAvatar();

        if (avActual.getLugar().getPosicion() > solar19.getPosicion()) {
            consola.imprimir("El jugador " + actual.getNombre() + " pasa por la casilla de salida y recibe 2000000$.\n");
            actual.setVueltas(actual.getVueltas() + 1);
            actual.sumarFortuna(2000000);
        }
        // Mostrar movimiento básico
        consola.imprimir("El avatar " + avActual.getId() + " avanza desde " + avActual.getLugar().getNombre() + " hasta " + solar19.getNombre() + ".");

        // Mover avatar
        avActual.getLugar().eliminarAvatar(avActual);
        avActual.setLugar(solar19);
        solar19.anhadirAvatar(avActual);
    }

    //Acción Suerte 2:
    public void veCarcel(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        actual.encarcelar(posiciones);
    }

    //Acción Suerte 3:
    public void boteLoteria(Jugador actual) {
        consola.imprimir("¡Has ganado el bote de la lotería! Recibe 1.000.000€.");

        actual.sumarFortuna(1000000);
        actual.sumarPremios(1000000);
    }

    //Accion Suerte 4:
    public void elegidoPresidente(Jugador actual, Jugador banca, ArrayList<Jugador> jugadores) {
        consola.imprimir("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");
        float deuda = 0;
        for (Jugador jugador : jugadores)
            if (!jugador.equals(actual)) {
                deuda += 250000;
                jugador.sumarFortuna(250000);
                consola.imprimir(jugador.getNombre() + " ha recibido 250.000€ de " + actual.getNombre());
                jugador.sumarPremios(250000);
            }

        actual.enBancarrota(deuda, banca);
        if (!actual.getEnBancarrota() && actual.getFortuna() < deuda)
            actual.setDeudaAPagar(deuda);
    }

    //Acción 5 Suerte:
    public void retrocedeTres(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("¡Hora punta de tráfico! Retrocede tres casillas.");

        actual.getAvatar().moverAvatar(posiciones, -3);
    }

    //Acción 6 Suerte:
    public void multa(Jugador actual, Jugador banca) {
        consola.imprimir("Te multan por usar el móvil mientras conduces. Paga 150.000€.");

        actual.enBancarrota(150000, banca);

        if (!actual.getEnBancarrota() && actual.getFortuna() < 150000)
            actual.setDeudaAPagar(150000);
        else {
            actual.sumarGastos(150000);
            actual.sumarFortuna(-150000);
        }
    }

    //Acción 7 Suerte:
    public void avanzaTransporte(Jugador actual, ArrayList<ArrayList<Casilla>> posiciones) {
        consola.imprimir("Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada.");

        Avatar avActual = actual.getAvatar();

        int posicionInicial = (int) avActual.getLugar().getPosicion();
        Casilla transporteCercana = avActual.getLugar();

        for (int i = posicionInicial + 1; transporteCercana.getTipo().equals("Transporte"); i = (i + 1)%40) {
            transporteCercana = posiciones.get(i / 10).get(i % 10);
            if (transporteCercana.getTipo().equals("Transporte"))
                avActual.setLugar(transporteCercana);
        }
    }
}
