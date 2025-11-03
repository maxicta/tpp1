package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Planta {
	
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private int vida;
	private boolean disponible;
	private boolean seleccionada;
	private Image imgPlanta;
	
	Planta(int x, int y, int alto, int ancho, int vida, boolean disponible, boolean seleccionada){
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.vida = vida;
		this.disponible = disponible;
		this.seleccionada = seleccionada;
		this.imgPlanta = Herramientas.cargarImagen("imagenes/planta.gif");
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
		entorno.dibujarImagen(imgPlanta, x, y, 0, 0.6);
	}

}