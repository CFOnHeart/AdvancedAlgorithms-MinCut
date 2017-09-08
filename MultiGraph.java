/**
 * Created by ganjun on 2017/9/8.
 */
public class MultiGraph {
    public int size;
    public int edges[][] ;

    public MultiGraph(int size){
        this.size = size;
        edges = new int[size][size];
        for(int i=0 ; i<size ; i++){
            for(int j=0 ; j<size ; j++){
                edges[i][j] = 0;
            }
        }
    }

    public boolean addEdge(int u , int v){
        if(u>=size && v>=size) return false;
        edges[u][v] ++;
        edges[v][u] ++;
        return true;
    }
    public boolean addEdge(int u , int v , int cnt){
        if(u>=size && v>=size) return false;
        edges[u][v] += cnt;
        edges[v][u] += cnt;
        return true;
    }
    public void show(){
        for(int i=0 ; i<size ; i++){
            for(int j=0 ; j<size ; j++){
                System.out.print(edges[i][j]+" ");
            }
            System.out.println("");
        }
    }
}
