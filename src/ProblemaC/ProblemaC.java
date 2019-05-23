package ProblemaC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProblemaC {
	//Input data
		static private int[][] mA;
		/**
		 * Se refiere a la suma total de ganancia que podemos recoger si tenemos recursos para 
		 * hacer todas las actividades 
		 */
		private int sumaTotal;
		/**
		 * Se trata de la actividad de menor valor que hay y usaremos para disminuir el valor 
		 * al problema de desición asociado.
		 */
		private int menorValor;
		/**
		 * Se trata de la cantidad de equipos de trabajo que tengo para
		 * hacer los servicios que me llegan. 
		 */
		private int k;
		public int[] calculateOptimalChange(int totalValue, int[] denominations) {

			// acá inicializaría k con el valor que leo del string
			k=0;
			// Necesito dos ciclos:
			// El primero sería por la cantidad de equipos de trabajo que tengo 
			int equActual = 0;
			while (equActual < k)
			{
				// El segundo que buscaría una solución para el equipo con el que estoy trabajando. 
				// En el peor de los casos un equipo al menos podría hacer solo un servicio
				// por lo que eso estará en el while
				boolean solucion = false;
				while(!solucion) {
					int boundNumberOfCoins = (maxGanancia+maxCoins)/2;
					//Llamamos a aver si encontramos solución con el valor que tenmos. 
					subconjunto solution = findFeasibleSolution(boundNumberOfCoins);
					//Si no tenemos solución restamos el valor minimo de valores que nos llegó del archivo
					if(solution==null) {
						maxGanancia = boundNumberOfCoins+1;
					} else {
					// Si tenemos solución terminamos la busqueda, marco los grafos y sigo con el siguiente grupo de trabajo	
						opt = solution;
						solucion = true;
					}
				}
				k++;
			}
			return opt.darServicios();
		}

		private subconjunto findFeasibleSolution(int boundNumberOfCoins) {
			subconjunto answer = null;
			//Initial state
			int [] coins = new int [denominations.length];
			Arrays.fill(coins, 0);
			subconjunto state = new subconjunto(coins);
			//Agenda
			Queue<subconjunto> agenda = new LinkedList<>();
			agenda.add(state);
			while(agenda.size()>0 && answer == null) {
				//Choose next state from the agenda
				state = agenda.poll();
				if(isViable(state)) {
					if(satisfacibilidad(state,boundNumberOfCoins)) {
						answer = state;
					} else {
						//Add successors to the agenda
						List<subconjunto> successors = sucesores (state);
						agenda.addAll(successors);
					}
				}
			}
			return answer;
		}
		/**
		 * Calculates the successors of the given state. Successors are all states formed adding
		 * one coin of each denomination
		 * @param state source state to define successors
		 * @return List<CoinChangeState> successors of the given state
		 */
		private List<subconjunto> sucesores(subconjunto state) {
			int [] s = Arrays.copyOf(state.darServicios(), state.darServicios().length);
			List<subconjunto> successors = new ArrayList<>(s.length);
			for(int i=0;i<s.length;i++) {
				s[i]++;
				subconjunto suc = new subconjunto(s);
				successors.add(suc);
				s[i]--;
			}
			return successors;
		}
		/**
		 * Determines if the given state could lead to a solution. This function implements the branch and
		 * bound strategy within the graph exploration algorithm
		 * @param state that will be checked for viability. 
		 * @return boolean true if the total value of the given state is less or equal than the value to be completed
		 */
		private boolean isViable(subconjunto state) {
			return true;//TODO
		}

		/**
		 * Determines if the given state is a solution. Implements the satisfiability predicate of the
		 * graph exploration algorithm
		 * @param state that will be checked
		 * @param cota Maximum number of coins allowed
		 * @return boolean true if the total value of the given state is equal to the value to be completed
		 */
		private boolean satisfacibilidad(subconjunto state, int cota) {
			return state.darGananciasTotales(mA)>=cota && cruce();
		}
		private boolean cruce()
		{
			//TODO
			return true;
		}
	}
	/**
	 *
	 * @author Leidy Romero y David Saavedra
	 */
	class subconjunto {
		int[] servicios;

		/**
		 * Creates a new state with the given configuration of coins
		 * @param s Number of coins of each denomination.
		 * This array is copied internally to allow posterior modifications
		 */
		public subconjunto(int[] s) {
			this.servicios = Arrays.copyOf(s, s.length);
		}
		/**
		 * Calculates the total number of coins in this state
		 * @return in Sum of the coins of each denomination
		 */
		public int darGananciasTotales (int[][] mA) {
			int s = 0;
			
			for(int i=0;i<servicios.length;i++) {
				s+=mA[servicios[i]][3];
			}
			return s;
		}
		/**
		 * Returns the number of coins of each denomination
		 * @return int [] number of coins of each denomination. The array is returned as is
		 */
		public int[] darServicios() {
			return servicios;
		}
}
