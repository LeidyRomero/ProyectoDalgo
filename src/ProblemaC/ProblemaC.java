package ProblemaC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ProblemaB.ProblemaB;

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
	private static int k;
	/**
	 * Se trata de la cantidad de servicios que me llegan. 
	 */
	private static int n;

	/**
	 * Lista de adyacencia para saber cuales son los que se curzan
	 * Es decir aquellos servicios que tienen días iguales. 
	 */
	public static List<List<Integer>> cruzados;
	public static void main(String[] args) throws Exception {

		try  
		{ 
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			String l = b.readLine();
			while(l!=null && l.length()>0 && !"0 0".equals(l)) {
				cruzados = new ArrayList<List<Integer>>();
				n = Integer.parseInt(l.split(" ")[0]);
				k = Integer.parseInt(l.split(" ")[1]);
				mA = new int[n][3];
				int cont = 0;
				int ganancia = 0;
				int menorValor = Integer.MAX_VALUE;
				while(cont < n)
				{
					l = b.readLine();	
					final String [] dataStr = l.split(" ");
					int i = Integer.parseInt(dataStr[0]) -1;
					mA[i][0] = Integer.parseInt(dataStr[1]);
					mA[i][1] = Integer.parseInt(dataStr[2]);
					mA[i][2] = Integer.parseInt(dataStr[3]);
					ganancia = ganancia + mA[i][2];
					List lista = new ArrayList<Integer>();
					cruzados.add(i, lista);
					if( mA[i][2] < menorValor)
					{
						menorValor = mA[i][2];
					}
					for (int j = 0; j< cruzados.size(); j++)
					{
						if( cruce(j,i) && j!=i )
						{
							cruzados.get(j).add(i);
						}
					}
					cont++;
				}
				new ProblemaC(ganancia, menorValor);
				l = b.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public  ProblemaC(int gananciaIdeal , int menorGanancia) {
		// Necesito dos ciclos:
		// El primero sería por la cantidad de equipos de trabajo que tengo 
		int equActual = 0;
		int cota = gananciaIdeal;
		int gananciaMaxima = 0;
		// Se refiere a los servicios que ya están tomado como ni siquiera he empezado no tomo ninguno
		ArrayList<Integer> serTomados = new ArrayList<>();
		while (equActual < k)
		{
//			System.out.println("Primer while");
			// El segundo que buscaría una solución para el equipo con el que estoy trabajando. 
			// En el peor de los casos un equipo al menos podría hacer solo un servicio
			// por lo que eso estará en el while
			boolean solucion = false;

			while(!solucion)
			{
//				System.out.println(cota);
//				System.out.println("Segundo while");
				//Llamamos a aver si encontramos solución con el valor que tenmos. 
				subconjunto solution = findFeasibleSolution(cota, serTomados);
				//Si no tenemos solución restamos el valor minimo de valores que nos llegó del archivo
				if(solution==null) {
					cota -=  menorGanancia;
				} else {
					// Si tenemos solución terminamos la busqueda, marco los grafos, 
					// sumo a la ganancia máxima y sigo con el siguiente grupo de trabajo	
					//ME
					// Falta marcar los servicios que ya hice 
					int gananciaParcial = solution.darGananciasTotales(mA);
					gananciaMaxima += gananciaParcial;
					for (int i : solution.darServicios()) {
						serTomados.add(i);
					}
					solucion = true;
					cota = gananciaParcial;
				}
			}
			equActual++;
		}
		System.out.println(gananciaMaxima);
	}

	private subconjunto findFeasibleSolution(int cota, ArrayList<Integer> tomados) {
		subconjunto answer = null;

		//Estado inicial es vacio por lo que sería solo el arreglo creado
		ArrayList<Integer> servicios = new ArrayList<Integer>();
		subconjunto estado = new subconjunto(servicios);
		//Agenda
		Queue<subconjunto> agenda = new LinkedList<>();
		agenda.add(estado);
		while(agenda.size()>0 && answer == null) {
			//Choose next state from the agenda
			estado = agenda.poll();
			if(isViable(estado))
			{
				if(satisfacibilidad(estado,cota)) {
					answer = estado;
				} else {
					//Add successors to the agenda
					List<subconjunto> successors = sucesores(estado, tomados);
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
	private List<subconjunto> sucesores(subconjunto estado, ArrayList<Integer> tomados) {
		ArrayList<Integer> servicios = (ArrayList<Integer>) estado.darServicios().clone();
		List<subconjunto> successors = new ArrayList<subconjunto>();
		for( int i = 0; i<n ;i++)
		{
			int servicioAgregado = -1;
			if(servicios.isEmpty())
			{
				if(!tomados.contains(i))
				{
					servicios.add(i);
					servicioAgregado = servicios.indexOf(i);	
				}
				else
					continue;
			}
			else if(servicios.contains(i) || tomados.contains(i))
			{
				continue;
			}
			else
			{
				servicios.add(i);
				servicioAgregado = servicios.indexOf(i);
			}
			subconjunto suc = new subconjunto(servicios);
			successors.add(suc);
			servicios.remove(servicioAgregado);
		}
		
		return successors;
	}
	private static boolean cruce(int j, int i)
	{
		
		int jInicial = mA[j][0];
		int jFinal = mA[j][1];
		int iInicial = mA[i][0];
		int iFinal = mA[i][1];

		if( iFinal < jInicial || iInicial > jFinal )
		{
			return false;
		}

		return true;
	}
	/**
	 * Determines if the given state could lead to a solution. This function implements the branch and
	 * bound strategy within the graph exploration algorithm
	 * @param state that will be checked for viability. 
	 * @return boolean true if the total value of the given state is less or equal than the value to be completed
	 * NO SE CRUZA
	 */
	private boolean isViable(subconjunto state) {
		ArrayList<Integer> servicios = state.darServicios();
		for(int i = 0; i< servicios.size();i++)
		{
			List<Integer> conectados = cruzados.get(servicios.get(i));
			for (int j : conectados) {
				if(servicios.contains(j))
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determines if the given state is a solution. Implements the satisfiability predicate of the
	 * graph exploration algorithm
	 * @param state that will be checked
	 * @param cota Maximum number of coins allowed
	 * @return boolean true if the total value of the given state is equal to the value to be completed
	 */
	private boolean satisfacibilidad(subconjunto state, int cota) {
		return state.darGananciasTotales(mA)>=cota;
	}
}
/**
 *
 * @author Leidy Romero y David Saavedra
 */
class subconjunto {
	ArrayList<Integer> servicios;

	/**
	 * Creates a new state with the given configuration of coins
	 * @param s Number of coins of each denomination.
	 * This array is copied internally to allow posterior modifications
	 */
	public subconjunto(ArrayList<Integer> s) {
		this.servicios =(ArrayList<Integer>) s.clone();
	}
	/**
	 * Calculates the total number of coins in this state
	 * @return in Sum of the coins of each denomination
	 */
	public int darGananciasTotales (int[][] mA) {
		int s = 0;

		for(int i=0;i<servicios.size();i++) {
			s+=mA[servicios.get(i)][2];
		}
		return s;
	}
	/**
	 * Returns the number of coins of each denomination
	 * @return int [] number of coins of each denomination. The array is returned as is
	 */
	public ArrayList<Integer> darServicios() {
		return servicios;
	}
}
