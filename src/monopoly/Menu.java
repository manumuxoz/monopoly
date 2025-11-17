package monopoly;

import java.util.*;

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
    private Jugador banca; //El jugador banca.

    //Atributos nuevos:
    private ArrayList<Edificio> edificios; //Edificios creados
    private int countAccionesSuerte; //Método para contar las cartas de suerte
    private int countAccionesCaja; //Método para contar las cartas de caja de comunidad

    // Constructor
    public Menu() {
        iniciarPartida();
        lanzamientos = 0;
        tablero = new Tablero(banca);
        dado1 = new Dado();
        dado2 = new Dado();
        edificios = new ArrayList<>();

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
            if (!partes[3].equals("sombrero") && !partes[3].equals("esfinge") &&
                    !partes[3].equals("pelota") && !partes[3].equals("coche")) {
                System.out.println("Tipo de avatar incorrecto. Usa: sombrero, esfinge, pelota o coche.");
                return;
            }
            if (partes[2].trim().isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío.");
                return;
            }

            Jugador nuevoJugador = new Jugador(partes[2], partes[3], tablero.encontrar_casilla("Salida"), avatares);
            if (nuevoJugador.getNombre() != null && jugadores.size() <= 4) {
                jugadores.add(nuevoJugador);
                System.out.println("{\n\tnombre: " + nuevoJugador.getNombre() + ",\n\tavatar: " + nuevoJugador.getAvatar().getId() + "\n}");
            }

        } else if (partes.length == 1 && partes[0].equals("comandos")) leerComandos();

        else if (partes.length == 2 && partes[0].equals("listar") && partes[1].equals("jugadores")) listarJugadores();

        else if (partes.length == 3 && partes[0].equals("describir") && partes[1].equals("jugador")) descJugador(partes);

        else if (partes.length == 2 && partes[0].equals("describir")) descCasilla(partes[1]);

        else if (partes[0].equals("listar") && partes[1].equals("enventa")) listarVenta();

        else if (partes.length == 3 && partes[0].equals("listar") && partes[1].equals("edificios")) listarEdificiosGrupo(partes[2]);

        else if (partes[0].equals("ver") && partes[1].equals("tablero")) System.out.println(tablero.toString());

        if (jugadores.size() > 1) { //Funcionalidades que requieren más de un jugador
            if (partes.length == 1 && partes[0].equals("jugador")) indicarTurnoJugador();

            else if (partes.length == 2 && partes[0].equals("acabar") && partes[1].equals("turno")) acabarTurno();

            else if (partes.length == 2 && partes[0].equals("lanzar") && partes[1].equals("dados")) lanzarDados(-1, -2);

            else if (partes.length == 4 && partes[0].equals("lanzar") && partes[1].equals("dados"))
                lanzarDados(Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));

            else if (partes.length == 2 && partes[0].equals("comprar")) comprar(partes[1]);

            else if (partes.length == 2 && partes[0].equals("salir") && partes[1].equals("carcel")) salirCarcel();

            else if (partes.length == 2 && partes[0].equals("edificar")) edificar(partes[1]);

            else if (partes.length == 2 && partes[0].equals("listar") && partes[1].equals("edificios")) listarEdificios();

            else if (partes.length == 2 && partes[0].equals("hipotecar")) hipotecar(partes[1]);

            else if (partes.length == 2 && partes[0].equals("deshipotecar")) deshipotecar(partes[1]);

            else if (partes.length == 4 && partes[0].equals("vender")) vender(partes[1], partes[2], Integer.parseInt(partes[3]));

            else if (partes.length == 2 && partes[0].equals("estadisticas")) mostrarEstadisticas(partes);

            else if (partes.length == 1 && partes[0].equals("estadisticas")) mostrarEstadisticasGlobales();
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        for (Jugador jugador : jugadores) { //Recorremos todos los jugadores
            if (jugador.getNombre().equals(partes[2])) { //Si existe su nombre imprimimos datos del jugador
                System.out.print("{\n\tnombre: " + jugador.getNombre() +
                        ",\n\tavatar: " + jugador.getAvatar().getId() +
                        ",\n\tfortuna: " + (int) jugador.getFortuna() +
                        ",\n\tpropiedades: [");
                imprimirPropiedades(jugador);
                System.out.print("],\n\thipotecas: [],\n\tedificios: [");
                imprimirEdificios(jugador);
                System.out.print("]\n}\n");
                return; //Salimos del bucle
            }
        }
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

        if (jugadorActual.getEnBancarrota() || !puedeTirar()) return; //Si el jugador está en bancarrota o ya ha tirado, no puede tirar

        //Realizar tirada
        int valorTirada, dado1Valor, dado2Valor;
        boolean sonDobles;

        if (tirada1 == -1 && tirada2 == -2) { //Tirada aleatoria
            dado1Valor = dado1.hacerTirada();
            dado2Valor = dado2.hacerTirada();
            valorTirada = dado1Valor + dado2Valor;
            System.out.println("Tirada: " + dado1Valor + " + " + dado2Valor + " = " + valorTirada);

            sonDobles = (dado1Valor == dado2Valor); //Comprobamos que sean dobles
        } else { //Tirada manual
            valorTirada = tirada1 + tirada2;

            if (tirada1 < 0 || tirada1 > 6 || tirada2 < 0 || tirada2 > 6) {
                System.out.println("Error: tirada no válida.");
                return;
            }
            System.out.println("Tirada manual: " + tirada1 + " + " + tirada2 + " = " + valorTirada);

            sonDobles = (tirada1 == tirada2); //Comprobamos que sean dobles
        }

        // Verificar si el jugador está en la cárcel
        if (jugadorActual.getEnCarcel()) {
            manejarCarcel(jugadorActual, sonDobles);
            return;
        }
        manejarAvatar(valorTirada);
        manejarDobles(sonDobles);
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if (casilla == null) {
            System.out.println("Error: La casilla " + nombre + " no existe.");
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
    private void listarVenta() {  //Imprime las casillas que están en venta
        for (ArrayList<Casilla> lado : tablero.getPosiciones())
            for (Casilla casilla : lado)
                System.out.print(casilla.casEnVenta());  //print y no println porque si no al hacer un return vacio ocupa una línea
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        if (!jugadores.isEmpty()) {
            for (Jugador jugador : jugadores) {
                System.out.print("{\n\tnombre: " + jugador.getNombre() +
                        ",\n\tavatar: " + jugador.getAvatar().getId() +
                        ",\n\tfortuna: " + jugador.getFortuna() +
                        ",\n\tpropiedades: [");
                imprimirPropiedades(jugador); //Imprimimos las propiedades
                System.out.print("],\n\thipotecas: [],\n\tedificios: [");
                imprimirEdificios(jugador); //Imprimimos los edificios
                System.out.print("]\n}\n");
            }
        } else
            System.out.println("No hay jugadores en la partida");

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
            System.out.println("{\n\tnombre: " + jugadorActual.getNombre() + ",\n\tavatar: " + avatarActual.getId() + "\n}");
        }
    }

    //Nuevos métodos:

    //Método para imprimir las propiedades d eun jugador dado
    public void imprimirPropiedades(Jugador jugador) {
        for (Casilla propiedad : jugador.getPropiedades()) {
            if (!propiedad.equals(jugador.getPropiedades().getLast()))
                System.out.print(propiedad.getNombre() + ", ");
            else
                System.out.print(propiedad.getNombre());
        }
    }

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
    private void manejarCarcel(Jugador jugador, boolean dobles) {
        jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
        lanzamientos = -1;
        if (jugador.getTiradasCarcel() >= 3 || dobles) {
            jugador.setenCarcel(false);
            jugador.setTiradasCarcel(0);
            System.out.println(jugador.getNombre() + " sale de la cárcel.");
            if(dobles) lanzamientos = 0;

        } else {
            System.out.println(jugador.getNombre() + " está en la cárcel. Turno " +
                    jugador.getTiradasCarcel() + "/3. Use 'salir carcel' para pagar fianza.");
        }
    }

    //Método para saber si un jugador puede tirar
    private boolean puedeTirar() {
        if (lanzamientos == -1) {
            System.out.println("Ya tiraste este turno. Usa 'acabar turno' para continuar.");
            return false;
        }
        return true;
    }

    //Método para manejar las situaciones en que la tirada ha sido dobles
    private void manejarDobles(boolean sonDobles) {
        Jugador jugadorActual = jugadores.get(turno);

        if (sonDobles) {
            lanzamientos++; // Aumentar contador de dobles consecutivos
            System.out.println("¡Dobles! Llevas " + lanzamientos + " dobles consecutivos.");

            // Si son 3 dobles consecutivos, ir a la cárcel
            if (lanzamientos == 3) {
                System.out.println("¡3 dobles consecutivos! " + jugadorActual.getNombre() + " va a la cárcel.");
                jugadorActual.encarcelar(tablero.getPosiciones());
                lanzamientos = -1; // Resetear contador
            }
        } else
            lanzamientos = -1; // Resetear contador si no son dobles
    }

    //Método para manejar las acciones del avatar
    private void manejarAvatar(int valorTirada) {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();

        avatarActual.moverAvatar(tablero.getPosiciones(), valorTirada);

        Casilla nuevaCasilla = avatarActual.getLugar();

        //Sumamos bote al parking en caso de caer en una casilla de tipo 'Impuesto'
        if (nuevaCasilla.getTipo().equals("Impuesto"))
            tablero.encontrar_casilla("Parking").sumarValor(nuevaCasilla.getImpuesto());

        if (nuevaCasilla.getTipo().equals("Suerte") || nuevaCasilla.getTipo().equals("Caja"))
            manejarAcciones(nuevaCasilla.getTipo());


        // USAR evaluarCasilla para TODAS las casillas
        boolean solvente = nuevaCasilla.evaluarCasilla(jugadorActual, banca, valorTirada);

        eliminarEdificiosBanca();

        //Si puede pagar el alquiler realizamos el bucle de venta o impoteca hasta pagarlo
        if (!solvente && !jugadorActual.getEnBancarrota())
            ventaOHipoteca(jugadorActual.getDeudaAPagar());


        // Si evaluarCasilla retorna false (para IrCarcel), manejar el encarcelamiento
        if (!solvente && nuevaCasilla.getNombre().equals("IrCarcel")) {
            jugadorActual.encarcelar(tablero.getPosiciones());
            jugadorActual.sumarVecesCarcel(1);
        }

        // Repintar tablero
        repintarTablero();
    }

    //Método para edificar un tipo de edificio dado
    private void edificar(String tipoEdificio) {
        Jugador jugadorActual =  jugadores.get(turno);
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();

        if (!casillaActual.getTipo().equals("Solar")) {
            System.out.println("Error: solo se puede edificar en una casilla de tipo 'Solar'.");
            return;
        }

         for (Casilla solar : casillaActual.getGrupo().getMiembros()) //Comprobamos que ningún miembro del grupo esté hipotecado
             if (solar.getHipotecado()) {
                 System.out.println("No se puede edificar en " + casillaActual.getNombre() + ". " + solar.getNombre() + " está hipotecado.");
                 return;
             }

        switch (tipoEdificio) {
            case "casa" -> casillaActual.edificarCasa(jugadorActual, edificios);
            case "hotel" -> casillaActual.edificarHotel(jugadorActual, edificios);
            case "piscina" -> casillaActual.edificarPiscina(jugadorActual, edificios);
            case "pista" -> casillaActual.edificarPista(jugadorActual, edificios);
            default -> System.out.println("Error: Nombre de edificación inválido. Usa: casa, hotel, piscina o pista");
        }
    }

    //Método para imprimir los IDs de los edificios de un jugador
    public void imprimirEdificios(Jugador jugador) {
        for (Edificio edificio : edificios) {
            if (edificio.getDuenho().equals(jugador)) {
                if (!edificio.equals(edificios.getLast()))
                    System.out.print(edificio.getID() + ", ");
                else
                    System.out.print(edificio.getID());
            }
        }
    }

    //Método para listar todos los edificios construidos
    public void listarEdificios() {
        if (!edificios.isEmpty()) {
            for (Edificio edificio : edificios) {
                System.out.println(edificio.infoEdificio());
            }
        } else
            System.out.println("No hay edificios para listar.");
    }

    //Método para listar los métodos de un grupo y saber qué edificios se pueden construir
    public void listarEdificiosGrupo(String colorGrupo) {
        int countCasas = 0, countHoteles = 0, countPiscina = 0, countPista = 0; //Variables para llevar cuenta de los edificios construidos
        String color = Character.toUpperCase(colorGrupo.charAt(0)) + colorGrupo.substring(1); //Formatemamos el color pasado por comando

        if (tablero.getGrupos().get(color) == null) { //Comprobamos que exista el color
            System.out.println("Error: No existe el grupo de color " + color + ".");
            return;
        }

        for (Casilla solar : tablero.getGrupos().get(color).getMiembros()) { //Iteramos sobre el arraylist de casillas del grupo
            System.out.println(solar.infoEdificios());
            countCasas += solar.getNumCasas();
            if (solar.getHotel()) countHoteles++;
            if (solar.getPiscina()) countPiscina++;
            if (solar.getPistaDeporte()) countPista++;
        }

        int numSolares = tablero.getGrupos().get(color).getMiembros().size(); //Número de solares
        System.out.println("Se pueden edificar " + (numSolares * 4 - countCasas) + " casas, " +
                (numSolares - countHoteles) + " hoteles, " + (numSolares - countPiscina) + " piscinas y " +
                (numSolares - countPista) + " pistas.");
    }

    //Método para hipotecar una casilla
    public void hipotecar(String nombreCasilla) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla hipoteca = null;

        for (ArrayList<Casilla> lado : tablero.getPosiciones()) //Buscamos la casilla a hipotecar
            for (Casilla casilla : lado)
                if (casilla.getNombre().equals(nombreCasilla)) {
                    hipoteca = casilla;
                    break;
                }

        if (hipoteca == null) { //Si no se ha asignado, es que no existe
            System.out.println("Error: No existe la casilla " + nombreCasilla + ".");
            return;
        }

        if (!hipoteca.getDuenho().equals(jugadorActual)) { //Comprobamos que sea el jugador actual el dueño de la casilla
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + hipoteca.getNombre() +
                    ". Esta casilla pertenece a " + hipoteca.getDuenho().getNombre() + ".");
            return;
        }

        if (hipoteca.getHipotecado()) { //Comprobamos que no esté hipotecada
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + hipoteca.getNombre() + ". Ya está hipotecada.");
            return;
        }

        if (!hipoteca.getEdificios().isEmpty()) { //Comprobamos que no tenga edificios la casilla a hipotecar
            System.out.println(jugadorActual.getNombre() + " no puede hipotecar " + hipoteca.getNombre() + ". Debe vender todos los edificios del solar");
            return;
        }

        hipoteca.setHipotecado(true); //Indicamos que la propiedad está hipotecada
        jugadorActual.sumarFortuna(hipoteca.getHipoteca()); //Sumamos la hipoteca
        jugadorActual.getHipotecas().add(hipoteca); //Añadimos a las propiedades hipotecadas del jugador

        StringBuilder sb = new StringBuilder();
        if (hipoteca.getTipo().equals("Solar")) //Si es solar añadimos mensaje de aviso
            sb.append(" No puede recibir alquileres ni edificar en el grupo ").append(hipoteca.color(hipoteca.getGrupo().getColorGrupo())).append(".");

        System.out.println(jugadorActual.getNombre() + " recibe " + hipoteca.getHipoteca() + "$ por la hipoteca de " +
                hipoteca.getNombre() + "." + sb);
    }

    //Método para deshipotecar una casilla
    public void deshipotecar(String nombreCasilla) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla hipoteca = null;

        for (ArrayList<Casilla> lado : tablero.getPosiciones()) //Buscamos la casilla a hipotecar
            for (Casilla casilla : lado)
                if (casilla.getNombre().equals(nombreCasilla)) {
                    hipoteca = casilla;
                    break;
                }

        if (hipoteca == null) { //Si no se ha asignado, es que no existe
            System.out.println("Error: No existe la casilla " + nombreCasilla + ".");
            return;
        }

        if (!hipoteca.getDuenho().equals(jugadorActual)) { //Comprobamos que sea el jugador actual el dueño de la casilla
            System.out.println(jugadorActual.getNombre() + " no puede deshipotecar " + hipoteca.getNombre() +
                    ". Esta casilla pertenece a " + hipoteca.getDuenho().getNombre() + ".");
            return;
        }

        if (!hipoteca.getHipotecado()) { //Comprobamos que no esté hipotecada
            System.out.println(jugadorActual.getNombre() + " no puede deshipotecar " + hipoteca.getNombre() + ". No está hipotecada.");
            return;
        }

        hipoteca.setHipotecado(false); //Indicamos que la propiedad no está hipotecada
        jugadorActual.sumarGastos(hipoteca.getHipoteca());
        jugadorActual.sumarFortuna(-hipoteca.getHipoteca()); //Restamos la hipoteca
        jugadorActual.getHipotecas().remove(hipoteca); //Eliminamos de las propiedades hipotecadas del jugador

        StringBuilder sb = new StringBuilder();
        if (hipoteca.getTipo().equals("Solar")) //Si es solar añadimos mensaje de aviso
            sb.append(" Ahora puede recibir alquileres y edificar en el grupo ").append(hipoteca.color(hipoteca.getGrupo().getColorGrupo())).append(".");

        System.out.println(jugadorActual.getNombre() + " paga " + hipoteca.getHipoteca() + "$ por deshipotecar " +
                hipoteca.getNombre() + "." + sb);
    }

    //Método para vender una casilla
    public void vender(String tipoEdificio, String nombreCasilla, int cantidad) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = null;

        for (ArrayList<Casilla> lado : tablero.getPosiciones())
            for (Casilla propiedad : lado)
                if (propiedad.getNombre().equals(nombreCasilla)) {
                    casilla = propiedad;
                    break; //Salimos del bucle para no recorrer casillas innecesarias
                }

        if (casilla == null) {
            System.out.println("Error: No existe la casilla " + nombreCasilla + ".");
            return;
        }

        if (!(tipoEdificio.equals("casa") || tipoEdificio.equals("hotel") || tipoEdificio.equals("piscina") || tipoEdificio.equals("pista"))) {
            System.out.println("Error: No existe el tipo de edificio '" + tipoEdificio + "'.");
            return;
        }

        if (!casilla.getDuenho().equals(jugadorActual)) { //Comprobamos que sea el jugador actual el dueño de la casilla
            System.out.println("No se pueden vender " + tipoEdificio + " en " + casilla.getNombre() +
                    ". Esta casilla pertenece a " + casilla.getDuenho().getNombre() + ".");
            return;
        }

        if (cantidad < 1) {
            System.out.println("Error: no se pueden vender " + cantidad + " edificaciones.");
            return;
        }

        casilla.venderEdificios(tipoEdificio, cantidad, edificios);
    }

    private Grupo buscarGrupoRentable(){
                float alquilerGrupo = 0;
                float alquilerGrupoMaximo = 0;
                Grupo grupoRentable = null;
                for (String color : tablero.getGrupos().keySet()){
                    for (Casilla casilla : tablero.getGrupos().get(color).getMiembros()){
                        alquilerGrupo += casilla.getAlquilerAcumulado();
                    }
                    if (alquilerGrupo > alquilerGrupoMaximo){
                        alquilerGrupoMaximo = alquilerGrupo;
                        grupoRentable = tablero.getGrupos().get(color);

                    }
                }
                return grupoRentable;
    }

    private Casilla buscarCasillaRentable(){
        float alquilerMaximo= 0;
        Casilla casillaRentable = null;
        for (ArrayList<Casilla> lado : tablero.getPosiciones()){
            for(Casilla casilla : lado){
                if (casilla.getAlquilerAcumulado() > alquilerMaximo){
                    alquilerMaximo = casilla.getAlquilerAcumulado();
                    casillaRentable = casilla;
                }
            }
        }
        return casillaRentable;
    }

    private Casilla buscarCasillaMasFrecuentada(){
        Casilla casillaMasFrecuentada = null;
        int vecesEnCasilla = 0;
        for (ArrayList<Casilla> lado : tablero.getPosiciones()){
            for(Casilla casilla : lado){
                if (casilla.getVecesEnCasilla() > vecesEnCasilla){
                    vecesEnCasilla = casilla.getVecesEnCasilla();
                    casillaMasFrecuentada = casilla;
                }
            }
        }
        return casillaMasFrecuentada;
    }

    private Jugador buscarJugadorConMasVueltas(){
        int vueltas = 0;
        Jugador jugadorConMasVueltas = null;
        for (Jugador jugador: jugadores){
            if (jugador.getVueltas() > vueltas){
                vueltas = jugador.getVueltas();
                jugadorConMasVueltas = jugador;
            }
        }
        return jugadorConMasVueltas;
    }

    private Jugador buscarJugadorEnCabeza(){
        float valorJugadorMaximo = 0;
        float valorJugador;
        Jugador  jugadorEnCabeza = null;

        for (Jugador jugador: jugadores){
            valorJugador = jugador.getPatrimonio() + jugador.getFortuna();
            if (valorJugador > valorJugadorMaximo){
                valorJugadorMaximo = valorJugador;
                jugadorEnCabeza = jugador;
            }
        }
        return jugadorEnCabeza;
    }

    //Método para mostrar las estadísticas de un jugador
    private void mostrarEstadisticas(String[] string){
        for (Jugador jugador: jugadores){
            if(jugador.getNombre().equals(string[1])){
                System.out.println("{\n\tdineroInvertido: " + jugador.getGastos() + "," +
                        "\n\tpagoTasasEImpuestos: " + jugador.getTasasImpuestos() + "," +
                        "\n\tpagoDeAlquileres: " + jugador.getPagoAlquileres() + "," +
                        "\n\tcobroDeAlquileres: " + jugador.getCobroAlquileres() + "," +
                        "\n\tpasarPorCasillaDeSalida: " + jugador.getVueltas()*2000000 + "," +
                        "\n\tpremiosInversionesOBote: " + jugador.getPremios() +"," +
                        "\n\tvecesEnLaCarcel: " + jugador.getVecesCarcel() +
                        "\n}");
            }
        }
    }

    //Método para mostrar las estadísticas globales de la partida
    private void mostrarEstadisticasGlobales(){
        String casillaMasRentable = "";
        if (buscarCasillaRentable()!=null)
            casillaMasRentable = buscarCasillaRentable().getNombre();

        String grupoMasRentable = "";
        if (buscarGrupoRentable()!=null)
            grupoMasRentable = buscarGrupoRentable().getMiembros().getFirst().color(buscarGrupoRentable().getColorGrupo());

        String casillaMasFrecuentada = "";
        if (buscarCasillaMasFrecuentada() !=null)
            casillaMasFrecuentada = buscarCasillaMasFrecuentada().getNombre();

        String jugadorConMasVueltas = "";
        if (buscarJugadorConMasVueltas()!=null)
            jugadorConMasVueltas = buscarJugadorConMasVueltas().getNombre();

        String jugadorEnCabeza = "";
        if (buscarJugadorEnCabeza()!=null)
            jugadorEnCabeza = buscarJugadorEnCabeza().getNombre();

        System.out.println("{\n\tcasillaMasRentable: " + casillaMasRentable + "," +
                "\n\tgrupoMasRentable: " + grupoMasRentable + "," +
                "\n\tcasillaMasFrecuentada: " + casillaMasFrecuentada + "," +
                "\n\tjugadorMasVueltas: " + jugadorConMasVueltas + "," +
                "\n\tjugadorEnCabeza: " + jugadorEnCabeza +
                "\n}");
    }

    //Método para manejar las acciones de las casillas de Suerte o Caja de Comunidad
    private void manejarAcciones(String tipo) {
        Jugador jugadorActual = jugadores.get(turno);

        if (tipo.equals("Suerte")){ //Acciones para Suerte
            Acciones suerte = new Acciones(tipo);
            System.out.println(jugadorActual.getNombre() + " elige una carta: " + (countAccionesSuerte + 1) + ".");
            switch (countAccionesSuerte) { //Elegimos acción
                case 0: suerte.avanzaSolar(jugadorActual, tablero.encontrar_casilla("Solar19")); break;
                case 1: suerte.veCarcel(jugadorActual, tablero.getPosiciones()); break;
                case 2: suerte.boteLoteria(jugadorActual); break;
                case 3:
                    System.out.println("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");

                    float cobro = 250000 * jugadores.size() - 1;

                    if (jugadorActual.enBancarrota(cobro, banca)) return;

                    ventaOHipoteca(cobro);

                    suerte.elegidoPresidente(jugadorActual, jugadores); break;
                case 4: suerte.retrocedeTres(jugadorActual, tablero.getPosiciones()); break;
                case 5:
                    System.out.println("Te multan por usar el móvil mientras conduces. Paga 150.000€.");

                    if (jugadorActual.enBancarrota(150000, banca)) return;

                    ventaOHipoteca(150000);

                    suerte.multa(jugadorActual); break;
                case 6:
                    suerte.avanzaTransporte(jugadorActual, tablero.getPosiciones());

                    jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual, banca, 0);break;
                default: break;
            }
            countAccionesSuerte = (countAccionesSuerte + 1)%6; //Llevamos cuenta de las cartas
        } else { //Acciones para Caja
            Acciones caja = new Acciones(tipo);
            System.out.println(jugadorActual.getNombre() + " elige una carta: " + (countAccionesCaja + 1) + ".");
            switch (countAccionesCaja) { //Elegimos acción
                case 0:
                    System.out.println("Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.");

                    if (jugadorActual.enBancarrota(50000, banca)) return;

                    ventaOHipoteca(50000);

                    caja.balneario(jugadorActual); break;
                case 1:
                    caja.veCarcel(jugadorActual, tablero.getPosiciones()); break;
                case 2:
                    caja.colocateSalida(jugadorActual, tablero.encontrar_casilla("Salida")); break;
                case 3:
                    caja.devolucionHacienda(jugadorActual); break;
                case 4: caja.retrocedeSolar1(jugadorActual, tablero.encontrar_casilla("Solar1")); break;
                case 5: caja.avanzaSolar(jugadorActual, tablero.encontrar_casilla("Solar20")); break;
                default: break;
            }
            countAccionesCaja = (countAccionesCaja + 1)%5;
        }
        eliminarEdificiosBanca();
    }

    //Método para eliminar los edificios retirados a un jugador en bancarrota
    private void eliminarEdificiosBanca() {
        edificios.removeIf(edificio -> edificio.getDuenho().equals(banca));
    }

    private void ventaOHipoteca(float cobro) {
        Jugador jugadorActual = jugadores.get(turno);

        while (jugadorActual.getFortuna() < cobro) {
            System.out.println(jugadorActual.getNombre() + " debe vender edificios y/o hipotecar propiedades para realizar el pago.\n");
            System.out.print("Propiedades disponibles: ");
            imprimirPropiedades(jugadorActual);
            System.out.println(".\n");

            Scanner sc = new Scanner(System.in);
            String comando = sc.nextLine();
            String[] partes = comando.trim().split("[ +]+"); //Dividimos por partes el comando

            if (partes.length == 2 && partes[0].equals("hipotecar")) hipotecar(partes[1]);
            else if (partes.length == 4 && partes[0].equals("vender")) vender(partes[1], partes[2], Integer.parseInt(partes[3]));
        }

        jugadorActual.setDeudaAPagar(0); //Reseteamos la deuda
    }
}
