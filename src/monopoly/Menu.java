package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import partida.*;
import  static monopoly.Valor.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Dado dado3;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private boolean ultimoDoble = true;//booleano que indica si en la ultima tirada fueron dobles

    // Constructor
    public Menu() {
        iniciarPartida();
        lanzamientos = 0;
        tablero = new Tablero(banca);
        dado1 = new Dado();
        dado2 = new Dado();
        dado3 = new Dado();

        while (true) {
            Scanner sc = new Scanner(System.in);
            String comando = sc.nextLine();
            analizarComando(comando);
        }
    }

    // Método para leer los comandos de un archivo
    private void leerComandos() {
        String ruta = System.getProperty("user.dir") + "/comandos.txt"; //Buscamos el directorio de trabajo y añadimos el archivo
        try (BufferedReader br = (new BufferedReader(new FileReader(ruta)))) {
            String comando;
            while ((comando = br.readLine()) != null) { //Leemos cada línea y analizamos comando
                analizarComando(comando);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        banca = new Jugador();
        banca.setNombre("Banca");
        banca.setFortuna(FORTUNA_BANCA);
        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
    }

    /*Método que interpreta el comando introducido y toma la accion correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {
        String[] partes = comando.trim().split("[ +]+"); //Dividimos por partes el comando

        //Funcionalidades que se pueden realizar con un número de jugadores menor a 2
        if (partes.length == 4 && partes[0].equals("crear") && partes[1].equals("jugador")) { //Dar de alta jugador
            String tipoAvatar = partes[3];
            String nombre = partes[2];

            // Validar tipo de avatar ANTES de crear el jugador
            if (!tipoAvatar.equals("sombrero") && !tipoAvatar.equals("esfinge") &&
                    !tipoAvatar.equals("pelota") && !tipoAvatar.equals("coche")) {
                System.out.println("Tipo de avatar incorrecto. Usa: sombrero, esfinge, pelota o coche");
                return;
            }
            if (nombre.trim().isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío");
                return;
            }

            Jugador nuevoJugador = new Jugador(nombre, tipoAvatar, this.tablero.encontrar_casilla("Salida"), avatares);
            if (nuevoJugador.getNombre() != null && jugadores.size() < 5 ) {
                jugadores.add(nuevoJugador);
                System.out.println("{\n\tnombre: " + nuevoJugador.getNombre() + ",\n\tavatar: " + nuevoJugador.getAvatar().getId() + "\n}");
            }

        } else if (partes.length == 1 && partes[0].equals("comandos")) leerComandos();

        else if (partes.length == 2 && partes[0].equals("listar") && partes[1].equals("jugadores")) listarJugadores();

        else if (partes.length == 3 && partes[0].equals("describir") && partes[1].equals("jugador"))
            descJugador(partes);

        else if (partes.length == 2 && partes[0].equals("describir")) descCasilla(partes[1]);

        else if (partes[0].equals("listar") && partes[1].equals("enventa")) listarVenta();

        else if (partes[0].equals("ver") && partes[1].equals("tablero")) System.out.println(tablero.toString());

        if (jugadores.size() > 1) { //Funcionalidades que requieren más de un jugador
            if (partes.length == 1 && partes[0].equals("jugador")) indicarTurnoJugador();

            else if (partes.length == 2 && partes[0].equals("acabar") && partes[1].equals("turno")) acabarTurno();

            else if (partes.length == 2 && partes[0].equals("lanzar") && partes[1].equals("dados")) lanzarDados(-1, -2);

            else if (partes.length == 4 && partes[0].equals("lanzar") && partes[1].equals("dados"))
                lanzarDados(Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));

            else if (partes.length == 2 && partes[0].equals("comprar")) comprar(partes[1]);

            else if (partes.length == 2 && partes[0].equals("salir") && partes[1].equals("carcel")) salirCarcel();
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        for (Jugador jugador : jugadores) { //Recorremos todos los jugadores
            if (jugador.getNombre().equals(partes[2])) { //Si existe su nombre imprimimos datos del jugador
                System.out.print("{" +
                        "\n\tnombre: " + jugador.getNombre() +
                        ",\n\tavatar: " + jugador.getAvatar().getId() +
                        ",\n\tfortuna: " + jugador.getFortuna() +
                        ",\n\tpropiedades: [");

                if(!jugador.getPropiedades().isEmpty()) { //Imprimimos lista de propiedades si no está vacía
                    for (int i = 0; i < jugador.getPropiedades().size() - 1; i++) {
                        System.out.print(jugador.getPropiedades().get(i).getNombre() + ", ");
                    }
                    System.out.print(jugador.getPropiedades().getLast().getNombre());
                    break; //Salimos del bucle
                }
            }

        }
        System.out.println("]\n}");
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    private void descAvatar(String ID) {
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    private void descCasilla(String nombre) {
        Casilla casilla = tablero.encontrar_casilla(nombre); //Buscamos casilla en el tablero
        if (casilla != null) //Si no devuelve null imprimimos la información
            System.out.println(casilla.infoCasilla());
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados(int tirada1, int tirada2) {
        Jugador jugadorActual = jugadores.get(turno);

        if (jugadorActual.getFortuna() <= 0) {
            System.out.println("Error: " + jugadorActual.getNombre() + " está en bancarrota");
            return;
        }

        if (lanzamientos == -1) {
            System.out.println("Ya tiraste este turno. Usa 'acabar turno' para continuar.");
            return;
        }

        Avatar avatarActual = jugadorActual.getAvatar();

        // Realizar tirada
        int valorTirada;
        int dado1Valor = dado1.hacerTirada();
        int dado2Valor = dado2.hacerTirada();
        int dado3Valor = dado3.hacerTirada();

        if (tirada1 == -1 && tirada2 == -2) {
            // Tirada aleatoria
            valorTirada = dado1Valor + dado2Valor + dado3Valor;
            System.out.println("Tirada: " + dado1Valor + " + " + dado2Valor + "+" + dado3Valor + " = " + valorTirada);
        } else {
            // Tirada manual
            valorTirada = tirada1 + tirada2;
            if (tirada1 < 0 || tirada1 > 6 || tirada2 < 0 || tirada2 > 6) return;
            System.out.println("Tirada manual: " + tirada1 + " + " + tirada2 + " = " + valorTirada);
        }

        boolean sonDobles = ((dado1Valor == dado2Valor) || (tirada1 == tirada2));

        // Verificar si el jugador está en la cárcel
        if (jugadorActual.getEnCarcel()) {
            manejarCarcel(jugadorActual);
            return;
        }

        if (sonDobles) {
            lanzamientos++; // Aumentar contador de dobles consecutivos
            System.out.println("¡Dobles! Llevas " + lanzamientos + " dobles consecutivos");

            // Si son 3 dobles consecutivos, ir a la cárcel
            if (lanzamientos == 3) {
                System.out.println("¡3 dobles consecutivos! " + jugadorActual.getNombre() + " va a la cárcel");
                jugadorActual.encarcelar(tablero.getPosiciones());
                lanzamientos = -1; // Resetear contador
                return;
            }
        } else {
            lanzamientos = -1; // Resetear contador si no son dobles
        }

        // Obtener casilla actual y nueva posición
        Casilla casillaActual = avatarActual.getLugar();
        float posicionActual = casillaActual.getPosicion();
        float nuevaPosicion = (posicionActual + valorTirada) % 40;

        // Encontrar nueva casilla
        Casilla nuevaCasilla = tablero.encontrarCasillaPorPosicion(nuevaPosicion);

        // Mostrar movimiento básico
        System.out.println("El avatar " + avatarActual.getId() + " avanza " + valorTirada +
                " posiciones, desde " + casillaActual.getNombre() + " hasta " +
                nuevaCasilla.getNombre() + ".");

        // Mover avatar
        casillaActual.eliminarAvatar(avatarActual);
        avatarActual.setLugar(nuevaCasilla);
        nuevaCasilla.anhadirAvatar(avatarActual);

        // USAR evaluarCasilla para TODAS las casillas
        boolean solvente = nuevaCasilla.evaluarCasilla(jugadorActual, banca, valorTirada);

        // Si evaluarCasilla retorna false (para IrCarcel), manejar el encarcelamiento
        if (!solvente && nuevaCasilla.getNombre().equals("IrCarcel")) {
            jugadorActual.encarcelar(tablero.getPosiciones());
        }
        // Si no es solvente por otras razones (falta de dinero)
        else if (!solvente) {
            System.out.println(jugadorActual.getNombre() + " debe hipotecar propiedades o declararse en bancarrota");
        }

        // Repintar tablero
        repintarTablero();
    }


    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if (casilla == null) {
            System.out.println("Error: La casilla '" + nombre + "' no existe");
            return;
        }
        for (Avatar av : casilla.getAvatares())
            if (av.getJugador().getNombre().equals(jugadorActual.getNombre()))
                casilla.comprarCasilla(jugadorActual, banca);
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        Jugador jugadorActual = jugadores.get(turno);
        if (jugadorActual.getFortuna() >= 500000 && jugadorActual.getEnCarcel()) {
            jugadorActual.setenCarcel(false);
            jugadorActual.sumarGastos(500000);
            jugadorActual.sumarFortuna(-500000);
            System.out.println(jugadorActual.getNombre() + " paga 500.000$ y sale de la carcel. Puede lanzar los dados.");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {  //Imprime las casillas que estan en venta
        for (ArrayList<Casilla> lado : tablero.getPosiciones())
            for (Casilla casilla : lado)
                System.out.println(casilla.casEnVenta());
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        for (Jugador jugador : jugadores) {
            System.out.print("{\n\tnombre: " + jugador.getNombre() +
                    ",\n\tavatar: " + jugador.getAvatar().getId() +
                    ",\n\tfortuna: " + jugador.getFortuna() +
                    ",\n\tpropiedades: [");
            if(!jugador.getPropiedades().isEmpty()) { //Imprimimos lista de propiedades si no está vacía
                for (int i = 0; i < jugador.getPropiedades().size() - 1; i++) {
                    System.out.print(jugador.getPropiedades().get(i).getNombre() + ", ");
                }
                System.out.print(jugador.getPropiedades().getLast().getNombre());
            }
            System.out.println("],\n\thipotecas: ,\n\tedificios:\n}");
        }

    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        turno = (turno + 1) % jugadores.size(); //Aritmética modular
        System.out.println("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
        lanzamientos = 0;
    }

    // Muestra el jugador que está en juego en ese momento
    private void indicarTurnoJugador() {
        if (!jugadores.isEmpty()) {
            Jugador jugadorActual = jugadores.get(turno);
            Avatar avatarActual = jugadorActual.getAvatar();
            System.out.println("{" +
                    "\n\tnombre: " + jugadorActual.getNombre() +
                    ",\n\tavatar: " + avatarActual.getId() +
                    "\n}");
        }
    }

    //Nuevos métodos:

    // Método para mostrar por pantalla información de la posición de cada jugador
    private void repintarTablero() {
        // Imprimimos las posiciones actuales para facilitar el control de los jugadores
        System.out.println("=== POSICIONES ACTUALES ===");
        for (Jugador jugador : jugadores) {
            Avatar avatar = jugador.getAvatar();
            System.out.println(jugador.getNombre() + " (" + avatar.getId() + ") en: " +
                    avatar.getLugar().getNombre());
        }
        System.out.println("===========================");
    }

    /*
    * Método para manejar situaciones cuando un jugador está en la cárcel.
    * Parámetro: jugador que está en la cárcel.
    * */
    private void manejarCarcel(Jugador jugador) {
        jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
        lanzamientos = -1;

        if (jugador.getTiradasCarcel() >= 3 || ultimoDoble) {
            jugador.setenCarcel(false);
            jugador.setTiradasCarcel(0);
            System.out.println(jugador.getNombre() + " sale de la cárcel");
        } else {
            System.out.println(jugador.getNombre() + " está en la cárcel. Turno " +
                    jugador.getTiradasCarcel() + "/3. Use 'salir carcel' para pagar fianza.");
        }
    }
    private void descCasillaActual(String nombre) {
        Casilla casilla = tablero.encontrar_casilla(nombre); //Buscamos casilla en el tablero
        if (casilla != null) //Si no devuelve null imprimimos la información
            System.out.println(casilla.infoCasilla());
    }

}
