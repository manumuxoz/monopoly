package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Objects;


public class Edificio { //Nueva clase edificio
    private Jugador duenho; //Jugador al que pertenece
    private Casilla casilla; //Casilla en la que está el edificio
    private Grupo grupo; //Grupo en el que está el edificio
    private String ID; //ID que identifica de manera única al edificio
    private String tipo; //String para el tipo de edificio (casa, hotel, piscina o pista)

    //Constructor vacío
    public Edificio() {
    }

    //Constructor con argumentos (jugador al que pertenece, casilla en la que está, tipo de edificio y un arraylist
    //con los edificios ya creados
    public Edificio(Jugador duenho, Casilla casilla, String tipo, ArrayList<Edificio> edificiosCreados) {
        if (tipo.equals("casa") || tipo.equals("hotel") || tipo.equals("piscina") || tipo.equals("pista")) {
            this.duenho = duenho;
            this.casilla = casilla;
            this.tipo = tipo;
            grupo = casilla.getGrupo();
            generarID(tipo,  edificiosCreados); //Creamos ID
        }
    }

    //Método para generar un ID a cada nuevo edificio
    private void generarID(String tipo, ArrayList<Edificio> edificiosCreados) {
        int count = 1;
        for (Edificio edificio : edificiosCreados) {
            if (tipo.equals(edificio.tipo)) //Comparamos tipo para sumar al contador
                count++;
        }
        ID = String.format(tipo + "-" + count); //Creamos ID con el formato tipo-número

        edificiosCreados.add(this); //Añadimos al arraylist global
    }

    //Getters:
    public Jugador getDuenho() {
        return duenho;
    }
    public Casilla getCasilla() {
        return casilla;
    }
    public String getID() {
        return ID;
    }
    public String getTipo() {
        return tipo;
    }

    /*Método para mostrar información sobre un edificio.
     * Devuelve una cadena con información específica de cada tipo de edificio.*/
    public String infoEdificio() {
        return "{\n\tid: " + ID +
                ",\n\tpropietario: " + duenho.getNombre() +
                ",\n\tcasilla: " + casilla.getNombre() +
                ",\n\tgrupo: " + casilla.color(grupo.getColorGrupo()) +
                ",\n\tcoste: " + imprimirCoste(tipo) +
                "\n}";
    }

    //Método para imprimir el valor del alquiler de cada edificio dependiendo de su tipo
    private String imprimirCoste(String tipo) {
        return switch (tipo) {
            case "casa" -> String.valueOf(casilla.getAlquilerCasa());
            case "hotel" -> String.valueOf(casilla.getAlquilerHotel());
            case "piscina" -> String.valueOf(casilla.getAlquilerPiscina());
            case "pista" -> String.valueOf(casilla.getAlquilerPistaDeporte());
            default -> "";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Edificio edificio = (Edificio) o;
        return Objects.equals(ID, edificio.ID);
    }
}
