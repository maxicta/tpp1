package juego;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Image;


import entorno.Entorno;
import entorno.Herramientas;


public class Zombie {
	private double x;
	private double y;
	private int ancho;
	private double alto;
	private int salud;
	private double velocidad;
	private double velocidadOriginal;
	private Image imgZombie;
	private boolean comiendo = false;
	private int contadorComiendo = 0;
	private int duracionComida = 120;


	public Zombie(double x2, double y2, int ancho, double alto, int salud, double d) {
		this.x = x2;
		this.y = y2;
		this.ancho = ancho;
		this.alto = alto;
		this.salud = salud;
		this.velocidad = d;
		this.velocidadOriginal = d;
		this.imgZombie = Herramientas.cargarImagen("imagenes/zombie.gif");
		
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
	public int getSalud() {
		return salud;
	}
	public void setSalud() {
		this.salud -= 1;
	}
	public double getVelocidad() {
		return velocidad;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imgZombie, x, y, 0, alto);
	}
	public void mover() {
		this.x -= this.velocidad;	
	}
	
	public void empezarAComer() {
	    this.comiendo = true;
	    this.contadorComiendo = 0;
	}
	
	public void actualizarComida() {
	    if (!comiendo) return;
	    contadorComiendo++;
	    if (contadorComiendo >= duracionComida) {
	        comiendo = false;
	        this.velocidad = this.velocidadOriginal;
	        
	    }
	    if (comiendo) {
	    	this.velocidad = 0;
	    }
	}

	public boolean estaComiendo() {
	    return comiendo;
	}

}
	