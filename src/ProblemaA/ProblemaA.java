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
		ProblemaA instancia = new ProblemaA();
		try ( 
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
				) { 
			String line = br.readLine();
			int cont = 0;
			while(line!=null && line.length()>0 && !"0".equals(line) && cont<Integer.parseInt(line)) {
				final String [] dataStr = line.split(" ");
				final int[] n = Arrays.stream(dataStr).mapToInt(f->Integer.parseInt(f)).toArray();
				int rta = instancia.procesarNumeros(n);
				System.out.println(rta);
				line = br.readLine();
			}
		}
	}
	/**
	 * l: longitud segmento mas largo
	 */
	public int procesarNumeros(int[] n) {
		int l = 0;
		int i = 0;
		int k = 1;
		while(k<n.length){
			if(n[k]<0) n[k] = n[k]*-1;

			if (n[k-1] <= n[k] && (k-i+1)>l)
				l=k-i+1;
			else if(n[k-1] > n[k])
				i = k;

			k = k+1;
		}
		return l;
	}
}
