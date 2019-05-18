package ProblemaB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ProblemaB {
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
		DirectedCycle cyclefinder = new DirectedCycle(matriz,n,k);
		return 0;
	}
}
