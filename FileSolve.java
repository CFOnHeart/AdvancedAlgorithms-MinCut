import java.io.*;

/**
 * Created by ganjun on 2017/9/8.
 */
import java.util.*;
public class FileSolve {
    //随机生成n(0~n-1)个点的不具备重边和自环的网络图 , pro=1~100表示边的密集度，返回生成数据文件的变量名
    public static String generateGraph(int n , int pro){
        boolean edges[][] = new boolean[n][n];
        int tot = 0;
        for(int i=0 ; i<n ; i++){
            for(int j=i+1 ; j<n ; j++){
                edges[i][j] = Math.random() * 100 <= pro ? true:false;
                if(edges[i][j] == true) tot ++;
            }
        }
        String fileName = "graph"+String.valueOf(n)+String.valueOf((int)(Math.random()*100000))+".txt";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(tot+"\n");
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (edges[i][j] == true) {
                        out.write(i+ " " + j+"\n");
                    }
                }
            }
            out.close();

        }catch (IOException ex){

        }
        return fileName;
    }

    public static int[][] readGraph(String fileName){
        try{
            Scanner in = new Scanner(new File(fileName));
            int tot = in.nextInt();
            int edges[][] = new int[tot][2];
            int index = 0;
            while(in.hasNext()){
                int u = in.nextInt();
                int v = in.nextInt();
                edges[index][0] = u ; edges[index++][1] = v;
            }
            in.close();
            return edges;

        }catch (IOException ex){
            return null;
        }
    }
    public static void main(String [] args){
        String fileName = generateGraph(7, 80);
        System.out.println(fileName);
        int [][] edges = readGraph(fileName);
        for(int i=0 ; i<edges.length ; i++){
            System.out.println(edges[i][0]+" "+edges[i][1]);
        }
    }
}
