package juego;

import java.awt.Color;

import entorno.Entorno;

public class Eliminados {
	private int contador;

	public Eliminados() {
		this.contador = 0;
	}

	public void sumarZombie() {
		contador++;
	}

	public int getContador() {
		return contador;
	}

	public void dibujar(Entorno entorno) {
		entorno.cambiarFont("arial", 24, Color.BLACK);
		entorno.escribirTexto("" + contador, 430, 40);
	}

}


