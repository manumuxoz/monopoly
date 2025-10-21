package partida;


public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        int min= 1;
        int max =6;
        int numeroDados1 = (int) (Math.random()*(max-min+1)+min);
        return  numeroDados1;
    }
}
