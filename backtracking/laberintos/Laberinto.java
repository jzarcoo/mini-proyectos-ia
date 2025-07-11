package laberintos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import processing.core.PApplet;

/**
 * Clase que crea un laberinto con Processing.
 */
public class Laberinto extends PApplet {
  	private int alto = 10;            // Altura (en celdas) de la cuadricula.
  	private int ancho = 10;           // Anchura (en celdas) de la cuadricula.
  	private int celda = 40;           // Tamanio de cada celda cuadrada (en pixeles).
  	private ModeloLaberinto modelo;   // El objeto que representa el modelo del laberinto.

	/**
	 * Configuración inicial de la applet.
	 */
	@Override
	public void setup() {
		frameRate(60);
		background(50);
		modelo = new ModeloLaberinto(ancho, alto, celda);
	}

	/**
	 * Lee un entero del teclado, tiene que ser no-negativo. Si no lo es, lo pide de nuevo.
	 * @param mensaje Mensaje que se muestra al usuario para que introduzca el entero.
	 * @return Entero introducido.
	 */
	private int pideNumero(String mensaje) {
		int num = 0;
		do {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
				System.out.print(mensaje);
				num = Integer.parseInt(in.readLine());
				if (num <= 0) {
					System.out.println("El valor introducido debe ser mayor que 0.");
				}
			} catch (NumberFormatException e) {
				System.out.println("El valor introducido no es correcto.");
			} catch (IOException e) {
				System.out.println("Error de entrada/salida.");
			}
		} while (num <= 0);
		return num;
	}
  
	/**
	 * Asigna el tamaño de la ventana.
	 */
	@Override
	public void settings() {
		ancho = pideNumero("Introduce el ancho del laberinto: ");
		alto = pideNumero("Introduce la altura del laberinto: ");
		size(ancho * celda, (alto * celda));
	}
  
	/**
	 * Pintar el mundo del modelo.
	 */
	@Override
	public void draw() {
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++){
				fill(204, 204, 204); // gris
				stroke(0);
				rect(j * modelo.getTamanio(), i * modelo.getTamanio(), modelo.getTamanio(), modelo.getTamanio());
				if(!modelo.getCelda(j, i).getEstado()){
					fill(255); // blanco
					rect(j * modelo.getTamanio(), i * modelo.getTamanio(), modelo.getTamanio(), modelo.getTamanio());
				}
				if(modelo.estaVisitado(j, i)){
					fill(255, 204, 204); // rojo claro
					rect(j * modelo.getTamanio(), i * modelo.getTamanio(), modelo.getTamanio(), modelo.getTamanio());
					stroke(255, 204, 204); // rojo claro
				} else {
					stroke(255); // blanco
				}
				if(modelo.esActual(j, i)) {
					fill(255, 0, 0); // rojo
					rect(j * modelo.getTamanio(), i * modelo.getTamanio(), modelo.getTamanio(), modelo.getTamanio());
				}         
				if(!modelo.getCelda(j, i).hayParedNorte()){
					line(j * modelo.getTamanio(), i * modelo.getTamanio(), ((j + 1) * modelo.getTamanio()), i * modelo.getTamanio());                    
				}
				if(!modelo.getCelda(j, i).hayParedEste()){
					line((j * modelo.getTamanio()) + modelo.getTamanio(), i * modelo.getTamanio(), (j + 1) * modelo.getTamanio(), (((i + 1) * modelo.getTamanio())));
				}
				if(!modelo.getCelda(j, i).hayParedSur()){
					line(j * modelo.getTamanio(), (i * modelo.getTamanio()) + modelo.getTamanio(), ((j + 1) * modelo.getTamanio()), ((i + 1) * modelo.getTamanio()));                    
				}
				if(!modelo.getCelda(j, i).hayParedOeste()){
					line(j * modelo.getTamanio(), i * modelo.getTamanio(), j * modelo.getTamanio(), ((i + 1) * modelo.getTamanio()));               
				}            
			} 
		}
		modelo.actualizar();
		delay(50);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		PApplet.main(new String[] { "laberintos.Laberinto" });
	} 
}
