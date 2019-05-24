package ProblemaC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ProblemaB.ProblemaB;

public class ProblemaC2 {
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
	private static ArrayList<String> lista;
	public static void main(String[] args) throws Exception {

		try  
		{ 
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			String l = b.readLine();
			while(l!=null && l.length()>0 && !"0 0".equals(l)) {
				n = Integer.parseInt(l.split(" ")[0]);
				k = Integer.parseInt(l.split(" ")[1]);
				mA = new int[n][3];
				int cont = 0;
				lista = new ArrayList<String>();
				while(cont < n)
				{
					l = b.readLine();	
					final String [] dataStr = l.split(" ");
					int i = Integer.parseInt(dataStr[0]) -1;
					mA[i][0] = Integer.parseInt(dataStr[1]);
					mA[i][1] = Integer.parseInt(dataStr[2]);
					mA[i][2] = Integer.parseInt(dataStr[3]);

					lista.add(cont,mA[i][2]+","+i);

					cont++;
				}
				System.out.println(encontrarMaximaGanancia(n));
				l = b.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static int encontrarUltimoNoCruzado(int id)
	{
		for(int j=id-1;0<=j;j--)
		{
			if(mA[Integer.parseInt(lista.get(j).split(",")[1])][1]<=mA[Integer.parseInt(lista.get(id).split(",")[1])][0])
				return Integer.parseInt(lista.get(j).split(",")[1]);
		}
		return -1;
	}
	public static int encontrarMaximaGanancia( int n)
	{
		Collections.sort(lista);
		
		int[] arreglo = new int[n];
		arreglo[0] = mA[Integer.parseInt(lista.get(0).split(",")[1])][2];

		for(int i = 1;i<n;i++)
		{
			int nuevaGanancia = mA[Integer.parseInt(lista.get(i).split(",")[1])][2];
			int l = encontrarUltimoNoCruzado(i);
			if(l!=-1)
				nuevaGanancia += arreglo[l]; 

			arreglo[i] = nuevaGanancia<arreglo[i-1]? arreglo[i-1]:nuevaGanancia;
		}

		return arreglo[n-1];
	}
}
