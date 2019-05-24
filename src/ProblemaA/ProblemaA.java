package ProblemaA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Programa para buscar el segmento mas largo de un arreglo de enteros b, tal que todos los elementos del segmento sean menores o iguales al valor absoluto del elemento mas a la derecha. 
 * @author Leidy Romero y David Saavedra
 */
public class ProblemaA {
	public static void main(String[] args) throws Exception {
		ProblemaA ins = new ProblemaA();
		try ( 
				BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
				) { 
			String l = b.readLine();
			while(l!=null && l.length()>0 && !"0".equals(l)) {
				l = b.readLine();
				int r = ins.procesarNumeros(Arrays.stream(l.split(" ")).mapToInt(f->Integer.parseInt(f)).toArray());
				System.out.println(r);
				l = b.readLine();
			}
		}
	}
	/**
	 * Retorna la longitud del segmento mas largo de numeros menores o iguales al valor absoluto del mayor valor del arreglo.

	 * @param n arreglo de numeros a revisar
	 * @return l longitud del segmento mas largo de numeros ascendentes del arreglo n
	 */
	public int procesarNumeros(int[] n) {
		int l = 1;
		int k = n.length-1;
		int i = k-1;
		int m = Math.abs(n[k]);
		int m2 = Math.abs(n[i]);
		int p2 = i;
		while(0<=i){
			
			if(n[i]<=m && (k-i+1)>l)
			{
				l = k-i+1;
				if(Math.abs(n[i])>m2)
				{
					m2 = Math.abs(n[i]);
					p2 = i;
				}
			}
			else if(n[i] > m) 
			{
				k--;
				i = k;
				m = Math.abs(n[k]);//TODO revisar
			}
			
			i--;
		}
		return l;
	}
}
