import numpy as np

from Fenotipo import Fenotipo

class Poblacion:
    def __init__(self, num_individuos, num_genes, llenar):
        """
        Inicializa una población de individuos.

        :param num_individuos: El número de individuos en la población.
        :param num_genes: El número de genes en cada individuo.
        """
        self.num_individuos = num_individuos
        self.num_genes = num_genes
        self.individuos = np.array([], dtype=Fenotipo)
        if llenar:
            self.llenar()

    def agrega_individuo(self, individuo):
        """
        Agrega un individuo a la población.

        :param individuo: El individuo a agregar.
        """
        self.individuos = np.append(self.individuos, individuo)

    def asignar_aptitud(self):
        """
        Calcula la aptitud de cada individuo en la población.
        """
        for individuo in self.individuos:
            individuo.calcular_aptitud()
        self.ordenar_individuos()

    def elitismo(self, num) -> list:
        """
        Selecciona los individuos más aptos para la siguiente generación,
        se asume que la población está ordenada de forma descendente antes de llamar a esta función,
        los individuos seleccionados son eliminados de la población original.

        :param num: El número de individuos a seleccionar.
        :return: Los individuos seleccionados.
        """
        return self.individuos[:num]

    def esta_llena(self) -> bool:
        """
        Verifica si la población está llena.

        :return: True si la población está llena, False en caso contrario.
        """
        return len(self.individuos) >= self.num_individuos

    def get_optimo(self):
        """
        Obtiene el individuo óptimo (aptitud máxima) de la población,
        se asume que la población está ordenada de forma descendente antes de llamar a esta función.

        :return: El individuo óptimo.
        """
        return self.individuos[0]

    def hay_optimo(self) -> bool:
        """
        Verifica si se ha encontrado un individuo óptimo (aptitud máxima),
        se asume que la población está ordenada de forma descendente antes de llamar a esta función.

        :return: True si se ha encontrado un individuo óptimo, False en caso contrario.
        """
        return self.individuos[0].aptitud == 1

    def llenar(self):
        """
        Llena la población con individuos aleatorios.
        """
        while not self.esta_llena():
            self.agrega_individuo(Fenotipo(num_genes=self.num_genes))

    def ordenar_individuos(self):
        """
        Ordena los individuos en la población por su aptitud de forma descendente.
        """
        self.individuos = np.array(sorted(self.individuos, key=lambda ind: ind.aptitud, reverse=True))

    def seleccion_ruleta(self):
        """
        Selecciona un individuo de la población utilizando la selección proporcional de aptitud.

        :return: El individuo seleccionado.
        """
        aptitudes = np.array([individuo.aptitud for individuo in self.individuos])
        probabilidades = aptitudes / np.sum(aptitudes)
        seleccion = np.random.choice(self.individuos, p=probabilidades)
        return seleccion

    def __str__(self) -> str:
        """
        Devuelve una representación en cadena de la población de individuos.

        :return: Una cadena que representa la población de individuos.
        """
        return "\n".join([str(ind) for ind in self.individuos])