package juego;

import java.awt.Color;
import java.awt.Image;


import entorno.Entorno;
import entorno.Herramientas;

public class BolaDeFuego {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private double velocidad;
	private Image imgBolaDeFuego;
	

	public BolaDeFuego(double e, double f, int ancho, int alto, double d) {
		this.x = e;
		this.y = f;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = d;
		this.imgBolaDeFuego = Herramientas.cargarImagen("imagenes/proyectil.png");
		
	}
	
	public BolaDeFuego(double x2, int y2, int ancho2, int alto2, int alto3, Color red) {
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
	public double getVelocidad() {
		return velocidad;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imgBolaDeFuego, x, y, 0, 0.6);
		
	}
	public void mover() {
		this.x += this.velocidad;
	}
}
