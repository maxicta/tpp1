package juego;

import java.awt.Color;

import entorno.Entorno;

public class Restantes {
	private int contador;

	public Restantes() {
		this.contador = 0;
	}

	public void sumarZombie() {
		contador++;
	}

	public void restarZombie() {
		if (contador > 0) {
			contador--;
		}
	}

	public void setContador(int cantidad) {
		this.contador = cantidad;
	}

	public int getContador() {
		return contador;
	}

	public void dibujar(Entorno entorno) {
		entorno.cambiarFont("arial", 24, Color.BLACK);
		entorno.escribirTexto("" + contador, 430, 75);
	}

}
