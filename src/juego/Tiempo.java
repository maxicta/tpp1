package juego;

import java.awt.Color;

import entorno.Entorno;

public class Tiempo {
	private long inicio;
	private int minutos;
	private int segundos;

	public Tiempo() {
		this.inicio = System.currentTimeMillis();
	}

	public int segundosTranscurridos() {
		long ahora = System.currentTimeMillis();
		return (int) ((ahora - inicio) / 1000); // convierte milisegundos a segundos
	}

	public void dibujar(Entorno entorno) {
		int totalSegundos = segundosTranscurridos();
		minutos = totalSegundos / 60;
		segundos = totalSegundos % 60;

		entorno.cambiarFont("Arial", 20, Color.BLACK);
		entorno.escribirTexto("" + segundos + "s" + minutos + "m ", 400, 110);

	}

}
