package monopoly;

import partida.*;
import java.util.ArrayList;


public class Edificio { //Nueva clase edificio
    private Jugador duenho; //Jugador al que pertenece
    private Casilla casilla; //Casilla en la que está el edificio
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

    //Setters:
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
