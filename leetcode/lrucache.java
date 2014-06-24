import java.util.*;
import java.lang.*;

public class LRUCache
{     
     public static void main(String []args){
        LRUCache cache=new LRUCache(3);
        
        cache.set(1,1);
    	cache.dump();
        cache.set(2,2);
    	cache.dump();
        cache.set(3,3);
    	cache.dump();
        cache.set(4,4);		
		cache.dump();
     }
	 
    hnode[] heap;
    HashMap<Integer, hnode> map=new HashMap<Integer,hnode>();
    
    public LRUCache(int capacity){
        heap=new hnode[capacity];
        for(int i=0;i<capacity;i++){
            heap[i]=new hnode(-1, 0);
            heap[i].datetime=0;
        }
    }
    
    public int get(int key){
        hnode hn=map.get(key);
        if(hn==null){
            return -1;
        }else{
            hn.datetime=System.nanoTime();
            reheap(hn.index);
            return hn.value;
        }
    }
	
	public void set(int key, int value){
        hnode hn=map.get(key);
        if(hn==null){
            if(heap[0].key!=-1){
            map.remove(heap[0].key);
            }
            
            heap[0]=new hnode(key, value);
            map.put(key,heap[0]);
            reheap(0);
        }else{
            hn.value=value;
            hn.datetime=System.nanoTime();
            reheap(hn.index);
        }
	}
        
    public void dump(){
        for(int i=0;i<heap.length;i++)
            System.out.println(heap[i].key+"_"+heap[i].value+"_"+heap[i].datetime);
            System.out.println("");
    }
    
    void reheap(int i){
        int min=i;
        int leftc=i*2+1;
        int rightc=i*2+2;
        if(leftc<heap.length){
            if(heap[leftc].datetime<heap[min].datetime){
                min=leftc;
            }
        }
        if(rightc<heap.length){
            if(heap[rightc].datetime<heap[min].datetime){
                min=rightc;
            }
        }
        
        if(min!=i){
            hnode tmpn=heap[i];
            heap[i]=heap[min];
            heap[min]=tmpn;
            heap[i].index=i;
            heap[min].index=min;
            reheap(min);
        }
    }
    
    public class hnode{
        public hnode(int key, int value){
            this.value=value;
            this.key=key;
            datetime=System.nanoTime();
            index=0;
        }
        public int index;
        public long datetime;
        public int value;
        public int key;
    }
}
