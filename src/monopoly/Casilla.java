package monopoly;

import partida.*;
import java.util.ArrayList;


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

    //Atributos nuevos:
    private float alquilerCasa;
    private float alquilerHotel;
    private boolean enVenta;
    private float alquilerPiscina;
    private float alquilerPistaDeporte;
    private String edificioConstruido;
    private int numeroCasas = 0;
    private int numeroHotel = 0;
    private int numeroPiscina = 0;
    private int numeroPistaDeporte = 0;
    private int turnoacabado = 0;


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

    public String getEdificioConstruido() {
        return edificioConstruido;
    }

    public  int getNumeroCasas() {
        return numeroCasas;
    }
    public  int getNumeroHotel() {
        return numeroHotel;
    }
    public  int getNumeroPiscina() {
        return numeroPiscina;
    }
    public  int getNumeroPistaDeporte() {
        return numeroPistaDeporte;
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

    public void setturnoacabado(int turnoacabado) {
        this.turnoacabado = turnoacabado;
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (!avatares.contains(av))
            this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (avatares.contains(av))
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
                if (!this.duenho.equals(banca) && !duenho.equals(actual)) {
                    int valorImpuesto = tirada * 4 * 50000;
                    if (actual.getFortuna() >= valorImpuesto) {
                        actual.sumarGastos(valorImpuesto);
                        actual.sumarFortuna(-valorImpuesto);
                        Jugador propietario = this.duenho;
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
                if (!this.duenho.equals(banca) && !this.duenho.equals(actual)) {
                    float alquiler = impuesto;
                    if (actual.getFortuna() >= alquiler) {
                        actual.sumarGastos(alquiler);
                        actual.sumarFortuna(-alquiler);
                        Jugador propietario = this.duenho;
                        propietario.sumarFortuna(alquiler);
                        System.out.println("Se han pagado " + (int)alquiler + "€ de transporte.");
                        return true;
                    } else {
                        System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el transporte");
                        return false;
                    }
                }
                break;

            case "Impuesto":
                float impuesto = this.impuesto;
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
                    float bote = this.impuesto;
                    if (bote > 0) {
                        actual.sumarFortuna(bote);
                        System.out.println("El jugador " + actual.getNombre() + " recibe " + (int)bote + "€.");
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
            edificioConstruido = "nada";
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
                        ",\n\tgrupo: " + grupo.getColorGrupo() +
                        ",\n\tpropietario: " + duenho.getNombre() +
                        ",\n\tvalor: " + valor +
                        ",\n\talquiler: " + impuesto +
                        ",\n\tvalor hotel: " + grupo.getPrecioHotel() +
                        ",\n\tvalor casa: " + grupo.getPrecioCasa() +
                        ",\n\tvalor piscina: " + grupo.getPrecioPiscina() +
                        ",\n\tvalor pista de deporte: " + grupo.getPrecioPistaDeporte() +
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
                        ",\n\tgrupo: " + (grupo != null ? grupo.getColorGrupo() : "null") +
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

    public void construirEdificio(Jugador solicitante, String edificio){ //funcion en la que solicitante compra el edificio
        if(!duenho.equals(solicitante)){
            System.out.println("Esta casilla tiene otro dueño: " + duenho.getNombre());
            return;
        }
        else if(!getGrupo().esDuenhoGrupo(solicitante)){ //para comprarla tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + grupo.getColorGrupo() + ".");
            return;
        }
        switch(edificio){
            case "casa":
                if ((edificioConstruido.equals("nada") || edificioConstruido.equals("1 casa") || edificioConstruido.equals("2 casas") || edificioConstruido.equals("3 casas")) && solicitante.getFortuna() >= getGrupo().getPrecioCasa() && getGrupo().esDuenhoGrupo(solicitante) && numeroCasas<4) {
                    solicitante.sumarGastos(getGrupo().getPrecioCasa());
                    solicitante.sumarFortuna(-getGrupo().getPrecioCasa());
                    System.out.println("Se ha edificado una casa en " + getNombre() + ". La fortuna de " + solicitante.getNombre() + " se reduce en " + getGrupo().getPrecioCasa());
                    impuesto = impuesto + alquilerCasa;
                    numeroCasas = numeroCasas + 1;
                    solicitante.anhadirPropiedadConEdificios(this);
                    if (numeroCasas == 1) edificioConstruido = "1 casa";
                    if (numeroCasas == 2) edificioConstruido = "2 casas";
                    if (numeroCasas == 3) edificioConstruido = "3 casas";
                    if (numeroCasas == 4) { //puede continuar comprando casas hasta que tenga 4
                        edificioConstruido = "4 casas";
                        turnoacabado = 1;
                    }
                    return;
                }
                else if(!edificioConstruido.equals("nada")) System.out.println("No se puede edificar ninguna casa más en esta casilla");
                else if(!(solicitante.getFortuna()>=getGrupo().getPrecioCasa())) System.out.println("La fortuna de " + solicitante.getNombre() + " no es suficiente para edificar una casa en la casilla " + getNombre() + ".");
                break;
            case "hotel":
                if (edificioConstruido.equals("4 casas") && solicitante.getFortuna()>= getGrupo().getPrecioHotel() && getGrupo().esDuenhoGrupo(solicitante) && turnoacabado == 0) {
                    solicitante.sumarGastos(getGrupo().getPrecioHotel());
                    solicitante.sumarFortuna(-getGrupo().getPrecioHotel());
                    System.out.println("Se ha edificado un hotel en " + getNombre() + ". La fortuna de " + solicitante.getNombre() + " se reduce en " + getGrupo().getPrecioHotel());
                    impuesto = alquilerHotel; //cuando se compra un hotel todas las casas desaparecen
                    solicitante.anhadirPropiedadConEdificios(this); //añadimos la propiedad a un array que facilita el acceso
                    edificioConstruido = "hotel";
                    numeroHotel = 1;
                    numeroCasas = 0;
                    turnoacabado = 1;
                    return;
                }
                else if(!edificioConstruido.equals("4 casas")) System.out.println("No se puede edificar un hotel ya que no se dispone de cuatro casas.");
                else if(!(solicitante.getFortuna()>=getGrupo().getPrecioCasa())) System.out.println("La fortuna de " + solicitante.getNombre() + " no es suficiente para edificar un hotel en la casilla " + getNombre() + ".");
                break;
            case "piscina":
                if (edificioConstruido.equals("hotel") && solicitante.getFortuna()>= getGrupo().getPrecioPiscina() && getGrupo().esDuenhoGrupo(solicitante) && turnoacabado == 0) {
                    solicitante.sumarGastos(getGrupo().getPrecioPiscina());
                    solicitante.sumarFortuna(-getGrupo().getPrecioPiscina());
                    System.out.println("Se ha edificado una piscina en " + getNombre() + ". La fortuna de " + solicitante.getNombre() + " se reduce en " + getGrupo().getPrecioPiscina());
                    impuesto = impuesto + alquilerPiscina;
                    solicitante.anhadirPropiedadConEdificios(this); //añadimos la propiedad a un array que facilita el acceso
                    edificioConstruido = "piscina";
                    numeroPiscina = 1;
                    turnoacabado = 1;
                    return;
                }
                else if(!edificioConstruido.equals("hotel")) System.out.println("No se puede edificar una piscina ya que no se dispone de un hotel.");
                else if(!(solicitante.getFortuna()>=getGrupo().getPrecioCasa())) System.out.println("La fortuna de " + solicitante.getNombre() + " no es suficiente para edificar una piscina en la casilla " + getNombre() + ".");
                break;
            case "pista_deporte":
                if (edificioConstruido.equals("piscina") && solicitante.getFortuna()>=getGrupo().getPrecioPistaDeporte() && getGrupo().esDuenhoGrupo(solicitante) && turnoacabado == 0) {
                    solicitante.sumarGastos(getGrupo().getPrecioPistaDeporte());
                    solicitante.sumarFortuna(-getGrupo().getPrecioPistaDeporte());
                    System.out.println("Se ha edificado una pista de deporte en " + getNombre() + ". La fortuna de " + solicitante.getNombre() + " se reduce en " + getGrupo().getPrecioPistaDeporte());
                    impuesto = impuesto + alquilerPistaDeporte;
                    solicitante.anhadirPropiedadConEdificios(this); //añadimos la propiedad a un array que facilita el acceso
                    edificioConstruido = "pistaDeporte";
                    numeroPistaDeporte = 1;
                    turnoacabado = 1;
                    return;
                }
                else if(!edificioConstruido.equals("piscina")) System.out.println("No se puede edificar una pista de deporte ya que no se dispone de una piscina.");
                else if(!(solicitante.getFortuna()>=getGrupo().getPrecioCasa())) System.out.println("La fortuna de " + solicitante.getNombre() + " no es suficiente para edificar una pista de deporte en la casilla " + getNombre() + ".");
                break;
            default:
                break;
        }

    }

}