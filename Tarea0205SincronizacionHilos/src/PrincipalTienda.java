/*
Ejercicio de Programación en Java: Sincronización de Hilos

El objetivo de este ejercicio es practicar la sincronización de hilos en Java utilizando la palabra clave synchronized.
Deberás crear un programa que simule una tienda con un stock limitado de productos y múltiples clientes que intentan comprar estos productos al mismo tiempo.

La clase Tienda tendrá un atributo stock que marcará la cantidad de productos disponibles para la venta.
Dispondrá también de un metodo comprarProducto(int cantidad). Si la cantidad es menor al stock se permitirá la compra y sino no.

La clase Cliente será la clase que implementará el metodo run.
Tendrá los atributos tienda (de la clase Tienda) y un entero cantidad, que marcará la cantidad de productos que el hilo querrá comprar.
En su metodo run se llamará al metodo comprarProducto.

En el metodo main de la clase PrincipalTienda creará los hilos.

Una salida para un valor de stock 10 podría ser la siguiente:
Cliente 1 compró 5 productos.
Stock disponible: 5
Cliente 3 compró 3 productos.
Stock disponible: 2
Cliente 2 intentó comprar 8 productos, pero no hay suficiente stock

 */

public class PrincipalTienda {
    public static void main(String[] args) {
        Tienda tienda = new Tienda(10); //Stock inicial = 10

        Thread cliente1 = new Thread(new Cliente(tienda, 5, "Cliente 1"));
        Thread cliente2 = new Thread(new Cliente(tienda, 8, "Cliente 2"));
        Thread cliente3 = new Thread(new Cliente(tienda, 3, "Cliente 3"));

        cliente1.start();
        cliente2.start();
        cliente3.start();

        try {
            cliente1.join();
            cliente2.join();
            cliente3.join();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        System.out.println("Todos los clientes han terminado.");
    }
}

class Tienda {
    private int stock; // cantidad de productos disponibles

    public Tienda(int stockInicial) {
        this.stock = stockInicial;
    }

    // Metodo sincronizado para evitar condiciones de carrera
    public synchronized boolean comprarProducto(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
            System.out.println("Compra exitosa: " + cantidad + " productos. Stock restante: " + stock);
            return true;
        } else {
            System.out.println("No hay suficiente stock para " + cantidad + " productos. Stock restante: " + stock);
            return false;
        }
    }
}

class Cliente implements Runnable {
    private Tienda tienda;
    private int cantidad;
    private String nombre;

    public Cliente(Tienda tienda, int cantidad, String nombre) {
        this.tienda = tienda;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    @Override
    public void run(){
        System.out.println(nombre + " intenta comprar " + cantidad + " productos.");
        tienda.comprarProducto(cantidad); // Llamada sincronizada
    }
}
