package laberintos;

/**
 * Clase que representa cada celda de la cuadricula.
 */
class Celda{
    /* Coordenada x de la celda. */
    private int celdaX; 
    /* Coordenada y de la celda. */
    private int celdaY;
    /* Pared norte de la celda. */
    private boolean pared_1;
    /* Pared este de la celda. */
    private boolean pared_2;
    /* Pared sur de la celda. */
    private boolean pared_3;
    /* Pared oeste de la celda. */
    private boolean pared_4;
    /* Estado de la celda. */
    private boolean estado;
    
    /** Constructor de una celda.
    *@param celdaX Coordenada en x
    *@param celdaY Coordenada en y
    *@param estado Estado de la celda. true si no ha sido visitada, false en otro caso.
    */
    Celda(int celdaX, int celdaY, boolean estado){
        this.celdaX = celdaX;
        this.celdaY = celdaY;
        this.estado = estado;
        this.pared_1 = true; // Booleano que representa la pared de arriba
        this.pared_2 = true; // Booleano que representa la pared de la derecha
        this.pared_3 = true; // Booleano que representa la pared de abajo
        this.pared_4 = true; // Booleano que representa la pared de la izquierda
    }

    /**
     * Regresa la coordenada en x de la celda.
     * @return la coordenada en x de la celda.
     */
    public int getCeldaX() {
        return celdaX;
    }

    /**
     * Regresa la coordenada en y de la celda.
     * @return la coordenada en y de la celda.
     */
    public int getCeldaY() {
        return celdaY;
    }

    /**
     * Establece el estado de la celda.
     * @param estado el estado de la celda: <code>true</code> si no ha sido visitada, <code>false</code> en otro caso.
     */
    public void setEstado(boolean estado){
        this.estado = estado;
    }

    /**
     * Regresa el estado de la celda.
     * @return el estado de la celda: <code>true</code> si no ha sido visitada, <code>false</code> en otro caso.
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Regresa la pared de arriba de la celda.
     * @return <code>true</code> si la celda tiene pared de arriba, <code>false</code> en otro caso.
     */
    public boolean hayParedNorte(){
        return pared_1;
    }

    /**
     * Establece la pared de arriba de la celda.
     * @param pared_1 <code>true</code> si la celda tiene pared de arriba, <code>false</code> en otro caso.
     */
    public void setParedNorte(boolean pared_1) {
        this.pared_1 = pared_1;
    }

    /**
     * Regresa la pared de la derecha de la celda.
     * @return <code>true</code> si la celda tiene pared de la derecha, <code>false</code> en otro caso.
     */
    public boolean hayParedEste(){
        return pared_2;
    }

    /**
     * Establece la pared de la derecha de la celda.
     * @param pared_2 <code>true</code> si la celda tiene pared de la derecha, <code>false</code> en otro caso.
     */
    public void setParedEste(boolean pared_2) {
        this.pared_2 = pared_2;
    }
     

    /**
     * Regresa la pared de abajo de la celda.
     * @return <code>true</code> si la celda tiene pared de abajo, <code>false</code> en otro caso.
     */
    public boolean hayParedSur(){
        return pared_3;
    }

    /**
     * Establece la pared de abajo de la celda.
     * @param pared_3 <code>true</code> si la celda tiene pared de abajo, <code>false</code> en otro caso.
     */
    public void setParedSur(boolean pared_3) {
        this.pared_3 = pared_3;
    }

    /**
     * Regresa la pared de la izquierda de la celda.
     * @return <code>true</code> si la celda tiene pared de la izquierda, <code>false</code> en otro caso.
     */
    public boolean hayParedOeste(){
        return pared_4;
    }

    /**
     * Establece la pared de la izquierda de la celda.
     * @param pared_4 <code>true</code> si la celda tiene pared de la izquierda, <code>false</code> en otro caso.
     */
    public void setParedOeste(boolean pared_4) {
        this.pared_4 = pared_4;
    }
}  