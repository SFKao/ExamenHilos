package problema1.arreglo;

import java.util.Random;

public class Problema1 {

    public static void main(String[] args) {
        System.out.println("Sin interbloqueo:\n");
        Herramienta h1, h2;//Creo las herramientas
        h1 = new Herramienta("Carraca");
        h2 = new Herramienta("Llave");
        Thread t1, t2;//Creo los hilos
        t1 = new Mecanico(h1,h2,1);//Les mando las herramientas en orden inverso
        t2 = new Mecanico(h2,h1,2);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Error al unir los hilos");
            return;
        }
        //Si llego aqui, no se habra producido un interbloqueo
        System.out.println("Si no se ha producido un interbloqueo, aparecere");


    }

}


class Mecanico extends Thread{

    Herramienta herramienta1, herramienta2;
    int id;

    public Mecanico(Herramienta herramienta1, Herramienta herramienta2, int id) {
        this.herramienta1 = herramienta1;
        this.herramienta2 = herramienta2;
        this.id = id;
    }

    @Override
    public void run() {

        //La manera de arreglar un interbloqueo es que las herramientas se recojan siempre en un orden establecido.
        //En este caso usaremos su orden en un diccionario
        Herramienta primera, segunda;
        if(herramienta1.nombre.compareTo(herramienta2.nombre) > 0){
            primera = herramienta1;
            segunda = herramienta2;
        }else{
            primera = herramienta2;
            segunda = herramienta1;
        }

        //Recojo la primera herramienta
        synchronized (primera){
            System.out.printf("Soy el mecanico %d y he cogido el/la %s%n",id,primera.getNombre());
            try {
                Thread.sleep(new Random().nextInt(100,300));//Espera de 0,1 0,3 ms
            } catch (InterruptedException e) {
                System.out.println("Hubo un problema al esperar");
                return;
            }
            //Intento coger la segunda herramienta
            System.out.printf("Soy el mecanico %d y voy a coger el/la %s%n",id,segunda.getNombre());
            synchronized (segunda){
                try {
                    Thread.sleep(new Random().nextInt(100,300));//Espera de 0,1 0,3 ms
                } catch (InterruptedException e) {
                    System.out.println("Hubo un problema al esperar");
                    return;
                }
                System.out.printf("Soy el mecanico %d y voy a soltar mis herramientas%n",id);
            }
        }

    }
}

class Herramienta{

    String nombre;

    public Herramienta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}