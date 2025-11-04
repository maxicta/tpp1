package juego;

import java.awt.Color;

import entorno.Entorno;

public class Tiempo {
    private long inicio = 0;
    private int minutos;
    private int segundos;
    private boolean detenido = false; // Bandera para saber si el tiempo está detenido
    private int segundosEnPausa = 0;

    public Tiempo() {
        this.segundos = 0;
        this.minutos = 0;
    }

    public void iniciar() {
        if (inicio == 0) { // Solo se inicializa una vez, en el primer tick
            inicio = System.currentTimeMillis(); // Marca el momento real de inicio del juego
        }
    }

    public void detener() {
        detenido = true; // Activa la bandera de tiempo detenido
        segundosEnPausa = segundosTranscurridos(); // Guarda el tiempo actual para congelarlo
    }


		

    public int segundosTranscurridos() {
        if (detenido) {
            return segundosEnPausa; // Si está detenido, devuelve el tiempo congelado
        }
        long ahora = System.currentTimeMillis(); // Tiempo actual
        return (int) ((ahora - inicio) / 1000); // Diferencia en segundos desde el inicio
    }


    public void dibujar(Entorno entorno) {
        iniciar(); // Asegura que el tiempo esté inicializado

        int totalSegundos = segundosTranscurridos();
        minutos = totalSegundos / 60;
        segundos = totalSegundos % 60;

        entorno.cambiarFont("Arial", 20, Color.BLACK);
        entorno.escribirTexto(String.format(" %02ds%2dm", segundos, minutos), 390, 110); // muestra segundos y minutos
    }

    public void reiniciar() {
        inicio = System.currentTimeMillis(); // Reinicia el tiempo desde cero
        detenido = false;
        segundosEnPausa = 0;
    }

    public boolean isDetenido() {
        return detenido;
    }

    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }
}
