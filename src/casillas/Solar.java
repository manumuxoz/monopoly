package casillas;

import edificios.*;
import excepciones.*;
import partida.Jugador;
import java.util.ArrayList;
import static monopoly.Valor.*;
import static monopoly.Juego.consola;

public final class Solar extends Propiedad {
    private float valorCasa;
    private float valorHotel;
    private float valorPiscina;
    private float valorPistaDeporte;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPistaDeporte;
    private ArrayList<Edificio> edificios;
    private boolean hipotecado;
    private float hipoteca; //Valor otorgado por hipotecar una casilla

    public Solar() {}

    public Solar(String nombre, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca, float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPistaDeporte) {
        super(nombre, "Solar", posicion, valor, duenho, alquiler);

        this.hipoteca = hipoteca;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPistaDeporte = alquilerPistaDeporte;
        edificios = new ArrayList<>();
    }

    //Getters:
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
    public float getAlquilerPiscina() {return alquilerPiscina;}
    public float getAlquilerPistaDeporte() {
        return alquilerPistaDeporte;
    }
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }
    public float getHipoteca() {
        return hipoteca;
    }

    //Setters:
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

    //Método para eliminar las casas de una casilla
    private void eliminarCasas(ArrayList<Edificio> edificiosCreados) {
        edificiosCreados.removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("casa"));
        edificios.removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("casa"));
        getDuenho().getEdificios().removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("casa"));
    }

    //Método que devuelve información sobre los edificios construidos en una casilla
    public String infoEdificios() {
        return "{\n\tpropiedad: " + getNombre() +
                ",\n\t" + imprimirEdificios() +
                ",\n\talquiler: " + getImpuesto() +
                "\n}";
    }

    //Método que devuelve un String de los edificios que se encuentran en la casilla
    private String imprimirEdificios() {
        StringBuilder sbCasas = new StringBuilder().append("["); //Creamos stringbuilders independientes para luego unirlos
        StringBuilder sbHotel = new StringBuilder().append("[");
        StringBuilder sbPiscina = new StringBuilder().append("[");
        StringBuilder sbPista = new StringBuilder().append("[");
        String separador = "";

        for (Edificio edificio : edificios) {
            switch (edificio.getTipo()) {
                case "piscina": sbPiscina.append(edificio.getID()); break;
                case "pista": sbPista.append(edificio.getID()); break;
                case "hotel": sbHotel.append(edificio.getID()); break;
                case "casa": sbCasas.append(separador).append(edificio.getID()); separador = ", "; break; //Si hay varias casas las separamos por comas
            }
        }
        return "casas: " + sbCasas + "],\n\thotel: " + sbHotel + "],\n\tpiscina: " + sbPiscina + "],\n\tpista: " + sbPista + "]"; //Unimos
    }

    //Método para vender edificios
    public void venderEdificios(String tipoEdificio, int cantidad, ArrayList<Edificio> edificiosCreados) {
        if (edificios.isEmpty())
            throw new ExcepcionArgumento("No hay edificios para vender en " + getNombre() + ".");

        StringBuilder sb = new StringBuilder();
        float venta = 0;

        switch (tipoEdificio) {
            case "casa": case "casas":
                if (existeHotel()) {
                    sb.append("No se pueden vender casas en ").append(getNombre()).append(". Antes hay que vender el hotel");
                    break;
                }
                if (cantidad > contarCasas()) {
                    sb.append("Solamente se pueden vender " + contarCasas() + " casas");
                    cantidad = contarCasas();
                } else
                    sb.append(getDuenho().getNombre()).append(" ha vendido ").append(cantidad).append(" casas en ").append(getNombre());

                venta = venderCasas(cantidad, edificiosCreados);
                break;
            case "hotel":
                if (!existeHotel()) {
                    sb.append(getNombre()).append(" no tiene ningún hotel construido");
                    break;
                }
                if (existePiscina()) {
                    sb.append("No se puede vender el hotel en ").append(getNombre()).append(". Antes hay que vender la piscina");
                    break;
                }
                if (cantidad > 1)
                    sb.append("Solamente se puede vender 1 hotel");
                else
                    sb.append(getDuenho().getNombre()).append(" ha vendido 1 hotel en ").append(getNombre());

                venta = venderHotel(edificiosCreados);
                break;
           case "piscina":
               if (!existePiscina()) {
                   sb.append(getNombre()).append(" no tiene ninguna piscina construida");
                   break;
               }
               if (existePistaDeporte()) {
                   sb.append("No se puede vender la piscina en ").append(getNombre()).append(". Antes hay que vender la pista de deporte");
                   break;
               }
               if (cantidad > 1)
                   sb.append("Solamente se puede vender 1 piscina");
               else
                   sb.append(getDuenho().getNombre()).append(" ha vendido 1 piscina en ").append(getNombre());

               venta = venderPiscina(edificiosCreados);
               break;

           case "pista":
               if (!existePistaDeporte()) {
                   sb.append(getNombre()).append(" no tiene ninguna pista de deporte construida");
                   break;
               }
               if (cantidad > 1)
                   sb.append("Solamente se puede vender 1 pita de deporte");
               else
                   sb.append(getDuenho().getNombre()).append(" ha vendido 1 pista de deporte en ").append(getNombre());

               venta = venderPista(edificiosCreados);
               break;

           case "default": break;
        }
        if (venta == 0)
            throw  new ExcepcionReglas(sb + ". En la En la propiedad quedan " + imprimirEdificiosRestantes() + ".");

        consola.imprimir(sb + ", recibiendo " + venta + "$. En la propiedad quedan " + imprimirEdificiosRestantes() + ".");
    }

    //Método para vender casas
    private float venderCasas(int cantidad, ArrayList<Edificio> edificiosCreados) {
        int numCasasInicial = contarCasas();
        float venta = cantidad * valorCasa;
        getDuenho().sumarFortuna(venta);
        eliminarCasas(edificiosCreados);
        getDuenho().sumarPatrimonio(-(cantidad * valorCasa));
        for (int i = 0; i < (numCasasInicial - cantidad); i++)
            edificios.add(new Casa(getDuenho(),this, edificiosCreados));

        return venta;
    }

    //Método para vender hoteles
    private float venderHotel(ArrayList<Edificio> edificiosCreados) {
        float venta = valorHotel;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorHotel);
        edificios.removeIf(edificio -> edificio.getTipo().equals("hotel"));
        edificiosCreados.removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("hotel"));
        getDuenho().getEdificios().removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("hotel"));
        edificios.add(new Casa(getDuenho(), this, edificiosCreados)); //Volvemos a edificar las 4 casas
        edificios.add(new Casa(getDuenho(), this, edificiosCreados));
        edificios.add(new Casa(getDuenho(), this, edificiosCreados));
        edificios.add(new Casa(getDuenho(), this, edificiosCreados));
        return venta;
    }

    //Método para vender piscinas
    private float venderPiscina(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPiscina;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorPiscina);
        edificios.removeIf(edificio -> edificio.getTipo().equals("piscina"));
        edificiosCreados.removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("piscina"));
        getDuenho().getEdificios().removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("piscina"));
        return venta;
    }

    //Método para vender pistas
    private float venderPista(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPistaDeporte;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorPistaDeporte);
        edificios.removeIf(edificio -> edificio.getTipo().equals("pista"));
        edificiosCreados.removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("pista"));
        getDuenho().getEdificios().removeIf(edificio -> edificio.getSolar().equals(this) && edificio.getTipo().equals("pista"));
        return venta;
    }

    //Método para imprimir edificios restantes después de una venta
    private String imprimirEdificiosRestantes() {
        StringBuilder sb = new StringBuilder();
        if (contarCasas() > -1) sb.append(contarCasas()).append(" casas");
        if (existeHotel()) sb.append(", 1 hotel");
        if (existePiscina()) sb.append(", 1 piscina");
        if (existePistaDeporte()) sb.append(", 1 pista de deporte");
        return sb.toString();
    }

    //Método que devuelve el nombre del color de un grupo pasado por argumento
    public String color() {
        return switch (getGrupo().getColorGrupo()) {
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

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Jugador duenho = getDuenho();

        sumarFrecuenciaVisita();
        if (!perteneceAJugador(banca) && !perteneceAJugador(actual) && !hipotecado) {
            float alquiler = alquiler();

            if (!actual.enBancarrota(alquiler, duenho) && actual.getFortuna() >= alquiler) {
                actual.sumarGastos(alquiler);
                actual.sumarFortuna(-alquiler);
                actual.sumarPagoAlquileres(alquiler);
                sumarAlquilerAcumulado(alquiler);
                duenho.sumarFortuna(alquiler);
                duenho.sumarCobroAlquileres(alquiler);
                consola.imprimir("Se han pagado " + alquiler + "€ de alquiler a " + duenho.getNombre() + ".");
                return true;
            } else if (!actual.getEnBancarrota() && actual.getFortuna() < alquiler)
                actual.setDeudaAPagar(alquiler);
        }
        return false;
    }

    @Override
    public String infoCasilla() {
        return "{" +
                "\n\ttipo: " + getTipo() +
                ",\n\tgrupo: " + color() +
                ",\n\tpropietario: " + getDuenho().getNombre() +
                ",\n\tvalor: " + getValor() +
                ",\n\talquiler: " + getImpuesto() +
                ",\n\tvalor hotel: " + valorHotel +
                ",\n\tvalor casa: " + valorCasa +
                ",\n\tvalor piscina: " + valorPiscina +
                ",\n\tvalor pista de deporte: " + valorPistaDeporte +
                ",\n\talquiler casa: " + alquilerCasa +
                ",\n\talquiler hotel: " + alquilerHotel +
                ",\n\talquiler piscina: " + alquilerPiscina +
                ",\n\talquiler pista de deporte: " + alquilerPistaDeporte +
                "\n}";
    }

    //nueva funcion para hipotecar
    public void deshipotecar(Jugador actual) throws ExcepcionReglas {
        if (!hipotecado) //Comprobamos que no esté hipotecada
            throw new ExcepcionReglas(actual.getNombre() + " no puede deshipotecar " + getNombre() + ". No está hipotecada.");

        hipotecado = false; //Indicamos que la propiedad no está hipotecada
        actual.sumarGastos(hipoteca);
        actual.sumarFortuna(-hipoteca); //Restamos la hipoteca
        actual.getHipotecas().remove(this); //Eliminamos de las propiedades hipotecadas del jugador
    }

    public void hipotecar(Jugador actual) throws ExcepcionReglas {
        if (hipotecado) //Comprobamos que no esté hipotecada
            throw new ExcepcionReglas(getNombre() + " no puede hipotecar " + getNombre() + ". Ya está hipotecada.");

        if (!(edificios.isEmpty())) //Comprobamos que no tenga edificios la casilla a hipotecar
            throw new ExcepcionReglas(getNombre() + " no puede hipotecar " + getNombre() + ". Debe vender todos los edificios del solar.");

        hipotecado = true; //Indicamos que la propiedad no está hipotecada
        actual.sumarGastos(-hipoteca);
        actual.sumarFortuna(hipoteca); //Restamos la hipoteca
        actual.getHipotecas().add(this); //Eliminamos de las propiedades hipotecadas del jugador
    }

    public boolean estaHipotecada() {
        return hipotecado;
    }

    @Override
    public String casEnVenta(){
        return "{" +
                "\n\tnombre: " + getNombre() +
                ",\n\ttipo: " + getTipo() +
                ",\n\tgrupo: " + color() +
                ",\n\tvalor: " + getValor() +
                "\n}";
    }

    @Override
    public float alquiler(){
        float alquiler = getImpuesto();
        int numCasas = contarCasas();

        if (getGrupo().esDuenhoGrupo(getDuenho()))
            alquiler *= 2;
        else if (numCasas > 0)
            alquiler = numCasas * alquilerCasa;
        else if (existeHotel())
            alquiler = alquilerHotel;
        else if (existePiscina())
            alquiler = alquilerHotel + alquilerPiscina;
        else if (existePistaDeporte())
            alquiler = alquilerHotel + alquilerPiscina + alquilerPistaDeporte;

        return alquiler;
    }

    //Método para edificar un tipo de edfiicio en un solar
    public void edificar(Jugador solicitante, String tipo, ArrayList<Edificio> edCreados) throws ExcepcionReglas {
        Jugador duenho = getDuenho();

        for (Solar solar : getGrupo().getMiembros()) //Comprobamos que ningún miembro del grupo esté hipotecado
            if (solar.estaHipotecada())
                throw new ExcepcionReglas("No se puede edificar en " + getNombre() + ". " + solar.getNombre() + " está hipotecado.");

        if (!perteneceAJugador(solicitante))
            throw new ExcepcionReglas("Esta casilla pertenece a " + getDuenho().getNombre() + ".");

        if (!getGrupo().esDuenhoGrupo(solicitante))
            throw new ExcepcionReglas(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color() + ".");

        switch (tipo) {
            case "casa" -> edificarCasa(edCreados);
            case "hotel" -> edificarHotel(edCreados);
            case "piscina" -> edificarPiscina(edCreados);
            case "pista" -> edificarPista(edCreados);
        }
    }

    //Método para edificar una casa
    private void edificarCasa(ArrayList<Edificio> edCreados) throws ExcepcionReglas {
        if (contarCasas() == 4)//Si hay 4 casas ya construídas no puede construir más
            throw new ExcepcionReglas("No se puede edificar ningún edificio más en esta casilla.");

        if (existeHotel()) //Si hay un hotel no puede construir casas
            throw new ExcepcionReglas("No se puede edificar ninguna casa, ya que ya se dispone de un hotel.");

        Jugador duenho = getDuenho();
        Casa casa = new Casa(getDuenho(), this, edCreados);
        edificios.add(casa); //Creamos una casa
        duenho.getEdificios().add(casa);
        duenho.sumarGastos(valorCasa); //Sumamos gastos y restamos fortuna
        duenho.sumarFortuna(-valorCasa);
        duenho.sumarPatrimonio(valorCasa);
    }

    //Método para edificar un hotel
    public void edificarHotel(ArrayList<Edificio> edCreados) throws ExcepcionReglas {
        int numCasas = contarCasas();

        if (numCasas < 4) //Si hay 4 casas ya construídas no puede construir más
            throw new ExcepcionReglas("No se puede edificar un hotel en esta casilla. Número de casas: " + numCasas);

        if (existeHotel()) //Si hay un hotel no puede construir casas
            throw new ExcepcionReglas("No se puede edificar un hotel, ya que ya se dispone de uno.");

        Jugador duenho = getDuenho();
        Hotel hotel = new Hotel(getDuenho(), this, edCreados);
        edificios.add(hotel); //Creamos hotel
        duenho.getEdificios().add(hotel);
        duenho.sumarGastos(valorHotel); //Sumamos gastos y restamos fortuna
        duenho.sumarFortuna(-valorHotel);
        duenho.sumarPatrimonio(valorHotel);
        eliminarCasas(edCreados); //Eliminamos las casas
    }

    //Método para edificar una piscina
    public void edificarPiscina(ArrayList<Edificio> edCreados) throws ExcepcionReglas {
        if (!existeHotel()) //Si hay un hotel no puede construir casas
            throw new ExcepcionReglas("No se puede edificar una piscina, ya que no se dispone de un hotel.");

        if (existePiscina())
            throw new ExcepcionReglas("No se puede edificar una piscina, ya que ya se dispone de una.");

        Jugador duenho = getDuenho();
        Piscina piscina = new Piscina(duenho, this, edCreados);
        edificios.add(piscina); //Creamos una piscina
        duenho.getEdificios().add(piscina);
        duenho.sumarGastos(valorPiscina); //Sumamos gastos y restamos fortuna
        duenho.sumarFortuna(-valorPiscina);
        duenho.sumarPatrimonio(valorPiscina);
    }

    //Método para edificar una pista de deporte
    public void edificarPista(ArrayList<Edificio> edCreados) throws ExcepcionReglas {
        if (!existePiscina()) //Si no hay una piscina no puede construir una pista
            throw new ExcepcionReglas("No se puede edificar una pista de deporte, ya que no se dispone de una piscina.");

        if (existePistaDeporte())
            throw new ExcepcionReglas("No se puede edificar una pista de deporte, ya que ya se dispone de una.");

        Jugador duenho = getDuenho();
        PistaDeporte pistaDeporte = new PistaDeporte(duenho, this, edCreados);
        edificios.add(pistaDeporte); //Creamos una pista de deporte
        duenho.getEdificios().add(pistaDeporte);
        duenho.sumarGastos(valorPistaDeporte); //Sumamos gastos y restamos fortuna
        duenho.sumarFortuna(-valorPistaDeporte);
        duenho.sumarPatrimonio(valorPistaDeporte);
    }

    //Método para contar las casas de una casilla
    public int contarCasas() {
        int count = 0;

        for (Edificio ed : edificios)
            if (ed.getTipo().equalsIgnoreCase("casa"))
                count++;

        return count;
    }

    //Método para saber si hay un hotel en la casilla
    public boolean existeHotel() {
        for (Edificio ed : edificios)
            if (ed.getTipo().equalsIgnoreCase("hotel"))
                return true;

        return false;
    }

    //Método para saber si hay una piscina en la casilla
    public boolean existePiscina() {
        for (Edificio ed : edificios)
            if (ed.getTipo().equalsIgnoreCase("piscina"))
                return true;

        return false;
    }

    //Método para saber si hay una pista de deporte en la casilla
    public boolean existePistaDeporte() {
        for (Edificio ed : edificios)
            if (ed.getTipo().equalsIgnoreCase("pista"))
                return true;

        return false;
    }
}