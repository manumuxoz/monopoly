package monopoly;

import java.util.*;

import casillas.*;
import edificios.*;
import excepciones.*;
import partida.*;
import  static monopoly.Valor.*;
import consola.ConsolaNormal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Juego implements Comando {
    //Atributos:
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    public static ConsolaNormal consola;
    private ArrayList<Edificio> edificios; //Edificios creados
    public static int vueltasTotales;

//    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
//     * Parámetro: id del avatar a describir.
//     */
//    private void descAvatar(String ID) {
//    }
//
//    // Método que realiza las acciones asociadas al comando 'listar avatares'.
//    private void listarAvatares() {
//    }

    // Constructor
    public Juego() {
        iniciarPartida();

        while (true) {
            try {
                analizarComando(consola.leer("Introduce un comando: "));
            } catch (ExcepcionArgumento | ExcepcionReglas | ExcepcionBancarrota e) {
                consola.imprimir(e.getMessage());
            } catch (ExcepcionDineroInsuficiente e) {
                consola.imprimir(e.getMessage());
                ventaOHipoteca(e.getCobro());
            } catch (RuntimeException e) {
            consola.imprimir("Error: " + e.getMessage());
            }
        }
    }

    // Método para inciar una partida: crea la banca, los jugadores y avatares, tablero, dados y consola.
    private void iniciarPartida() {
        banca = new Jugador();
        iniciarBanca(banca);

        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
        tablero = new Tablero(banca);
        dado1 = new Dado();
        dado2 = new Dado();

        consola = new ConsolaNormal();
        edificios = new ArrayList<>();
    }

    //Método para inicar la banca
    private void iniciarBanca(Jugador banca) {
        banca.setNombre("Banca");
        banca.setFortuna(FORTUNA_BANCA);
        banca.setPropiedades(new ArrayList<>());
    }

    /*Método que interpreta el comando introducido y toma la accion correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) throws Excepcion {
        String[] partes = comando.trim().split("[ +]+"); //Dividimos por partes el comando

        switch (partes[0]) {
            case "acabar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (partes.length != 2 || !partes[1].equals("turno"))
                    throw new ExcepcionArgumento("Uso: acabar turno");

                acabarTurno();
                break;
            case "comandos":
                if (partes.length != 1)
                    throw new ExcepcionArgumento("Uso: comandos");

                leerComandos();
                break;
            case "comprar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (partes.length != 2)
                    throw new ExcepcionArgumento("Uso: comprar <nombre>");

                comprar(partes[1]);
                break;
            case "crear":
                if (partes.length != 4 || !partes[1].equals("jugador"))
                    throw new ExcepcionArgumento("Uso: crear jugador <nombre> <tipo>");

                crearJugador(partes);
                break;
            case "describir":
                if (partes.length != 3 || !(partes[1].equalsIgnoreCase("casilla") || partes[1].equalsIgnoreCase("jugador")))
                    throw new ExcepcionArgumento("Uso: describir casilla/jugador <nombre>");

                switch (partes[1]) {
                    case "casilla" -> descCasilla(partes[2]);
                    case "jugador" -> descJugador(partes[2]);
                }
                break;
            case "deshipotecar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (partes.length != 2)
                    throw new ExcepcionArgumento("Uso: deshipotecar <nombre>");

                deshipotecar(partes[1]);
                break;
            case "edificar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (partes.length != 2)
                    throw new ExcepcionArgumento("Uso: edificar <tipo>");

                edificar(partes[1]);
                break;
            case "hipotecar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (partes.length != 2)
                    throw new ExcepcionArgumento("Uso: hipotecar <nombre>");

                hipotecar(partes[1]);
                break;
            case "jugador":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                indicarTurnoJugador();
                break;
            case "lanzar":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                if (!(partes.length == 2 || partes.length == 4) || !partes[1].equals("dados"))
                    throw new ExcepcionArgumento("Uso: lanzar  (x+y)");

                if (partes.length == 2)
                    lanzarDados(-1,-1);
                else
                    lanzarDados(Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
                break;
            case "listar":
                listar(partes);
                break;
            case "estadisticas":
                estadisticas(partes);
                break;
            case "salir":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                salirCarcel();
                break;
            case "vender":
                if (!esPartidaIniciada())
                    throw new ExcepcionReglas("La partida no está iniciada. Número de jugadores: " + jugadores.size());

                vender(partes[1], partes[2], Integer.parseInt(partes[3]));
                break;
            case "ver":
                if (partes.length != 2 || !partes[1].equals("tablero"))
                    throw new ExcepcionArgumento("Uso: ver tablero");

                consola.imprimir(verTablero());
                break;
            default:
                throw new ExcepcionArgumento("'" +  comando + "' no es un comando válido.");
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    @Override
    public void acabarTurno() {
        turno = (turno + 1) % jugadores.size(); //Aritmética modular
        consola.imprimir("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
        lanzamientos = 0;
    }

    //Método para crear un jugador
    @Override
    public void crearJugador(String partes[]) throws Excepcion {
        if (jugadores.size() == 4)
            throw new ExcepcionReglas("Número máximo de jugadores alcanzado");

        if (!avatarValido(partes[3]))
            throw new ExcepcionReglas("El avatar '" + partes[3] + "' no válido. Avatares disponible: " + avataresDisponibles() + ".");

        jugadores.add(new Jugador(partes[2], partes[3], tablero.encontrarCasilla("Salida"), avatares));
    }

    //Método que devuelve si un avatar es valido
    private boolean avatarValido(String tipoAvatar) {
        for (Jugador jugador : jugadores)
            if (jugador.getAvatar().getTipo().equalsIgnoreCase(tipoAvatar))
                return false;

        return tipoAvatar.equalsIgnoreCase("sombrero") ||
                tipoAvatar.equalsIgnoreCase("pelota") ||
                tipoAvatar.equalsIgnoreCase("coche") ||
                tipoAvatar.equalsIgnoreCase("esfinge");
    }

    //Método que devuelve un String con los avatares disponibles
    private String avataresDisponibles() {
        ArrayList<String> avUsados = new ArrayList<>();

        for (Jugador j : jugadores)
            avUsados.add(j.getAvatar().getTipo());

        StringBuilder sb = new StringBuilder();
        String separador = "";

        if (!avUsados.contains("coche")) {
            sb.append(separador).append("coche");
            separador = ", ";
        }

        if (!avUsados.contains("esfinge")) {
            sb.append(separador).append("esfinge");
            separador = ", ";
        }

        if (!avUsados.contains("pelota")) {
            sb.append(separador).append("pelota");
            separador = ", ";
        }
        
        if (!avUsados.contains("sombrero")) {
            sb.append(separador).append("sombrero");
            separador = ", ";
        }
        
        return sb.toString();
    }

    //Método que devuelve si hay el número suficiente de jugadores para iniciar la partida (mín. 2)
    private boolean esPartidaIniciada() {
        return jugadores.size() >= 2;
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    @Override
    public void comprar(String nombre) throws Excepcion {
        Casilla casilla = tablero.encontrarCasilla(nombre);

        if (casilla == null)
            throw new ExcepcionArgumento("La casilla '" + nombre + "' no existe.");

        Jugador actual = jugadores.get(turno);

        if (!casilla.getTipo().equalsIgnoreCase("Solar") || !casilla.getTipo().equalsIgnoreCase("Transporte") ||
            !casilla.getTipo().equalsIgnoreCase("Servicios"))
            throw new ExcepcionReglas("La casilla '" + casilla.getNombre() + "' es de tipo " + casilla.getTipo() +
                    ". Solo se peuden comprar casillas de tipo: Solar, Transporte o Servicios.");

        ((Propiedad) casilla).comprarCasilla(actual, banca);

        consola.imprimir("El jugador " + actual.getNombre() + " compra la casilla " + casilla.getNombre() + " por "
                + ((Propiedad) casilla).valor() + "$. Su fortuna actual es " + actual.getFortuna() + "$.");
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    @Override
    public void descCasilla(String nombre) throws Excepcion {
        Casilla casilla = tablero.encontrarCasilla(nombre);

        if (casilla == null)
            throw new ExcepcionArgumento("La casilla '" + nombre + "' no existe.");

        consola.imprimir(casilla.infoCasilla());
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    @Override
    public void descJugador(String nombre) throws Excepcion {
        Jugador jugador = null;

        for (Jugador j : jugadores)
            if (j.getNombre().equals(nombre))
                jugador = j;

        if (jugador == null)
            throw new ExcepcionArgumento("El jugador '" + nombre + "' no existe.");

        consola.imprimir("{\n\tnombre: " + jugador.getNombre() +
                ",\n\tavatar: " + jugador.getAvatar().getId() +
                ",\n\tfortuna: " + (int) jugador.getFortuna() +
                ",\n\tpropiedades: " + imprimirPropiedades(jugador) +
                ",\n\thipotecas: " + imprimirHipotecas(jugador) +
                ",\n\tedificios: " + imprimirEdificios(jugador) +
                "\n}");
    }

    //Método para imprimir las propiedades de un jugador dado
    private String imprimirPropiedades(Jugador jugador) {
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";
        for (Propiedad propiedad : jugador.getPropiedades()) {
            if(!((Solar) propiedad).estaHipotecada()) {
                sb.append(separador).append(propiedad.getNombre());
                separador = ", ";
            }
        }

        return sb.append("]").toString();
    }

    //Método que devuelve un String con las hipotecas de un jugador
    private String imprimirHipotecas(Jugador jugador) {
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";

        for (Propiedad propiedad : jugador.getHipotecas()) {
            sb.append(separador).append(propiedad.getNombre());
            separador = ", ";
        }

        return sb.append("]").toString();
    }

    //Método para imprimir los IDs de los edificios de un jugador
    private String imprimirEdificios(Jugador jugador) {
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";

        for (Edificio ed : jugador.getEdificios()) {
            sb.append(separador).append(ed.getID());
            separador = ", ";
        }

        return sb.append("]").toString();
    }

    //Método para deshipotecar una casilla
    @Override
    public void deshipotecar(String nombre) throws Excepcion {
        Jugador actual = jugadores.get(turno);
        Casilla hipoteca = tablero.encontrarCasilla(nombre);

        if (hipoteca == null) //Si no se ha asignado, es que no existe
            throw new ExcepcionArgumento("La casilla '" + nombre + "' no existe.");

        if (!hipoteca.getDuenho().equals(actual)) //Comprobamos que sea el jugador actual el dueño de la casilla
            throw new ExcepcionReglas(actual.getNombre() + " no puede deshipotecar " + hipoteca.getNombre() +
                    ". Esta casilla pertenece a " + hipoteca.getDuenho().getNombre() + ".");

        if (hipoteca.getTipo().equals("Transporte") || hipoteca.getTipo().equals("Servicios"))
            throw new ExcepcionReglas(actual.getNombre() + " no puede deshipotecar " + hipoteca.getNombre() + ". Solo se pueden hipotecar propiedades de tipo Solar.");

        ((Solar) hipoteca).deshipotecar(actual);

        StringBuilder sb = new StringBuilder();
        for (Solar s : hipoteca.getGrupo().getMiembros()) {
            if (s.estaHipotecada()) {
                sb.append("Aún no puede recibir alquileres en el grupo: ").append(s.color()).append(". Quedan solares sin desipotecar.");
                break;
            }

            sb.append("Ahora puede recibir alquileres en el grupo: ").append(s.color()).append(".");
        }

        consola.imprimir(actual.getNombre() + " paga " + ((Solar)hipoteca).getHipoteca() + "$ por deshipotecar " +
                hipoteca.getNombre() + "." + sb);
    }

    //Método para edificar un tipo de edificio dado
    @Override
    public void edificar(String tipo) throws Excepcion {
        if (!(tipo.equalsIgnoreCase("Casa") ||  tipo.equalsIgnoreCase("Hotel") ||
                tipo.equalsIgnoreCase("Piscina") ||  tipo.equalsIgnoreCase("Pista")))
            throw new ExcepcionArgumento("El tipo de edificio '" + tipo + "' no existe. Se pueden edificar: " +
                    "casas (casa), un hotel (hotel), una piscina (piscina) o una pista de deporte (pista).");

        Jugador actual = jugadores.get(turno);
        Casilla casilla = actual.getAvatar().getLugar();

        if (!casilla.getTipo().equals("Solar"))
            throw new ExcepcionReglas("La casilla " + casilla.getNombre() + " es de tipo " + casilla.getTipo() +
                    ". Solo se pueden hipotecar propiedades de tipo Solar.");

        ((Solar) casilla).edificar(actual, tipo, edificios);

        float valor = 0;
        switch (tipo) {
            case "casa" -> valor = ((Solar) casilla).getValorCasa();
            case "hotel" -> valor = ((Solar) casilla).getValorHotel();
            case "piscina" -> valor = ((Solar) casilla).getValorPiscina();
            case "pista" -> valor = ((Solar) casilla).getValorPistaDeporte();
        }

        consola.imprimir("Se ha edificado " + tipo + " en " + casilla.getNombre() + ". La fortuna de " + actual.getNombre() +
                " se reduce en " + valor + "$.");
    }

    //Método para hipotecar una casilla
    @Override
    public void hipotecar(String nombreCasilla) throws Excepcion {
        Jugador actual = jugadores.get(turno);
        Casilla hipoteca = tablero.encontrarCasilla(nombreCasilla);

        if (hipoteca == null)  //Si no se ha asignado, es que no existe
            throw new ExcepcionArgumento("Error: No existe la casilla " + nombreCasilla + ".");

        if (!((Propiedad) hipoteca).perteneceAJugador(actual)) //Comprobamos que sea el jugador actual el dueño de la casilla
            throw new ExcepcionReglas(actual.getNombre() + " no puede hipotecar " + hipoteca.getNombre() +
                    ". Esta casilla pertenece a " + hipoteca.getDuenho().getNombre() + ".");

        if (hipoteca.getTipo().equals("Transporte") || hipoteca.getTipo().equals("Servicios"))
            throw new ExcepcionReglas(actual.getNombre() + " no puede hipotecar " + hipoteca.getNombre() + ". Solo se pueden hipotecar propiedades de tipo 'Solar'.");

        ((Solar) hipoteca).hipotecar(actual);

        System.out.println(actual.getNombre() + " recibe " + ((Solar)hipoteca).getHipoteca() + "$ por la hipoteca de " +
                hipoteca.getNombre() + ". No puede recibir alquileres ni edificar en el grupo: " + ((Solar)hipoteca).color() + ".");
    }

    // Muestra el jugador que está en juego en ese momento
    @Override
    public void indicarTurnoJugador() {
        Jugador actual = jugadores.get(turno);
        consola.imprimir("{\n\tnombre: " + actual.getNombre() + ",\n\tavatar: " + actual.getAvatar().getId() + "\n}");
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    @Override
    public void lanzarDados(int tirada1, int tirada2) throws Excepcion {
        Jugador actual = jugadores.get(turno);

        if (actual.getEnBancarrota())
            throw new ExcepcionBancarrota(actual.getNombre() + " está en banccarrota. No puede realizar la tirada.");

        if (!puedeTirar())
            throw new ExcepcionReglas("Ya tiraste este turno. Usa 'acabar turno' para continuar.");

        if (tirada1 == -1 && tirada2 == -1) { //Tirada aleatoria
            tirada1 = dado1.hacerTirada();
            tirada2 = dado2.hacerTirada();
        }

        if ((tirada1 < 0 || tirada1 > 6) && (tirada2 < 0 || tirada2 > 6)) //Tirada manual
            throw new ExcepcionReglas("Tirada no válida. Valores entre 1 y 6");

        int valorTirada = tirada1 + tirada2;
        consola.imprimir("Tirada: " + tirada1 + " + " + tirada2 + " = " + valorTirada);

        // Verificar si el jugador está en la cárcel
        if (actual.getEnCarcel())
            manejarCarcel(actual, tirada1 == tirada2);
        else {
            manejarDobles(actual, tirada1 == tirada2);
            actual.getAvatar().moverAvatar(tablero.getPosiciones(), valorTirada);
        }

        Casilla cas = actual.getAvatar().getLugar();

        if (!cas.evaluarCasilla(actual, banca, valorTirada) && !actual.getEnBancarrota())
            throw new ExcepcionDineroInsuficiente(actual.getNombre() + " debe vender edificios y/o hipotecar propiedades para realizar el pago.", actual.getDeudaAPagar());
        else if (actual.getEnBancarrota())
            throw new ExcepcionBancarrota(actual.getNombre() + " está en bancarrota. Todas sus propiedades ahora pertenecen a " + cas.getDuenho().getNombre() + ".");

        if (cas.equals(tablero.encontrarCasilla("Impuesto")))
            ((Parking) tablero.encontrarCasilla("Parking")).sumarBote(cas.getImpuesto());
        else if (cas.equals(tablero.encontrarCasilla("IrCarcel")))
            actual.encarcelar(tablero.getPosiciones());

        consola.imprimir(imprimirPosiciones());
    }

    //Método para saber si un jugador puede tirar
    private boolean puedeTirar() {
        return !(lanzamientos == -1);
    }

    /*
     * Método para manejar situaciones cuando un jugador está en la cárcel.
     * Parámetro: jugador que está en la cárcel.
     * */
    private void manejarCarcel(Jugador jugador, boolean dobles) {
        jugador.setTiradasCarcel(jugador.getTiradasCarcel() + 1);
        lanzamientos = -1;

        if (jugador.getTiradasCarcel() == 3 || dobles) {
            jugador.setenCarcel(false);
            jugador.setTiradasCarcel(0);
            consola.imprimir(jugador.getNombre() + " sale de la cárcel.");

            if (dobles) //Si sacó doble puede volver a tirar
                lanzamientos = 0;
        } else {
            consola.imprimir(jugador.getNombre() + " está en la cárcel. Turno " +
                    jugador.getTiradasCarcel() + "/3. Use 'salir carcel' para pagar fianza.");
        }
    }

    //Método para manejar las situaciones en que la tirada ha sido dobles
    private void manejarDobles(Jugador actual, boolean sonDobles) {
        if (sonDobles) {
            lanzamientos++; // Aumentar contador de dobles consecutivos
            consola.imprimir("¡Dobles! Llevas " + lanzamientos + " dobles consecutivos.");

            // Si son 3 dobles consecutivos, ir a la cárcel
            if (lanzamientos == 3) {
                consola.imprimir("¡3 dobles consecutivos! " + actual.getNombre() + " va a la cárcel.");
                actual.encarcelar(tablero.getPosiciones());
                lanzamientos = -1; // Resetear contador
                consola.imprimir(actual.getNombre() + " ha sido enviado a la carcel.");
            }
        } else
            lanzamientos = -1; // Resetear contador si no son dobles
    }

    // Método para mostrar por pantalla información de la posición de cada jugador
    private String imprimirPosiciones() {
        StringBuilder sb = new StringBuilder().append("=== POSICIONES ACTUALES ===\n");
        // Imprimimos las posiciones actuales para facilitar el control de los jugadores
        for (Jugador jugador : jugadores) {
            Avatar avatar = jugador.getAvatar();
            sb.append(jugador.getNombre()).append(" (").append(avatar.getId()).append(") en: ").append(avatar.getLugar().getNombre()).append("\n");
        }
        sb.append("===========================");

        return sb.toString();
    }

    //Método para leer los comandos de un archivo
    @Override
    public void leerComandos() throws  RuntimeException{
        String ruta = System.getProperty("user.dir") + "/comandos.txt"; //Buscamos el directorio de trabajo y añadimos el archivo
        try (BufferedReader br = (new BufferedReader(new FileReader(ruta)))) {
            String comando;
            while ((comando = br.readLine()) != null) { //Leemos cada línea y analizamos comando
                System.out.println("Comando: " + comando);
                analizarComando(comando);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listar(String partes[]) throws Excepcion{
        if(partes.length == 2){
            if (!(partes[1].equalsIgnoreCase("jugadores") || partes[1].equalsIgnoreCase("edificios") || partes[1].equalsIgnoreCase("enventa")))
                throw new ExcepcionArgumento("Uso: listar grupo <color> /jugadores/edificios/enventa");
            else {
                if (partes[1].equalsIgnoreCase("jugadores")) listarJugadores();
                else if (partes[1].equalsIgnoreCase("enventa")) listarVenta();
                else if (partes[1].equalsIgnoreCase("edificios")) listarEdificios();
            }
        }
        else if (!(partes.length ==  3 || partes[1].equalsIgnoreCase("grupo"))) {
            throw new ExcepcionArgumento("Uso: listar grupo <color> /jugadores/edificios/enventa");
        }
    }

    //Método para listar todos los edificios construidos
    @Override
    public void listarEdificios() throws Excepcion{
        if (edificios.isEmpty()) {
            throw new ExcepcionReglas("No hay edificios para listar.");
        }
            for (Edificio edificio : edificios) {
                consola.imprimir(edificio.infoEdificio());
        }
    }

    //Método para listar los métodos de un grupo y saber qué edificios se pueden construir
    @Override
    public void listarEdificiosGrupo(String colorGrupo) throws Excepcion{
        int countCasas = 0, countHoteles = 0, countPiscina = 0, countPista = 0; //Variables para llevar cuenta de los edificios construidos
        String color = Character.toUpperCase(colorGrupo.charAt(0)) + colorGrupo.substring(1); //Formatemamos el color pasado por comando

        if (tablero.getGrupos().get(color) == null) { //Comprobamos que exista el color
            throw new ExcepcionArgumento("Error: No existe el grupo de color " + color + ".");
        }
        for (Casilla solar : tablero.getGrupos().get(color).getMiembros()) { //Iteramos sobre el arraylist de casillas del grupo
            System.out.println(((Solar)solar).infoEdificios());
            countCasas += ((Solar)solar).contarCasas();
            if (((Solar)solar).existeHotel()) countHoteles++;
            if (((Solar)solar).existePiscina()) countPiscina++;
            if (((Solar)solar).existePistaDeporte()) countPista++;
        }

        int numSolares = tablero.getGrupos().get(color).getMiembros().size(); //Número de solares
        consola.imprimir("Se pueden edificar " + (numSolares * 4 - countCasas) + " casas, " +
                (numSolares - countHoteles) + " hoteles, " + (numSolares - countPiscina) + " piscinas y " +
                (numSolares - countPista) + " pistas.");
    }


    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    @Override
    public void listarJugadores() throws Excepcion{
        if (jugadores.isEmpty()) {
            throw new ExcepcionArgumento("No hay jugadores en la partida");
        }
        for (Jugador jugador : jugadores) {
            consola.imprimir("{\n\tnombre: " + jugador.getNombre() +
                    ",\n\tavatar: " + jugador.getAvatar().getId() +
                    ",\n\tfortuna: " + jugador.getFortuna() +
                    ",\n\tpropiedades: [");

            imprimirPropiedades(jugador); //Imprimimos las propiedades

            consola.imprimir("],\n\thipotecas: [],\n\tedificios: [");

            imprimirEdificios(jugador); //Imprimimos los edificios

            consola.imprimir("]\n}\n");
        }

    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    @Override
    public void listarVenta(){  //Imprime las casillas que están en venta
        for (ArrayList<Casilla> lado : tablero.getPosiciones())
            for (Casilla casilla : lado)
                consola.imprimir(((Solar) casilla).casEnVenta());  //print y no println porque si no al hacer un return vacio ocupa una línea
    }



    //Método para mostrar las estadísticas de un jugador
    @Override
    public void mostrarEstadisticas(String[] string) throws Excepcion{
        Jugador jugador = null;
        for (Jugador j: jugadores){
            if(j.getNombre().equals(string[1])){
                jugador = j;
            }
        }
        if (jugador == null){
            throw new ExcepcionArgumento("No existe el jugador" + string[1] + ".");
        }

        System.out.println("{\n\tdineroInvertido: " + jugador.getGastos() + "," +
                "\n\tpagoTasasEImpuestos: " + jugador.getTasasImpuestos() + "," +
                "\n\tpagoDeAlquileres: " + jugador.getPagoAlquileres() + "," +
                "\n\tcobroDeAlquileres: " + jugador.getCobroAlquileres() + "," +
                "\n\tpasarPorCasillaDeSalida: " + jugador.getVueltas()*2000000 + "," +
                "\n\tpremiosInversionesOBote: " + jugador.getPremios() +"," +
                "\n\tvecesEnLaCarcel: " + jugador.getVecesCarcel() +
                "\n}");
    }

    //Método para mostrar las estadísticas globales de la partida
    @Override
    public void mostrarEstadisticasGlobales(){
        String casillaMasRentable = "";
        String grupoMasRentable = "";
        String casillaMasFrecuentada = "";
        String jugadorConMasVueltas = "";
        String jugadorEnCabeza = "";

        if (buscarCasillaRentable()==null)
            throw new ExcepcionArgumento("No se ha encontrado casilla mas rentable.");

        if (buscarGrupoRentable()==null)
            throw new ExcepcionArgumento("No se ha encontrado grupo mas rentable");

        if (buscarCasillaMasFrecuentada() ==null)
            throw new ExcepcionArgumento("No se ha encontrado casilla mas frecuentada.");

        if (buscarJugadorConMasVueltas()==null)
            throw new ExcepcionArgumento("No se ha encontrado jugador con mas vueltas");

        if (buscarJugadorEnCabeza()==null)
            throw new ExcepcionArgumento("No se ha encontrado jugador en cabeza");

        casillaMasRentable = buscarCasillaRentable().getNombre();
        grupoMasRentable = buscarGrupoRentable().getMiembros().getFirst().color();
        casillaMasFrecuentada = buscarCasillaMasFrecuentada().getNombre();
        jugadorConMasVueltas = buscarJugadorConMasVueltas().getNombre();
        jugadorEnCabeza = buscarJugadorEnCabeza().getNombre();

        System.out.println("{\n\tcasillaMasRentable: " + casillaMasRentable + "," +
                "\n\tgrupoMasRentable: " + grupoMasRentable + "," +
                "\n\tcasillaMasFrecuentada: " + casillaMasFrecuentada + "," +
                "\n\tjugadorMasVueltas: " + jugadorConMasVueltas + "," +
                "\n\tjugadorEnCabeza: " + jugadorEnCabeza +
                "\n}");
    }

    private void estadisticas(String partes[]){
        if (!((partes[1].equalsIgnoreCase("jugadores")) || (partes[1].equalsIgnoreCase("enventa") || partes[1].equalsIgnoreCase("edificios")) || (partes[2].equalsIgnoreCase("jugadores"))) && !(partes[2].equalsIgnoreCase("grupo"))) {
            throw new ExcepcionArgumento("Uso: listar jugadores/edificios/enventa");
        }
        if(partes.length == 2){
            if(partes[1].equalsIgnoreCase("jugadores")) listarJugadores();
            else if (partes[1].equalsIgnoreCase("enventa")) listarVenta();
            else if (partes[1].equalsIgnoreCase("edificios")) listarEdificios();
        }
        else listarEdificiosGrupo(partes[3]);
    }



    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    @Override
    public void salirCarcel() throws Excepcion {
        Jugador jugadorActual = jugadores.get(turno);
        if (!(jugadorActual.getFortuna() >= 500000))
            throw new ExcepcionDineroInsuficiente("El jugador " + jugadorActual.getNombre() + " no tiene suficiente dinero", 0);
        if (!jugadorActual.getEnCarcel())
            throw new ExcepcionReglas("El jugador no está en la carcel");

        jugadorActual.setenCarcel(false);
        jugadorActual.sumarGastos(500000);
        jugadorActual.sumarFortuna(-500000);
        jugadorActual.sumarTasasImpuestos(500000);
        ((Parking) tablero.encontrarCasilla("Parking")).sumarBote(500000);
        System.out.println(jugadorActual.getNombre() + " paga 500.000$ y sale de la carcel. Puede lanzar los dados.");
    }


    //Método para vender edificios de una casilla
    @Override
    public void vender(String tipoEdificio, String nombreCasilla, int cantidad) throws Excepcion{
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrarCasilla(nombreCasilla);

        if (casilla == null)
            throw new ExcepcionArgumento("Error: No existe la casilla " + nombreCasilla + ".");

        if (!(tipoEdificio.equals("casa") || tipoEdificio.equals("casas") || tipoEdificio.equals("hotel") || tipoEdificio.equals("piscina") || tipoEdificio.equals("pista")))
            throw new ExcepcionArgumento("Error: No existe el tipo de edificio '" + tipoEdificio + "'.");

        if (!((Propiedad) casilla).perteneceAJugador(jugadorActual)) { //Comprobamos que sea el jugador actual el dueño de la casilla
            throw new ExcepcionReglas("No se pueden vender " + tipoEdificio + " en " + casilla.getNombre() +
                    ". Esta casilla pertenece a " + casilla.getDuenho().getNombre() + ".");
        }

        if (cantidad < 1) {
            throw new ExcepcionArgumento("Error: no se pueden vender " + cantidad + " edificaciones.");
        }

        ((Solar)casilla).venderEdificios(tipoEdificio, cantidad, edificios);
    }



    private Grupo buscarGrupoRentable(){
        float alquilerGrupo = 0;
        float alquilerGrupoMaximo = 0;
        Grupo grupoRentable = null;
        for (String color : tablero.getGrupos().keySet()){
            for (Casilla casilla : tablero.getGrupos().get(color).getMiembros()) {
                alquilerGrupo += ((Propiedad)casilla).getAlquilerAcumulado();
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
                if (((Propiedad)casilla).getAlquilerAcumulado() > alquilerMaximo){
                    alquilerMaximo = ((Propiedad)casilla).getAlquilerAcumulado();
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
                if (casilla.getFrecuenciaVisita() > vecesEnCasilla){
                    vecesEnCasilla = casilla.getFrecuenciaVisita();
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

    //Método para manejar las acciones de las casillas de Suerte o Caja de Comunidad
    private void manejarAcciones(String tipo) {
        Jugador jugadorActual = jugadores.get(turno);

        if (tipo.equals("Suerte")){ //Acciones para Suerte
            cartas.Suerte suerte = new cartas.Suerte();
            consola.imprimir(jugadorActual.getNombre() + " elige una carta: " + (countAccionesSuerte + 1) + ".");
            switch (countAccionesSuerte) { //Elegimos acción
                case 0: suerte.avanzaSolar(jugadorActual, (Solar)tablero.encontrarCasilla("Solar19")); break;
                case 1: suerte.veCarcel(jugadorActual, tablero.getPosiciones()); break;
                case 2: suerte.boteLoteria(jugadorActual); break;
                case 3:
                    consola.imprimir("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");

                    float cobro = 250000 * jugadores.size() - 1;

                    if (jugadorActual.enBancarrota(cobro, banca)) return;

                    ventaOHipoteca(cobro);

                    suerte.elegidoPresidente(jugadorActual, jugadores); break;
                case 4: suerte.retrocedeTres(jugadorActual, tablero.getPosiciones()); break;
                case 5:
                    consola.imprimir("Te multan por usar el móvil mientras conduces. Paga 150.000€.");

                    if (jugadorActual.enBancarrota(150000, banca)) return;

                    ventaOHipoteca(150000);

                    tablero.encontrarCasilla("Parking").setImpuesto(tablero.encontrarCasilla("Parking").getImpuesto() + 150000);

                    suerte.multa(jugadorActual); break;
                case 6:
                    suerte.avanzaTransporte(jugadorActual, tablero.getPosiciones());

                    jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual, banca, 0);break;
                default: break;
            }
            countAccionesSuerte = (countAccionesSuerte + 1)%6; //Llevamos cuenta de las cartas
        } else { //Acciones para Caja
            cartas.CajaComunidad caja = new cartas.CajaComunidad();
            consola.imprimir(jugadorActual.getNombre() + " elige una carta: " + (countAccionesCaja + 1) + ".");
            switch (countAccionesCaja) { //Elegimos acción
                case 0:
                    consola.imprimir("Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.");

                    if (jugadorActual.enBancarrota(50000, banca)) return;

                    ventaOHipoteca(50000);

                    caja.balneario(jugadorActual); break;
                case 1:
                    caja.veCarcel(jugadorActual, tablero.getPosiciones()); break;
                case 2:
                    caja.colocateSalida(jugadorActual, tablero.encontrarCasilla("Salida")); break;
                case 3:
                    caja.devolucionHacienda(jugadorActual); break;
                case 4: caja.retrocedeSolar1(jugadorActual, (Solar)tablero.encontrarCasilla("Solar1")); break;
                case 5: caja.avanzaSolar(jugadorActual, (Solar)tablero.encontrarCasilla("Solar20")); break;
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
        Jugador actual = jugadores.get(turno);

        while (actual.getFortuna() < cobro) {
            consola.imprimir("Edificios: " + imprimirPropiedades(actual));
            consola.imprimir("Propiedades: " + imprimirPropiedades(actual));

            String[] partes = consola.leer("Vender/Hipotecar: ").trim().split("[ +]+"); //Dividimos por partes el comando

            if (partes.length == 2 && partes[0].equals("hipotecar")) hipotecar(partes[1]);
            else if (partes.length == 4 && partes[0].equals("vender")) vender(partes[1], partes[2], Integer.parseInt(partes[3]));
        }

        consola.imprimir(actual.getNombre() + " ha pagado la deuda: " + cobro + "$.");

        actual.setDeudaAPagar(0); //Reseteamos la deuda
}

    @Override
    public String verTablero() {
        return tablero.toString();
    }

}