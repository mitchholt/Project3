// Sedgewick, Robert, and Kevin Daniel Wayne. Algorithms. Fourth ed. Upper Saddle River, NJ: Addison-Wesley, 2011. Print.
// basically the book implementation with one changed line to make it a minheap rather than max

import java.util.*;

class MinHeap<Key extends Comparable<Key>> {
	private Key[] pq;
	private int N = 0;
	
	public MinHeap(int maxN) 
	{
		pq = (Key[]) new Comparable[maxN + 1];
	}
	
	public boolean isEmpty()
	{
		return N == 0;
	}
	
	public int size()
	{
		return N;
	}
	
	public void insert(Key v)
	{
		pq[++N] = v;
		swim(N);
	}
	
	private void swim(int k)
	{
		while (k > 1 && !less(k/2, k))
		{
			exch(k/2, k);
			k = k/2;
		}
	}
	
	private void sink(int k)
	{
		while (2*k <= N)
		{
			int j = 2*k;
			if (j < N && !less(j, j+1)) 
				j++;
			if (less(k, j)) 
				break;
			exch(k, j);
			k = j;
		}
	}
	
	public Key delMin()
	{
		Key min = pq[1];
		exch(1, N--);
		pq[N+1] = null;
		sink(1);
		return min;
	}
	
	public Key returnMin()
	{
		return pq[1];
	}
	
	private boolean less(int i, int j)
	{
		return pq[i].compareTo(pq[j]) < 0;
	}
	
	private void exch(int i, int j)
	{
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}

}
