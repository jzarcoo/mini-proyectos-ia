import numpy as np
import matplotlib.pyplot as plt

class Fenotipo:
    def __init__(self, num_genes=None, cromosoma=None):
        """
          Inicializa un individuo con un cromosoma aleatorio si no se proporciona uno.

          :param cromosoma: El cromosoma del individuo.
          :param num_genes: El número de genes en el cromosoma.
        """
        if cromosoma is None:
            self.cromosoma = np.random.randint(1, num_genes + 1, num_genes)
        else:
            self.cromosoma = cromosoma

        self.num_genes = len(self.cromosoma)

    def calcular_ataques(self) -> int:
        """
        Calcula el número de ataques en el tablero del individuo, i.e., el número de pares de reinas que se
        atacarán entre sí.

        :return: El número de ataques en el tablero del individuo.
        """
        ataques = 0
        for i in range(self.num_genes):
            for j in range(i + 1, self.num_genes):
                misma_fila = self.cromosoma[i] == self.cromosoma[j]
                # Diagonales
                izquierda_derecha = self.cromosoma[i] - (i+1) == self.cromosoma[j] - (j+1)
                derecha_izquierda = self.cromosoma[i] + (i+1) == self.cromosoma[j] + (j+1)
                misma_diagonal = izquierda_derecha or derecha_izquierda
                if misma_fila or misma_diagonal:
                    ataques += 1
        return ataques

    def calcular_aptitud(self):
        """
        Calcula la aptitud del individuo.

        :return: La aptitud del individuo
        """
        self.aptitud = 1 / (1 + self.calcular_ataques())

    def get_aptitud(self) -> float:
        """
        Obtiene la aptitud del individuo.

        :return: La aptitud del individuo.
        """
        return self.aptitud

    def get_cromosoma(self) -> np.ndarray:
        """
        Obtiene el cromosoma del individuo.

        :return: El cromosoma del individuo.
        """
        return self.cromosoma

    def get_num_genes(self) -> int:
        """
        Obtiene el número de genes en el cromosoma del individuo.

        :return: El número de genes en el cromosoma del individuo.
        """
        return self.num_genes

    def mutacion(self, tasa_mutacion):
        """
        Realiza la mutación del cromosoma del individuo.

        :param tasa_mutacion: La probabilidad de mutación para cada gen.
        :return: El individuo mutado.
        """
        for i in range(len(self.cromosoma)):
            if np.random.rand() < tasa_mutacion:
                self.cromosoma[i] = np.random.randint(1, self.num_genes)
        return self

    def __str__(self) -> str:
        """
        Devuelve una representación en cadena del individuo (tablero).

        :return: Una cadena que representa el individuo (tablero).
        """
        return str(self.cromosoma)

    def dibuja(self):
        """
        Dibuja el tablero del individuo (tablero).
        """
        fig, ax = plt.subplots()
        fig.set_size_inches(8, 8)
        ax.set_xlim(0, self.num_genes)
        ax.set_ylim(0, self.num_genes)
        ax.set_xticks([])
        ax.set_yticks([])
        # Tablero
        for i in range(self.num_genes):
            for j in range(self.num_genes):
                color = 'white' if (i + j) % 2 == 0 else 'black'
                ax.add_patch(plt.Rectangle((j, self.num_genes - i - 1), 1, 1, facecolor=color))
        # Reinas
        for columna, fila in enumerate(self.cromosoma):
            ax.text(columna + 0.5, fila - 0.5, '♛', fontsize=20, ha='center', va='center', color='white')
            ax.add_patch(plt.Rectangle((columna, fila - 1), 1, 1, facecolor='gold'))
        plt.title(f"Tablero: {self} | Fitness: {self.aptitud:.2f}", fontsize=25)
        plt.tight_layout()
        plt.show()
