package monopoly;

import partida.*;
import java.util.ArrayList;

class Grupo extends Casilla {

    //Atributos
    private ArrayList<Solar> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Solar cas1, Solar cas2, String colorGrupo) {
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
    public Grupo(Solar cas1, Solar cas2, Solar cas3, String colorGrupo) {
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
    public ArrayList<Solar> getMiembros() {
        return miembros;
    }
    public String getColorGrupo() {
        return colorGrupo;
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Solar miembro) {
        if(!miembros.contains(miembro) && numCasillas < 3) {
            miembro.setGrupo(this);
            miembros.add(miembro);
            numCasillas += 1;
        }
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        return jugador.getPropiedades().containsAll(miembros);
    }
}
