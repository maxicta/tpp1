package juego;

import java.awt.Image;
import javax.sound.midi.MidiSystem; //AGREGADO
import javax.sound.midi.Sequence; //AGREGADO
import javax.sound.midi.Sequencer; //AGREGADO
import entorno.Entorno;
import entorno.Herramientas; //AGREGADO
import entorno.InterfaceJuego;

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
	private Sequence secuencia;
	private Sequencer secuenciador;
	private Image imgGanaste; // IMAGEN GANASTE
	private boolean ganasteFin = false; // AGREGADO
	private boolean tiempoDetenido = false; // AGREGADO
	private Image imgPerdiste; // IMAGEN PERDISTE
	private boolean perdisteFin = false; // AGREGADO


    Juego() {
    	
        this.entorno = new Entorno(this, "La Invasión de los Zombies", 800, 600);
        try {
            secuencia = MidiSystem.getSequence(ClassLoader.getSystemResource("sonidos/Super_Mario_World_SNES.mid"));
            secuenciador = MidiSystem.getSequencer();
            secuenciador.open();
            secuenciador.setSequence(secuencia);
            secuenciador.setTempoFactor(1.0f); // velocidad normal
            secuenciador.setLoopCount(Sequencer.LOOP_CONTINUOUSLY); // repetir indefinidamente
            secuenciador.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		this.setImgGanaste(Herramientas.cargarImagen("imagenes/ganaste.png")); // IMAGEN GANASTE
		this.setImgPerdiste(Herramientas.cargarImagen("imagenes/perdiste.png")); // IMAGEN PERDISTE
        
        this.zombies = new Zombie[75];
        this.cesped = new Cesped(400, 300, 0, 0);
        this.regalos = new Regalo[5];
        this.plantas = new Planta[5];
        this.bolasDeFuego = new BolaDeFuego[100];
        this.contadorDisparoPlantas = new int[plantas.length];
        this.nuez = new Nuez[5];
		this.setEliminados(new Eliminados());
		this.setRestantes(new Restantes());
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
		restantes.dibujar(entorno);
		tiempo.dibujar(entorno);

		if (perdisteFin) {
			entorno.dibujarImagen(imgPerdiste, 400, 300, 0, 0.7); // imagen PERDISTE
			return;
		}
		entorno.dibujarImagen(cesped.imgCesped, 400, 300, 0, 1.0);
		eliminados.dibujar(entorno); //
		restantes.dibujar(entorno);

		if (!tiempoDetenido) { // tiempo del juego
			tiempo.dibujar(entorno);
		}

		if (eliminados.getContador() >= 10 && !tiempoDetenido) { // se detiene el tiempo del juego con 10 eliminados
			tiempoDetenido = true;
			tiempo.detener();
		}

		if (ganasteFin) {
			entorno.dibujarImagen(imgGanaste, 400, 300, 0, 1.0);// imagen GANASTE
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

   
        for (int i = 0; i < nuez.length; i++) {  ///////ESTO PARA COLISION NUEZ. VER NUEZ.JAVA
        	if (nuez[i] != null) {
                Nuez n = nuez[i];
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

                        if (distancia < 20) {
                            zombies[j].setSalud();
                            bolasDeFuego[i] = null;

                            if (zombies[j].getSalud() <= 0) {
                                zombies[j] = null;
								eliminados.sumarZombie(); // agregue contador de zombies eliminados
                            }
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

                            if (distancia < 20 && !zombies[j].estaComiendo()) {
                                zombies[j].empezarAComer();
                                plantas[i] = null;
                                break;
                            }
                        }
                    }
                }
            }
        }
    
    // Colisiones zombies - nueces.
	
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
							    plantas[i] = null;
							    break;
	                        }
	                    }
	                }
	            }
	        }
	    }
	
	if (eliminados.getContador() >= 20 && !perdisteFin) { // contador de zombies eliminados, AGREGADO
		ganasteFin = true;
		entorno.dibujarImagen(imgGanaste, 400, 300, 0, 1.0); // MUESTRA IMAGEN DE GANASTE
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
                double x = 800;
				int[] filasY = {220, 270, 340, 400, 460};
				double y = filasY[(int) (Math.random() * filasY.length)]; // 220, 270, 340, 400, 460

                Zombie nuevoZombie;
                if (totalZombiesCreados == 4 || totalZombiesCreados == 44
                        || totalZombiesCreados == 59 || totalZombiesCreados == 74) {
                    nuevoZombie = new Zombie(x, y, 100, 0.7, 10, 0.4);
                } else {
                    nuevoZombie = new Zombie(x, y, 80, 0.6, 3, 0.2);
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
	public Image getImgGanaste() {
		return imgGanaste;
	}

	public void setImgGanaste(Image imgGanaste) {
		this.imgGanaste = imgGanaste;
	}

	public Image getImgPerdiste() {
		return imgPerdiste;
	}

	public void setImgPerdiste(Image imgPerdiste) {
		this.imgPerdiste = imgPerdiste;
	}

	public boolean isPerdisteFin() {
		return perdisteFin;
	}

	public void setPerdisteFin(boolean perdisteFin) {
		this.perdisteFin = perdisteFin;
	}
}
