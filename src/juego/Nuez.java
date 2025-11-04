package juego;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Nuez {
	private double x;
	private int y;
	private int ancho;
	private int alto;
	private int vida;
	private boolean seleccionada;
	private boolean disponible;
	private Image imgNuez;
	private int salud = 50;

	public Nuez(int x, int y, int alto, int ancho, int vida, boolean disponible, boolean seleccionada) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imgNuez = Herramientas.cargarImagen("imagenes/nuez.gif");
	}
	
	public void recibirDanio(int d) { /////////////AGREGADO
	    salud -= d;
	}
	public boolean estaDestruida() { /////////////////AGREGADO
	    return salud <= 0;
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
	public int getVida() {
		return vida;
	}
	
	public boolean getSeleccionada() {
		return seleccionada;
	}
	
	public void setSeleccionada() {
		seleccionada = true;
	}
	
	public void setDeseleccionada() {
		seleccionada = false;
	}
	
	public void disponible() {
		disponible = true;
	}
	
	public void situarse(int x, int y) {
		
		if(180<=y && y<=269) {
			this.y = 220;
		}
		if(270<=y && y<=359) {
			this.y = 270;
		}
		if(360<=y && y<=449) {
			this.y = 340;
		}
		if(450<=y && y<=539) {
			this.y = 400;
		}
		if(540<=y && y<=600) {
			this.y = 460;
		}
		
		if (180 <= x && x <= 269) {
	        this.x = 280;
	    } else if (270 <= x && x <= 359) {
	        this.x = 345;
	    } else if (360 <= x && x <= 449) {
	        this.x = 405;
	    } else if (450 <= x && x <= 540) {
	        this.x = 465;
	    }  else if (450 <= x && x <= 540) {
	        this.x = 505;
	}
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imgNuez, x, y, 0, 0.6);
	}

}
