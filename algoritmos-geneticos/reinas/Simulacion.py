import numpy as np

from Fenotipo import Fenotipo
from Poblacion import Poblacion

def recombinacion(padre1: Fenotipo, padre2: Fenotipo) -> tuple[Fenotipo, Fenotipo]:
    """
    Realiza la recombinación de dos padres para generar dos hijos.

    :param padre1: El primer padre.
    :param padre2: El segundo padre.
    :return: Los dos hijos generados.
    """
    cromosoma1 = padre1.get_cromosoma()
    cromosoma2 = padre2.get_cromosoma()
    cruce = np.random.randint(1, padre1.get_num_genes())
    hijo1 = Fenotipo(cromosoma=np.concatenate((cromosoma1[:cruce], cromosoma2[cruce:])))
    hijo2 = Fenotipo(cromosoma=np.concatenate((cromosoma2[:cruce], cromosoma1[cruce:])))
    return hijo1, hijo2

def algoritmo_genetico(num_poblacion, reinas, max_generaciones, impresiones, num_elitismo, probabilidad_mutacion):
    """
    Ejecuta el algoritmo genético para resolver el problema de las n reinas.

    :param num_poblacion: El número de individuos en la población inicial.
    :param reinas: El número de reinas, o bien, el tamaño del tablero.
    :param max_generaciones: El límite de generaciones.
    :param impresiones: El número de impresiones por generación.
    :param num_elitismo: El número de individuos a mantener de la población en cada generación.
    :param probabilidad_mutacion: La probabilidad de mutación de los genes de los individuos.
    """
    poblacion = Poblacion(num_poblacion, reinas, True)
    poblacion.asignar_aptitud() # ordena individuos
    generacion = 1
    while generacion < max_generaciones and not poblacion.hay_optimo():
          if generacion % impresiones == 0:
                print(f"Mejor solución en iteración {generacion} es: {poblacion.get_optimo()} | fitness: {poblacion.get_optimo().get_aptitud()}")
          nueva_poblacion = Poblacion(num_poblacion, reinas, False)
          nueva_poblacion.agrega_individuo(poblacion.elitismo(num_elitismo))
          while not nueva_poblacion.esta_llena():
                padre1 = poblacion.seleccion_ruleta()
                padre2 = poblacion.seleccion_ruleta()
                hijo1, hijo2 = recombinacion(padre1, padre2)
                hijo1.mutacion(probabilidad_mutacion)
                hijo2.mutacion(probabilidad_mutacion)
                nueva_poblacion.agrega_individuo(hijo1)
                if not nueva_poblacion.esta_llena():
                      nueva_poblacion.agrega_individuo(hijo2)
          poblacion = nueva_poblacion
          poblacion.asignar_aptitud() # ordena individuos
          generacion += 1
    optimo = poblacion.get_optimo()
    if poblacion.hay_optimo():
          print(f"Se encontró el óptimo en la generación: {generacion}")
          print(f"Es: {optimo} | fitness: {optimo.get_aptitud()}")
    else:
          print(f"Mejor solución en iteración {generacion} es: {optimo} | fitness: {optimo.get_aptitud()}")
    optimo.dibuja()

def pide_reinas() -> int:
     """
     Pide al usuario el número de reinas.

     :return: El número de reinas.
     """
     while True:
          try:
               num_reinas = int(input("Introduzca el número de reinas: "))
               if num_reinas <= 0:
                    print("El número de reinas debe ser mayor que 0")
               else:
                    return num_reinas
          except ValueError:
               print("El número de reinas debe ser un número entero")

def main():
    """
    Programa para resolver el problema de las n reinas usando un algoritmo genético.
    """
    POBLACION = 50
    MAX_GENERACIONES = 1000
    IMPRESIONES = 50
    NUM_ELITISMO = 1
    PROBABILIDAD_MUTACION = 0.2
    reinas = pide_reinas()
    algoritmo_genetico(POBLACION, reinas, MAX_GENERACIONES, IMPRESIONES, NUM_ELITISMO, PROBABILIDAD_MUTACION)


if __name__ == "__main__":
    try:
        main()
    except EOFError: # Ctrl + D
        print('Bye')
    except KeyboardInterrupt: # Ctrl + C
        print('Bye')

