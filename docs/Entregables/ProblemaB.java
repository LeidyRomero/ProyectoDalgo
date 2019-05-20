import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
/**
 * Programa para buscar el segmento mas largo de un arreglo de enteros b, tal que todos los elementos del segmento sean menores o iguales al valor absoluto del elemento mas a la derecha. 
 * (2019) Recuperado de: https://www.geeksforgeeks.org/cycles-of-length-n-in-an-undirected-and-connected-graph/
 * @author nuclode
 */
public class ProblemaB {
	int count = 0; 
	
	public static void main(String[] args) throws Exception {
		ProblemaB instancia = new ProblemaB();
		try ( 
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
				) { 
			String line = br.readLine();

			while(line!=null && line.length()>0 && !"0 0".equals(line)) {
				int k = Integer.parseInt(line.split(" ")[1]);
				int n = Integer.parseInt(line.split(" ")[0]);
				int cont = 0;
				int [][] matriz = new int[k-1][k-1];
				while(cont<n)
				{
					line = br.readLine();	
					final String [] dataStr = line.split(" ");
					final int[] a = Arrays.stream(dataStr).mapToInt(f->Integer.parseInt(f)).toArray();
					for(int j = 1;j<dataStr.length-1;j++)
					{
						matriz[Integer.parseInt(dataStr[0])-1][Integer.parseInt(dataStr[j])-1] = 1;
						matriz[Integer.parseInt(dataStr[j])-1][Integer.parseInt(dataStr[0])-1] = 1;
					}
					cont++;
				}
				int rta = instancia.encontrarCiclos(matriz,n,k);
				System.out.println(rta);
			}
		}
	}
	public int encontrarCiclos(int[][] matriz,int n, int k)
	{
		//DirectedCycle cyclefinder = new DirectedCycle(matriz,n,k);
		boolean marked[] = new boolean[n]; 

		// Buscar un ciclo usando n-k+1 vertices
		for (int i = 0; i < n - (k - 1); i++) { 
			DFS(matriz, marked, k-1, i, i, n); 
	
			marked[i] = true; 
		} 
		return count / 2;  
	}
	public void DFS(int graph[][], boolean marked[], int k, int vert, int start, int n) { 
		
		marked[vert] = true; 

		// Se encuentra un camino de longitud k-1
		if (k == 0) { 
			
			marked[vert] = false; 

			// Revisa el final del ciclo con el inicio (si se conectan)
			if (graph[vert][start] == 1) { 
				count++; 
				return; 
			}
			else return; 
		} 

		// Fuerza bruta
		for (int i = 0; i < n; i++) 
			if (!marked[i] && graph[vert][i] == 1) 

				// Recursion
				DFS(graph, marked, k-1, i, start, n); 

		marked[vert] = false; 
	} 
}
