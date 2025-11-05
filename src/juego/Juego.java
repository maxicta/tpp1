package juego;

import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.Image;


public class Juego extends InterfaceJuego {

    private Entorno entorno;
    private Regalo[] regalos;
    private Zombie[] zombies;
    private Planta[] plantas;
    private BolaDeFuego[] bolasDeFuego;
    private Cesped cesped;
    private Nuez[] nuez;
    private int[] contadorDisparoPlantas;
    private int totalZombiesCreados = 0;
	private Eliminados eliminados;
	private Restantes restantes;
	private Tiempo tiempo;
	private Image imgGanaste; // IMAGEN GANASTE
	private boolean ganasteFin = false; // AGREGADO
	private boolean tiempoDetenido = false; // AGREGADO
	private Image imgPerdiste; // IMAGEN PERDISTE
	private boolean perdisteFin = false; // AGREGADO
	private Zombie z;

    Juego() {
    	
        this.entorno = new Entorno(this, "La Invasión de los Zombies", 800, 600);
        this.imgPerdiste = Herramientas.cargarImagen("imagenes/perdiste.png"); // IMAGEN PERDISTE
		this.imgGanaste = Herramientas.cargarImagen("imagenes/ganaste.png");
        this.zombies = new Zombie[15];
        this.cesped = new Cesped(400, 300, 0, 0);
        this.regalos = new Regalo[5];
        this.plantas = new Planta[25];
        this.bolasDeFuego = new BolaDeFuego[100];
        this.contadorDisparoPlantas = new int[plantas.length];
        this.nuez = new Nuez[5];
		this.setEliminados(new Eliminados());
		this.restantes = new Restantes( this.zombies.length );
		this.setTiempo(new Tiempo());

        for (int i = 0; i < this.plantas.length; i++) {
            this.plantas[i] = new Planta(70, 80, 40, 40, 0, false, false);
        }

 
        int[] filasY = {220, 270, 340, 400, 460};
        for (int i = 0; i < this.regalos.length; i++) {
            this.regalos[i] = new Regalo(230, filasY[i], 80, 80, true);
        }


        for (int i = 0; i < this.nuez.length; i++) {
            this.nuez[i] = new Nuez(160, 80, 80, 80, 0, false, false);
        }

        this.entorno.iniciar();
    }


