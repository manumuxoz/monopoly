package monopoly;

import partida.*;
import java.util.ArrayList;
import static monopoly.Valor.*;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Atributos nuevos (Edificaciones):
    private float valorCasa;
    private float valorHotel;
    private float valorPiscina;
    private float valorPistaDeporte;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPistaDeporte;
    private int numCasas = 0;
    private boolean hotel;
    private boolean piscina;
    private boolean pistaDeporte;



    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca, float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPistaDeporte) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = alquiler;
        this.hipoteca = hipoteca;
        avatares = new ArrayList<>();
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPistaDeporte = alquilerPistaDeporte;
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuesto";
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = impuesto;
        avatares = new ArrayList<>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        avatares = new ArrayList<>();
    } //MANUEL

    //Getters:
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public float getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public float getHipoteca() {
        return hipoteca;
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    //Getters atributos nuevos:
    public float getValorCasa() {
        return valorCasa;
    }

    public float getValorHotel() {
        return valorHotel;
    }

    public float getValorPiscina() {
        return valorPiscina;
    }

    public float getValorPistaDeporte() {
        return valorPistaDeporte;
    }

    public float getAlquilerCasa() {
        return alquilerCasa;
    }

    public float getAlquilerHotel() {
        return alquilerHotel;
    }

    public float getAlquilerPiscina() {
        return alquilerPiscina;
    }

    public float getAlquilerPistaDeporte() {
        return alquilerPistaDeporte;
    }

    //Setters:
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    //Setters atributos nuevos:
    public void setValorCasa(float valorCasa) {
        this.valorCasa = valorCasa;
    }

    public void setValorHotel(float valorHotel) {
        this.valorHotel = valorHotel;
    }

    public void setValorPiscina(float valorPiscina) {
        this.valorPiscina = valorPiscina;
    }

    public void setValorPistaDeporte(float valorPistaDeporte) {
        this.valorPistaDeporte = valorPistaDeporte;
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (!avatares.contains(av))
            this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        avatares.remove(av);
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
     * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        switch (tipo) {
            case "Solar":
                if (!duenho.equals(banca) && !duenho.equals(actual)) {
                    float alquiler = impuesto;
                    if (actual.getFortuna() >= alquiler) {
                        actual.sumarGastos(alquiler);
                        actual.sumarFortuna(-alquiler);
                        Jugador propietario = duenho;
                        propietario.sumarFortuna(alquiler);
                        System.out.println("Se han pagado " + alquiler + "€ de alquiler.");
                        return true;
                    } else {
                        System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el alquiler");
                        return false;
                    }
                }
                break;

            case "Servicios":
                if (!duenho.equals(banca) && !duenho.equals(actual)) {
                    int valorImpuesto = tirada * 4 * 50000;
                    if (actual.getFortuna() >= valorImpuesto) {
                        actual.sumarGastos(valorImpuesto);
                        actual.sumarFortuna(-valorImpuesto);
                        Jugador propietario = duenho;
                        propietario.sumarFortuna(valorImpuesto);
                        System.out.println("Se han pagado " + valorImpuesto + "€ de servicio.");
                        return true;
                    } else {
                        System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el servicio");
                        return false;
                    }
                }
                break;

            case "Transporte":
                if (!duenho.equals(banca) && !duenho.equals(actual)) {
                    if (actual.getFortuna() >= impuesto) {
                        actual.sumarGastos(impuesto);
                        actual.sumarFortuna(-impuesto);
                        Jugador propietario = duenho;
                        propietario.sumarFortuna(impuesto);
                        System.out.println("Se han pagado " + (int)impuesto + "€ de transporte.");
                        return true;
                    } else {
                        System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el transporte");
                        return false;
                    }
                }
                break;

            case "Impuesto":
                if (actual.getFortuna() >= impuesto) {
                    actual.sumarGastos(impuesto);
                    actual.sumarFortuna(-impuesto);

                    // Añadir al bote del Parking
                    System.out.println("El jugador paga " + (int)impuesto + "€ que se depositan en el Parking.");
                    return true;
                } else {
                    System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar impuestos");
                    return false;
                }

            case "Especiales":
                if (this.nombre.equals("Parking")) {
                    if (impuesto > 0) {
                        actual.sumarFortuna(impuesto);
                        System.out.println("El jugador " + actual.getNombre() + " recibe " + (int)impuesto + "€.");
                        this.impuesto = 0;
                    }
                    return true;
                } else if (this.nombre.equals("IrCarcel")) {
                    // Retornamos false para indicar que se debe manejar el encarcelamiento externamente
                    return false;
                }
                // Para IrCarcel, no hacemos nada aquí porque se maneja en lanzarDados()
                break;

            case "Suerte":
            case "Caja":
                // No se realiza ninguna acción - no mostrar mensaje adicional
                break;
        }
        return true;
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!duenho.equals(banca)){
            System.out.println("Esta casilla ya tiene dueño: " + duenho.getNombre());
            return;
        }
        if (solicitante.getFortuna() >= valor && !tipo.equals("Especiales") && !tipo.equals("Suerte") && !tipo.equals("Caja")) {
            solicitante.sumarGastos(valor);
            solicitante.sumarFortuna(-valor);
            banca.eliminarPropiedad(this);
            solicitante.anhadirPropiedad(this);
            this.setDuenho(solicitante);
            System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + nombre +
                    " por " + valor + "$. Su fortuna actual es " + solicitante.getFortuna() + "$");
        }

    }

    /*Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        if (nombre.equals("Parking"))
            impuesto += suma;
        if (tipo.equals("Solar"))
            valor += suma;
    }

    /*Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        switch (tipo) {
            case "Solar":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tgrupo: " + color(grupo.getColorGrupo()) +
                        ",\n\tpropietario: " + duenho.getNombre() +
                        ",\n\tvalor: " + valor +
                        ",\n\talquiler: " + impuesto +
                        ",\n\tvalor hotel: " + valorHotel +
                        ",\n\tvalor casa: " + valorCasa +
                        ",\n\tvalor piscina: " + valorPiscina +
                        ",\n\tvalor pista de deporte: " + valorPistaDeporte +
                        ",\n\talquiler casa: " + alquilerCasa +
                        ",\n\talquiler hotel: " + alquilerHotel +
                        ",\n\talquiler piscina: " + alquilerPiscina +
                        ",\n\talquiler pista de deporte: " + alquilerPistaDeporte +
                        "\n}";
            case "Transporte":
            case "Servicio":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tvalor: " + valor +
                        ",\n\talquiler: " + impuesto +
                        "\n}";
            case "Impuesto":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tapagar: " + impuesto +
                        "\n}";
            case "Especiales":
                if (nombre.equals("Parking")) {
                    System.out.print("{\n\tbote: " + impuesto + "\n\tjugadores: [");

                    if (!avatares.isEmpty()) {
                        for (int i = 0; i < avatares.size() - 1; i++) //Recorremos avatares
                            System.out.print(avatares.get(i).getJugador().getNombre() + ", ");

                        System.out.print(avatares.getLast().getJugador().getNombre());
                    }
                    System.out.println("]\n}");
                }
                if (nombre.equals("Carcel")) {
                    System.out.print("{\n\tsalir: 500000,\n\tjugadores: ");
                    if (!avatares.isEmpty())
                        for (Avatar av : avatares) //Recorremos avatares
                            System.out.print("[" + av.getJugador().getNombre() + ", " + av.getJugador().getTiradasCarcel() + "] ");
                    System.out.println("\n}");
                }
                return "";
            default:
                return "";
        }
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        // Solo mostrar si el dueño es la banca (está en venta)

        if (duenho != null && duenho.getNombre().equals("Banca")) {
            if (this.getTipo().equals("Solar"))
                return "{" +
                        "\n\tnombre: " + nombre +
                        "\n\ttipo: " + tipo +
                        ",\n\tgrupo: " + color(grupo.getColorGrupo()) +
                        ",\n\tvalor: " + valor +
                        "\n}";
            if (this.getTipo().equals("Transporte") || this.getTipo().equals("Servicios"))
                return "{" +
                        "\n\tnombre: " + nombre +
                        "\n\ttipo: " + tipo +
                        ",\n\tvalor: " + valor +
                        "\n}";
        }
        return "";
    }

    //Métodos nueos:

    //Método que devuelve el nombre del color de un grupo pasado por argumento
    private String color(String colorGrupo) {
        return switch (colorGrupo) {
            case BLACK -> "Negro";
            case CYAN -> "Cian";
            case PURPLE -> "Morado";
            case WHITE -> "Blanco";
            case RED -> "Rojo";
            case YELLOW -> "Amarillo";
            case GREEN -> "Verde";
            case BLUE -> "Azul";
            default -> "";
        };
    }

    //Método para cambiar el valor del alquiler de las casillas de tipo 'Solar'
    private void incrementarAlquiler() {
        if (numCasas == 0 && !hotel) impuesto += impuesto;
        if (hotel) impuesto = alquilerHotel;
        if (piscina) impuesto = alquilerHotel + alquilerPiscina;
        if (pistaDeporte) impuesto = alquilerHotel + alquilerPiscina + alquilerPistaDeporte;
        if (numCasas > 0) impuesto = alquilerCasa * numCasas;
    }

    private void edificarCasa(Jugador solicitante) {
        if (!duenho.equals(solicitante)) {
            System.out.println("Esta casilla pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!grupo.esDuenhoGrupo(solicitante)){ //para comprarla tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color(grupo.getColorGrupo()) + ".");
            return;
        }
        if (numCasas = 4)
            System.out.println();

    }
}