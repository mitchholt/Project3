/*************************************************************************
 *  Compilation:  javac LinearProbingHashST.java
 *  Execution:    java LinearProbingHashST
 *
 *  Symbol table implementation with linear probing hash table.
 *
 *  % java LinearProbingHashST
 *  128.112.136.11
 *  208.216.181.15
 *  null
 *
 *
 *************************************************************************/
import java.util.*;

public class LinearProbingHashST<Key extends Comparable<Key>, Value>{
    private static final int INIT_CAPACITY = 22222223;
    private int N;           // number of key-value pairs in the symbol table
    private int M;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values
    static double startTest, endTest;
    // create an empty hash table - use 16 as default size
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }
    
    // create linear proving hash table of given capacity
    public LinearProbingHashST(int capacity) {
        M = capacity;
        keys = (Key[])   new Comparable[M];
        vals = (Value[]) new Object[M];
    }
    
    // return the number of key-value pairs in the symbol table
    public int size() {
        return N;
    }
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return search(key) != null;
    }
    
    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode()  & 0x7fffffff ) % M;
    }
    
    // insert the key-value pair into the symbol table
    public void insert(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        
        // double table size if 50% full
        
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) { vals[i] = val; return; }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }
    
    // return the value associated with the given key, null if no such value
    public Value search(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    
    // delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        if (!contains(key)) return;
        
        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        
        // delete key and associated value
        keys[i] = null;
        vals[i] = null;
        
        // rehash all keys in same cluster
        i = (i + 1) % M;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            insert(keyToRehash, valToRehash);
            i = (i + 1) % M;
        }
        
        N--;
        

    }
    
    // return all of the keys as in Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }
    
    public int rank(Key key){
    	int rank = 0;
    	for (int i = 0; i < M; i++)
    	{
    		if (keys[i] == null)
    			continue;
    		if (key.compareTo(keys[i]) > 0)
    			rank++;
    	}
    	return rank;
    }
    
    public Key getValByRank(int k){
    	
    	if (k > N || k < 0)
    		return null;
    	MaxHeap<Key> heap = new MaxHeap<Key>(k+1);
    	int index = 0;
    	int keyCount = 0;
    	while (keyCount <= k && index < M)
    	{
    		if (keys[index] != null)
    		{
    			heap.insert(keys[index]);
    			keyCount++;
    		}
    		index++;
    	}
    	Key max = heap.returnMax();
    	while (index < M)
    	{
    		if (keys[index] != null)
    		{
    			if (max.compareTo(keys[index]) > 0)
    			{
    				heap.delMax();
    				heap.insert(keys[index]);
    			}
    			
    		}
    		index++;
    	}
    	return heap.returnMax();
    }
    
    public Iterable<Key> kSmallest(int k){
    	if (k < 0 || k > N)
    		return null;
        /* TODO: Implement kSmallest here... */
    	Queue<Key> kSmallestKeys = new Queue<Key>();
    	MaxHeap<Key> heap = new MaxHeap<Key>(k);
    	int index = 0;
    	int keyCount = 0;
    	while (keyCount < k && index < M)
    	{
    		if (keys[index] != null)
    		{
    			heap.insert(keys[index]);
    			keyCount++;
    		}
    		index++;
    	}
    	Key max = heap.returnMax();
    	while (index < M)
    	{
    		if (keys[index] != null)
    		{
    			if (max.compareTo(keys[index]) > 0)
    			{
    				heap.delMax();
    				heap.insert(keys[index]);
    			}
    		}
    		index++;
    	}
    	for (int i = 0; i < k; i++)
    	{
    		kSmallestKeys.enqueue(heap.delMax());
    	}
    	
    	return kSmallestKeys;
    }
    
    public Iterable<Key> kLargest(int k){
    	if (k < 0 || k > N)
    		return null;
        /* TODO: Implement kLargest here... */
    	Queue<Key> kLargestKeys = new Queue<Key>();
    	MinHeap<Key> heap = new MinHeap<Key>(k);
    	int index = 0;
    	int keyCount = 0;
    	while (keyCount < k && index < M)
    	{
    		if (keys[index] != null)
    		{
    			heap.insert(keys[index]);
    			keyCount++;
    		}
    		index++;
    	}
    	Key min = heap.returnMin();
    	while (index < M)
    	{
    		if (keys[index] != null)
    		{
    			if (min.compareTo(keys[index]) < 0)
    			{
    				heap.delMin();
    				heap.insert(keys[index]);
    			}
    		}
    		index++;
    	}
    	for (int i = 0; i < k; i++)
    	{
    		kLargestKeys.enqueue(heap.delMin());
    	}
    	
    	return kLargestKeys;
    }
    
    public int rangeCount(Key low, Key high){
        /* TODO: Implement rangeCount here... */
    	if (high.compareTo(low) < 0)
    		return 0;
    	int count = 0;
    	for (int i = 0; i < M; i++)
    	{
    		if (keys[i] == null)
    			continue;
    		int cmp1 = low.compareTo(keys[i]);
    		int cmp2 = high.compareTo(keys[i]);
    		if (cmp1 <= 0 && cmp2 >= 0)
    			count++;
    	}
    	return count;
    }
    
    
}
