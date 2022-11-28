package problema2;

import java.util.Random;

public class Problema2 {

    public static void main(String[] args) {
        Cubetas c = new Cubetas();
        Thread[] t = new Thread[5];
        for(int i = 0; i < t.length; i++){
            t[i] = new Thread(new Persona(c,(i%2)+1,i+1,10));
        }
        for (Thread thread : t) {
            thread.start();
        }
        for(int i = 0; i < t.length; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar a terminar los hilos");
                return;
            }
        }

        System.out.printf("La cubeta 1 tiene %d litros y la cubeta 2 tiene %d litros",c.getLitrosCubeta1(),c.getLitrosCubeta2());
    }

}

class Persona implements Runnable {

    private final Cubetas c;
    private final int cubetaALlenar;
    private final int id;
    private final int numeroDeLitros;

    public Persona(Cubetas c, int cubetaALlenar, int id, int numeroDeLitros) {
        this.c = c;
        this.cubetaALlenar = cubetaALlenar;
        this.id = id;
        this.numeroDeLitros = numeroDeLitros;
    }

    @Override
    public void run() {
        //La cantidad de veces que cada hilo ha de insertar litros
        for (int i = 0; i < numeroDeLitros; i++) {

            System.out.printf("Soy la persona %d y voy a llenar la cubeta %d%n", id, cubetaALlenar);
            switch (cubetaALlenar) {
                case 1 -> c.incrementarCubeta1();
                case 2 -> c.incrementarCubeta2();
            }
            System.out.printf("Soy la persona %d y he llenado la cubeta %d%n", id, cubetaALlenar);
        }
    }
}

class Cubetas{
    //Los contadores y sus locks
    private int litrosCubeta1 = 0, litrosCubeta2 = 0;
    private final Object lock1 = new Object(), lock2 = new Object();

    public Cubetas() {
    }

    public void incrementarCubeta1(){
        synchronized (lock1){
            litrosCubeta1+=10;
            //Le añado un tiempo de espera antes de intentarlo, se puede quitar
            try {
                Thread.sleep(new Random().nextInt(30, 100));//Espera de 0,03 a 0,1 ms
            } catch (InterruptedException e) {
                System.out.println("Hubo un problema al esperar");
                return;
            }
        }
    }

    public void incrementarCubeta2(){
        synchronized (lock2){
            litrosCubeta2+=10;
            //Le añado un tiempo de espera antes de intentarlo, se puede quitar
            try {
                Thread.sleep(new Random().nextInt(30, 100));//Espera de 0,03 a 0,1 ms
            } catch (InterruptedException e) {
                System.out.println("Hubo un problema al esperar");
                return;
            }
        }
    }

    public int getLitrosCubeta1() {
        synchronized (lock1){
            return litrosCubeta1;
        }
    }

    public int getLitrosCubeta2() {
        synchronized (lock2){
            return litrosCubeta2;
        }
    }
}
