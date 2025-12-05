package consola;

import java.util.Scanner;

public class ConsolaNormal implements Consola {
    @Override
    public void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String leer(String descripcion) {
        System.out.print(descripcion);
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }
}