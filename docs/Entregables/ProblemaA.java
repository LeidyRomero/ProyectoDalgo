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
	 * l: longitud segmento mas largo
	 * k: Posicion que cambia siempre en el arreglo
	 * i: Posicion que cambia siempre que se encuentre un nuevo segmento
	 * @param n arreglo de numeros a revisar
	 * @return l longitud del segmento mas largo de numeros ascendentes del arreglo n
	 */
	public int procesarNumeros(int[] n) {
		int l = 0;
		int i = 0;
		int k = 1;
		if(k == n.length)
			return 1;
		else
		{
			while(k<n.length){
				if(n[k]<0) n[k] = n[k]*-1;

				if (n[k-1] <= n[k] && (k-i+1)>l)
					l=k-i+1;
				else if(n[k-1] > n[k])
					i = k;

				k = k+1;
			}
		}
		return l;
	}
}
