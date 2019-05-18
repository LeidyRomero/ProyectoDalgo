package ProblemaB;

import java.util.ArrayList;

public class DirectedCycle {

	private boolean[] marcados;
	private int[] edgeTo;
	private ArrayList<Integer> cycle; // vertices on a cycle (if one exists)
	private boolean[] enStack; // vertices on recursive call stack

	public DirectedCycle(int[][] matriz, int numeroVertices, int k)
	{
		enStack = new boolean[numeroVertices];
		edgeTo = new int[numeroVertices];
		marcados = new boolean[numeroVertices];

		for (int v = 0; v < numeroVertices; v++)
			if (!marcados[v]) dfs(matriz, v, matriz[0].length,k);
	}

	private void dfs(int[][] matriz, int fuente, int numeroVertices, int k)
	{
		enStack[fuente] = true;
		marcados[fuente] = true;
		for (int w = 0;w<numeroVertices ;w++)
		{
			if(matriz[fuente][w]>0)
			{ 	
				if (this.hasCycle()) 
				{ 
					return;
				}
				else if (!marcados[w])
				{ 
					edgeTo[w] = fuente; 
					dfs(matriz, w, numeroVertices,k-1); 
				}
				else if (enStack[w])
				{
					cycle = new ArrayList<>();
					for (int x = fuente; x != w; x = edgeTo[x])
						cycle.add(0, x);
					cycle.add(0, w);
					cycle.add(0, fuente);
				}
				else
				{
					//TODO revisar	
				}
			}
		}
		enStack[fuente] = false;
	}

	public boolean hasCycle()
	{ return cycle != null; }

	public Iterable<Integer> cycle()
	{ return cycle; } 

}
