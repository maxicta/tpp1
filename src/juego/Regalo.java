package juego;


import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Image;

public class Regalo {
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private boolean sano;
	private Image imgRegalo;

	
	Regalo(int x, int y, int alto, int ancho, boolean sano){
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.sano = sano;
		this.imgRegalo = Herramientas.cargarImagen("imagenes/regalos.gif");
		
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public int getAlto() {
		return alto;
	}
	
	public boolean getSano() {
		return sano;
	}
	
	public void setMuerto() {
		this.sano = false;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imgRegalo, x, y, 0, 0.09);
	}

}