package laberintos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Clase que modela el laberinto, es decir, crea el mundo del laberinto.
 */
class ModeloLaberinto{
    private int ancho;  // Tamaño de celdas a lo ancho de la cuadrícula.
    private int alto;  // Tamaño de celdas a lo largo de la cuadrícula.
    private int tamanio;  // Tamaño en pixeles de cada celda.
    private Celda[][] mundo;  // Mundo de celdas
    /* Pila de celdas */
    private Stack<Celda> pila;        
    /* Conjunto de celdas visitadas */
    private Set<Celda> visitados;
    /* Generador de numeros aleatorios */
    private static final Random rnd = new Random();
    /* Celda actual */
    private Celda actual;
    
    /** Constructor del modelo
     * @param ancho Cantidad de celdas a lo ancho en la cuadricula.
     * @param ancho Cantidad de celdas a lo largo en la cuadricula.
     * @param tamanio Tamaño (en pixeles) de cada celda cuadrada que compone la cuadricula.
     */
    ModeloLaberinto(int ancho, int alto, int tamanio){
        this.ancho = ancho;
        this.alto = alto;
        this.tamanio = tamanio;
        mundo = new Celda[alto][ancho];
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
                mundo[i][j] = new Celda(j,i, true);
        pila = new Stack<Celda>();
        visitados = new HashSet<Celda>();
        // Celda inicial aleatoria
        int x = rnd.nextInt(ancho);
        int y = rnd.nextInt(alto);
        mundo[y][x].setEstado(false);
        pila.push(mundo[y][x]);
        actual = mundo[y][x];
    }

    /**
     * Comprueba si la celda indicada ha sido visitada.
     * @param x Coordenada en x de la celda a comprobar.
     * @param y Coordenada en y de la celda a comprobar.
     * @return <code>true</code> si la celda indicada ha sido visitada, <code>false</code> en otro caso.
     */
    public boolean estaVisitado(int x, int y){
        return visitados.contains(mundo[y][x]);
    }

    /**
     * Comprueba si la celda indicada es la actual.
     * @param x Coordenada en x de la celda a comprobar.
     * @param y Coordenada en y de la celda a comprobar.
     * @return true si la celda indicada es la actual, false en otro caso.
     */
    public boolean esActual(int x, int y){
        if(actual == null) return false; // no dibuje de color rojo
        return actual.getCeldaX() == x && actual.getCeldaY() == y;
    }

    /**
     * Mueve a la celda en la direccion indicada.
     * @param dir Direccion a la que se quiere mover la celda.
     * @param celda Celda que se quiere mover.
     * @return La celda que se ha movido.
     */
    private Celda moverse(Direccion dir, Celda celda) {
        switch (dir) {
            case NORTE:
                mundo[celda.getCeldaY()][celda.getCeldaX()].setParedNorte(false);
                mundo[celda.getCeldaY() - 1][celda.getCeldaX()].setParedSur(false);
                celda = mundo[celda.getCeldaY() - 1][celda.getCeldaX()];
                break;
            case SUR:
                mundo[celda.getCeldaY()][celda.getCeldaX()].setParedSur(false);
                mundo[celda.getCeldaY() + 1][celda.getCeldaX()].setParedNorte(false);
                celda = mundo[celda.getCeldaY() + 1][celda.getCeldaX()];
                break;
            case OESTE:
                mundo[celda.getCeldaY()][celda.getCeldaX()].setParedOeste(false);
                mundo[celda.getCeldaY()][celda.getCeldaX() - 1].setParedEste(false);
                celda = mundo[celda.getCeldaY()][celda.getCeldaX() - 1];
                break;
            case ESTE:
                mundo[celda.getCeldaY()][celda.getCeldaX()].setParedEste(false);
                mundo[celda.getCeldaY()][celda.getCeldaX() + 1].setParedOeste(false);
                celda = mundo[celda.getCeldaY()][celda.getCeldaX() + 1];
                break;
        }
        return celda;
    }

    /**
     * Obtiene las celdas vecinas disponibles.
     * @param celda Celda a la que se quiere obtener las celdas vecinas.
     * @return Lista de las direcciones de las celdas vecinas disponibles.
     */
    private List<Direccion> getVecinos(Celda celda) {
        int x = celda.getCeldaX();
        int y = celda.getCeldaY();
        List<Direccion> vecinos = new ArrayList<>();
        if (y > 0 && mundo[y - 1][x].getEstado()) vecinos.add(Direccion.NORTE);
        if (y < alto - 1 && mundo[y + 1][x].getEstado()) vecinos.add(Direccion.SUR);
        if (x > 0 && mundo[y][x - 1].getEstado()) vecinos.add(Direccion.OESTE);
        if (x < ancho - 1 && mundo[y][x + 1].getEstado()) vecinos.add(Direccion.ESTE);
        return vecinos;
    }

    /**
     * Actualiza el laberinto.
     * Avanza la celda actual un paso.
     */
    public void actualizar() {
        if (pila.isEmpty()) {
            actual = null;
            return;
        }
        actual = pila.peek(); 
        List<Direccion> vecinos = getVecinos(actual);
        if (vecinos.isEmpty()) {
            Celda eliminada = pila.pop();
            visitados.remove(eliminada);
            return;
        }  
        int i = rnd.nextInt(vecinos.size());
        Direccion dir = vecinos.get(i);
        Celda nuevaCelda = moverse(dir, actual);
        nuevaCelda.setEstado(false);
        pila.push(nuevaCelda);
        visitados.add(actual);
        actual = nuevaCelda;
    }

    /**
     * Regresa el tamaño del laberinto.
     * @return el tamaño del laberinto
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Regresa la celda indicada.
     * @param x Coordenada en x de la celda a regresar.
     * @param y Coordenada en y de la celda a regresar.
     * @return la celda indicada.
     */
    public Celda getCelda(int x, int y) {
        return mundo[y][x];
    }
}