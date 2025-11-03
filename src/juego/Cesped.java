package juego;


import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Cesped {
	
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private Image imgCesped;
	
	Cesped(int x, int y, int alto, int ancho){
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.imgCesped = Herramientas.cargarImagen("imagenes/cesped.png");
	}
	
	public Cesped(Entorno entorno) {
		// TODO Auto-generated constructor stub
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getAncho() {
		return ancho;
	}
	public double getAlto() {
		return alto;
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imgCesped, x, y, 0, 1);

	}
}