	public void tick() {
        this.cesped.dibujar(this.entorno);
		this.eliminados.dibujar(entorno);
		this.restantes.dibujar(entorno);
		tiempo.dibujar(entorno);
		
		if (!tiempoDetenido) { // tiempo del juego
			tiempo.dibujar(entorno);
		}

		if (eliminados.getContador() >= this.zombies.length && !ganasteFin) {
		    tiempoDetenido = true;
		    tiempo.detener();
		    ganasteFin = true; // activa el modo victoria
		}
		

		if (ganasteFin) {
			entorno.dibujarImagen(imgGanaste, 400, 300, 0, 1.0);// imagen GANASTE
			return;
		}
		if (perdisteFin) {
			entorno.dibujarImagen(imgPerdiste, 400, 300, 0, 1.0);// imagen GANASTE
			return;
		}
		
		
		

        // Disponibilidad aleatoria de las plantas
        if (Math.random() < 1) {
            for (Planta p : this.plantas) {
                if (p != null) p.disponible();
            }
        }

        // Aparición aleatoria de  los zombies
        if (Math.random() < 0.003) {
            agregarZombie();
        }

        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null) {
                zombies[i].mover();
                zombies[i].dibujar(entorno);
                

                if (zombies[i].getX() < -50) {
                    zombies[i] = null;
                }
            }
        }

        // Plantas
        for (int i = 0; i < plantas.length; i++) {
            if (plantas[i] != null) {
                Planta p = plantas[i];
                p.dibujar(entorno);

                if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
                        && this.cursorDentro(p, entorno.mouseX(), entorno.mouseY(), 20)) {
                    p.setSeleccionada();
                    break;
                }

                if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
                        && !this.cursorDentro(p, entorno.mouseX(), entorno.mouseY(), 20)) {
                    p.setDeseleccionada();
                }

           
                if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && p.getSeleccionada()) {
                    p.situarse(entorno.mouseX(), entorno.mouseY());
                }

    
                boolean zombieEnLinea = false;
                for (Zombie z : zombies) {
                    if (z != null && Math.abs(p.getY() - z.getY()) < 40) {
                        zombieEnLinea = true;
                        break;
                    }
                }

    
                contadorDisparoPlantas[i]++;
                if (zombieEnLinea && contadorDisparoPlantas[i] >= 80) {
                    contadorDisparoPlantas[i] = 0;
                    for (int b = 0; b < bolasDeFuego.length; b++) {
                        if (bolasDeFuego[b] == null) {
                            bolasDeFuego[b] = new BolaDeFuego(p.getX() + 30, p.getY(), 10, 8, 3);
                            break;
                        }
                    }
                }
            }
        }

   
        for (int i = 0; i < nuez.length; i++) {
            Nuez n = nuez[i];
            if (n != null) {
            n.dibujar(entorno);

            if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
                    && this.cursorDentro(n, entorno.mouseX(), entorno.mouseY(), 20)) {
                n.setSeleccionada();
                break;
            }

            if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
                    && !this.cursorDentro(n, entorno.mouseX(), entorno.mouseY(), 20)) {
                n.setDeseleccionada();
            }

            if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && n.getSeleccionada()) {
                n.situarse(entorno.mouseX(), entorno.mouseY());
            }
            }
        }

        
        for (int i = 0; i < bolasDeFuego.length; i++) {
            if (bolasDeFuego[i] != null) {
                bolasDeFuego[i].dibujar(entorno);
                bolasDeFuego[i].mover();

                if (bolasDeFuego[i].getX() > 820) {
                    bolasDeFuego[i] = null;
                }
            }
        }

 
        for (int i = 0; i < regalos.length; i++) {
            if (regalos[i] != null) {
                regalos[i].dibujar(entorno);

                for (Zombie z : zombies) {
                    if (z != null) {
                        double dx = regalos[i].getx() - z.getX();
                        double dy = regalos[i].gety() - z.getY();
                        double distancia = Math.sqrt(dx * dx + dy * dy);

                        if (distancia < 10) {
                            regalos[i] = null;
                            this.perdisteFin = true;
                            break;
                        }
                    }
                }
            }
        }

        // Colisiones bolas de fuego - zombies
        for (int i = 0; i < bolasDeFuego.length; i++) {
            if (bolasDeFuego[i] != null) {
                for (int j = 0; j < zombies.length; j++) {
                    if (zombies[j] != null) {
                        double dx = bolasDeFuego[i].getX() - zombies[j].getX();
                        double dy = bolasDeFuego[i].getY() - zombies[j].getY();
                        double distancia = Math.sqrt(dx * dx + dy * dy);

                        if (distancia < 60) {
                            zombies[j].setSalud();
                            bolasDeFuego[i] = null;

                            if (zombies[j].getSalud() <= 0) {
                                zombies[j] = null;
								eliminados.sumarZombie(); // agregue contador de zombies eliminados
                            }
                            break;
                        }
                    }
                }
            }
        }

        // Colisiones zombies - plantas
        for (int j = 0; j < zombies.length; j++) {
            if (zombies[j] != null) {
                if (zombies[j].estaComiendo()) {
                    zombies[j].actualizarComida();
                    zombies[j].dibujar(entorno);
                } else {
                    zombies[j].mover();
                    zombies[j].dibujar(entorno);

                    for (int i = 0; i < plantas.length; i++) {
                        if (plantas[i] != null) {
                            double dx = plantas[i].getX() - zombies[j].getX();
                            double dy = plantas[i].getY() - zombies[j].getY();
                            double distancia = Math.sqrt(dx * dx + dy * dy);

                            if (distancia < 20) {
                                zombies[j].empezarAComer();
                                plantas[i] = null;
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        for (int j = 0; j < zombies.length; j++) {
			if (zombies[j] != null) {
				if (zombies[j].estaComiendo()) {
					zombies[j].actualizarComida();
					zombies[j].dibujar(entorno);
				} else {
					zombies[j].mover();
					zombies[j].dibujar(entorno);

					for (int i = 0; i < nuez.length; i++) {
						if (nuez[i] != null) {
							double dx = nuez[i].getX() - zombies[j].getX();
							double dy = nuez[i].getY() - zombies[j].getY();
							double distancia = Math.sqrt(dx * dx + dy * dy);

							if (distancia < 20 && !zombies[j].estaComiendo()) {
							    zombies[j].empezarAComer();
							    nuez[i] = null;
							    break;
	                        }
	                    }
	                }
	            }
	        }
	    }
    }

   
    public boolean cursorDentro(Planta a, int mx, int my, int d) {
        return ((a.getX() - mx) * (a.getX() - mx) + (a.getY() - my) * (a.getY() - my)) < d * d;
    }

    public boolean cursorDentro(Nuez a, int mx, int my, int d) {
        return ((a.getX() - mx) * (a.getX() - mx) + (a.getY() - my) * (a.getY() - my)) < d * d;
    }


    private void agregarZombie() {
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] == null) {
            	this.restantes.restarZombie();

                double x = 800;
				int[] filasY = {220, 270, 340, 400, 460};
				double y = filasY[(int) (Math.random() * filasY.length)]; // 220, 270, 340, 400, 460

                Zombie nuevoZombie;
                if (totalZombiesCreados == 4 || totalZombiesCreados == 44
                        || totalZombiesCreados == 59 || totalZombiesCreados == 74) {
                    nuevoZombie = new Zombie(x, y, 100, 0.7, 10, 0.5);
                } else {
                    nuevoZombie = new Zombie(x, y, 80, 0.6, 3, 1.0);
                }

                zombies[i] = nuevoZombie;
                totalZombiesCreados++;
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
    public Cesped getCesped() {
        return cesped;
    }

    public void setCesped(Cesped cesped) {
        this.cesped = cesped;
    }

    public Eliminados getEliminados() {
        return eliminados;
    }

    public void setEliminados(Eliminados eliminados) {
        this.eliminados = eliminados;
    }

    public Restantes getRestantes() {
        return restantes;
    }

    public void setRestantes(Restantes restantes) {
        this.restantes = restantes;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }
}







