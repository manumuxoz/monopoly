package monopoly;

import partida.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Acciones {
    private String tipo; //Suerte o Caja
    private int countAccion; //Contador para sabaer qué acción llevar a cabo

    //Constructor vacío
    public Acciones(){}

    public Acciones(String tipo, int countAccion){
        this.tipo = tipo;
        this.countAccion = countAccion;
    }

    //Acción suerte 1:
    public void avanzaSolar19(Jugador jugadorActual, Casilla Solar19) {
        System.out.println("Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€");

        Avatar avatarActual = jugadorActual.getAvatar();

        if (avatarActual.getLugar().getPosicion() > Solar19.getPosicion()) {
            jugadorActual.setVueltas(jugadorActual.getVueltas() + 1);
            jugadorActual.sumarFortuna(2000000);
            avatarActual.setLugar(Solar19);
        } else
            avatarActual.setLugar(Solar19);
    }

    //Acción Suerte 2:
    public void veCarcel(Jugador jugadorActual, Casilla Carcel) {
        System.out.println("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        Avatar avatarActual = jugadorActual.getAvatar();

        avatarActual.setLugar(Carcel);
        jugadorActual.setenCarcel(true);
        jugadorActual.setTiradasCarcel(0);
        jugadorActual.sumarVecesCarcel(1);
    }

    //Acción Suerte 3:
    public void boteLoteria(Jugador jugadorActual) {
        System.out.println("¡Has ganado el bote de la lotería! Recibe 1.000.000€.");

        jugadorActual.sumarFortuna(1000000);
    }

    //Accion Suerte 3:
    public void elegidoPresidente(Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        System.out.println("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");


        while (jugadorActual.getFortuna() < 250000 * jugadores.size()) {
            System.out.println(jugadorActual.getNombre() + " no dispone de suficiente dinero para realizar el pago. " +
                    "Debe vender edificios o hipotecar propiedades.");

            Casilla casillaVenta =  null;
            do {
                System.out.print("Seleccione una de sus propiedades para continuar con la venta: ");
                Scanner sc = new Scanner(System.in);
                String nombre = sc.nextLine();

                for (Casilla casilla : jugadorActual.getPropiedades()) {
                    if (casilla.getNombre().equals(nombre)) {
                        casillaVenta = casilla;
                        break;
                    }
                }
            } while(casillaVenta ==  null);

            //Sin acabar

        }
    }

}
