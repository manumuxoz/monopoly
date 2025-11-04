package monopoly;

import partida.*;
import java.util.ArrayList;

class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //nuevos atributos
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPistaDeporte;

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        miembros = new ArrayList<>();
        cas1.setGrupo(this);
        anhadirCasilla(cas1);
        cas2.setGrupo(this);
        anhadirCasilla(cas2);
        this.colorGrupo = colorGrupo;
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
            miembros = new ArrayList<>();
            cas1.setGrupo(this);
            anhadirCasilla(cas1);
            cas2.setGrupo(this);
            anhadirCasilla(cas2);
            cas3.setGrupo(this);
            anhadirCasilla(cas3);
            this.colorGrupo = colorGrupo;
    }

    //Getters:
    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }
    public String getColorGrupo() {
        return colorGrupo;
    }
    public int getNumCasillas() {
        return numCasillas;
    }
    public float getPrecioCasa() {
        return precioCasa;
    }
    public float getPrecioHotel() {
        return precioHotel;
    }
    public float getPrecioPiscina() {
        return precioPiscina;
    }
    public float getPrecioPistaDeporte() {
        return precioPistaDeporte;
    }


    //Setters:
    public void setMiembros(ArrayList<Casilla> miembros) {
        this.miembros = miembros;
    }
    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }
    public void setNumCasillas(int numCasillas) {
        this.numCasillas = numCasillas;
    }
    public void setPrecioCasa(float precioCasa) {
        this.precioCasa = precioCasa;
    }
    public void setPrecioHotel(float precioHotel) {
        this.precioHotel = precioHotel;
    }
    public void setPrecioPiscina(float precioPiscina) {
        this.precioPiscina = precioPiscina;
    }
    public void setPrecioPistaDeporte(float precioPistaDeporte) {
        this.precioPistaDeporte = precioPistaDeporte;
    }


    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        if(!miembros.contains(miembro)) {
            miembros.add(miembro);
            numCasillas += 1;
        }
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        if (jugador.getPropiedades().containsAll(getMiembros())) return true;
        else return false;
    }
}
