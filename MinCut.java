/**
 * Created by ganjun on 2017/9/8.
 */

import java.io.FileReader;
import  java.util.Vector;
public class MinCut {
    //从G中收缩一条uv的边返回一个减少一个点的新图
    public MultiGraph Contract(MultiGraph G , int u , int v){
        int id[] = new int [G.size];
        int tot = 0;
        for(int i=0 ; i<G.size ; i++){
            if(i!=u && i!=v){
                id[i] = tot++;
            }
        }
        MultiGraph newG = new MultiGraph(G.size-1);
        for(int i=0 ; i<G.size ; i++){
            for(int j=i+1 ; j<G.size ; j++){
                if((i==u || i==v)==true && (j==u || j==v)==true) continue;
                else if((i==u || i==v)==false && (j==u || j==v)==true)
                    newG.addEdge(tot , id[i] , G.edges[i][j]);
                else if((i==u || i==v)==true && (j==u || j==v)==false)
                    newG.addEdge(tot , id[j] , G.edges[i][j]);
                else
                    newG.addEdge(id[i] , id[j] , G.edges[i][j]);

            }
        }
        return newG;
    }
    //将图的点收缩直到剩余t个点
    public MultiGraph RandomContract(MultiGraph G , int t){

        while(G.size >= t) {
            //随机选择两个具有连边的点
            int u = 0 , v = 0;
            while(true){
                u = (int)(Math.random()*G.size);
                v = (int)(Math.random()*G.size);
                if(u!=v && G.edges[u][v]>0) break;
            }
            G = Contract(G , u , v);
        }
        return G;
    }
    //判断p中的所有点在图G中是否连通
    public boolean isConnect(MultiGraph G , Vector<Integer> p){
        int maxn = 0;
       // System.out.println("isConnect: "+p.size());
        for(int i=0 ; i<p.size() ; i++){
          //  System.out.println("debug"+p.get(i));
            maxn = Math.max(maxn , p.get(i));
        }
        boolean vis[] = new boolean[maxn+1];
        for(int i=0 ; i<=maxn ; i++) vis[i] = false;

        vis[p.get(0)] = true;
        Vector<Integer> biaoji = new Vector<Integer>();
        biaoji.add(p.get(0));
        for(int i=1 ; i<p.size() ; i++){
            for(int j=1 ; j<p.size() ; j++){
                if(vis[p.get(j)] == false) {
                    for (int t=0 ; t<biaoji.size() ; t++){
                        if(G.edges[biaoji.get(t)][p.get(j)] > 0){
                            vis[p.get(j)] = true;
                            biaoji.add(p.get(j));
                            break;
                        }
                    }
                }
            }
        }
        return biaoji.size() == p.size();
    }
    //暴力计算点少的图，方法过于暴力，有待修改
    public int bruteForce(MultiGraph G){
        int kind = 1;
        for(int i=0 ; i<G.size ; i++) kind*=2;
        int ret = 1000000000;
        for(int i=1 ; i<kind-1 ; i++){
            boolean color[] = new boolean[G.size];
            for(int j=0 ; j<G.size ; j++) color[j] = false;
            int p = i;
            Vector<Integer> v1 = new Vector<Integer>();
            Vector<Integer> v2 = new Vector<Integer>();
            int tot = 0;
            while(p>0){
                if(p%2 == 0){color[tot++] = false; }
                else {color[tot++] = true;}
                p /= 2;
            }
           // System.out.println("here");
            //判断点标记为true的点是否连通还有所有标记为false的点是否连通
            for(int j=0 ; j<G.size ; j++){
                if(color[j] == true) v1.add(j);
                else v2.add(j);
            }
            if(isConnect(G , v1) == false || isConnect(G , v2) == false) continue;
            //debug
//            for(int j=0 ; j<v1.size() ; j++){
//                System.out.print(v1.get(j)+" ");
//            }
//            System.out.println("");
            int curCnt = 0;
            for(int j=0 ; j<G.size ; j++){
                for(int k=j+1 ; k<G.size ; k++){
                    if(color[j]^color[k]) curCnt+=G.edges[j][k];
                }
            }
           // System.out.println("ret: "+curCnt);
            ret = Math.min(curCnt , ret);
        }
        return ret;
    }

    public int fastCut(MultiGraph G){
        if(G.size <= 6){
            return bruteForce(G);
        }
        else{
          //  System.out.println(G.size+" "+(int)(G.size/Math.sqrt(2)+1+1));
            MultiGraph G1 = RandomContract(G , (int)(G.size/Math.sqrt(2)+1+1));
            MultiGraph G2 = RandomContract(G , (int)(G.size/Math.sqrt(2)+1+1));
            return Math.min(fastCut(G1) , fastCut(G2));
        }
    }
    public static void main(String [] args){
        String fileName = FileSolve.generateGraph(1000 , 60);
        int [][] edges = FileSolve.readGraph(fileName);

        //计算运行时间
        long st = System.currentTimeMillis();

        int size = 0;
        for(int i=0 ; i<edges.length ; i++) size = Math.max(size , Math.max(edges[i][0]+1 , edges[i][1]+1));
        MultiGraph G = new MultiGraph(size);
        for(int i=0 ; i<edges.length ; i++)
            G.addEdge(edges[i][0] , edges[i][1]);

        MinCut minCut = new MinCut();
      //  minCut.Contract(G , 0 , 1).show();
        System.out.println(minCut.fastCut(G));

        long en = System.currentTimeMillis();
        System.out.println("running time: "+(en-st)+"ms");
    }
}
