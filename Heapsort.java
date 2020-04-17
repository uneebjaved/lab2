public class MaxPQ<Key extends Comparable <Key>> {
    private Key[] pq;
    private int N = 0;


    public MaxPQ(int MaxN){
        pq= (Key[]) new Comparable[MaxN+1];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void insert(Key v){
        pq[++N] = v;
        //swim(N);
    }

    public Key delMax(){
        Key max = pq[1];
        exch(1,N--);
        pq[N+1] = null;
        sink(1);
        return max;
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        Key t = pq[i]; pq[i] = pq[j]; pq[j] = t;
    }

    private void swim(int i){
        while (i > 1 && less(i/2, i)){
            exch(i/2,i);
            i/=2;
        }
    }

    private void sink(int i){
        while(2*i <= N){
            int j = 2*i;
            if(j < N && less(j, j+1))
                j++;
            if(!less(i,j))
                break;
            exch(i,j);
            i = j;
        }
    }
}
