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
				BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
				) { 
			String l = b.readLine();

			while(l!=null && l.length()>0 && !"0 0".equals(l)) {
				int k = Integer.parseInt(l.split(" ")[1]);
				int n = Integer.parseInt(l.split(" ")[0]);
				int [][] m = new int[n-1][n-1];
				while(!"0 0".equals(l))
				{
					l = b.readLine();	
					final String [] dataStr = l.split(" ");
					for(int j = 1;j<dataStr.length-1;j++)
					{
						m[Integer.parseInt(dataStr[0])-1][Integer.parseInt(dataStr[j])-1] = 1;
						m[Integer.parseInt(dataStr[j])-1][Integer.parseInt(dataStr[0])-1] = 1;
					}
				}
				int r = instancia.encontrarCiclos(m,n,k);
				System.out.println(r);
			}
		}
	}
	public int encontrarCiclos(int[][] m,int n, int k)
	{
		boolean marcados[] = new boolean[n]; 

		// Buscar un ciclo usando n-k+1 vertices
		for (int i = 0; i < n - (k - 1); i++) { 
			DFS(m, marcados, k-1, i, i, n); 
	
			marcados[i] = true; 
		} 
		return count / 2;  
	}
	public void DFS(int m[][], boolean marcados[], int k, int v, int s, int n) { 
		
		marcados[v] = true; 

		// Se encuentra un camino de longitud k-1
		if (k == 0) { 
			
			marcados[v] = false; 

			// Revisa el final del ciclo con el inicio (si se conectan)
			if (m[v][s] == 1) { 
				count++; 
				return; 
			}
			else return; 
		} 

		// Fuerza bruta
		for (int i = 0; i < n; i++) 
			if (!marcados[i] && m[v][i] == 1) 

				// Recursion
				DFS(m, marcados, k-1, i, s, n); 

		marcados[v] = false; 
	} 
}
