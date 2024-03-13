import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class experimental {
    public static void firstExperiment(){
        System.out.println("First experiment");
        for(int i = 1; i <= 6; i++){
            int N = (int)(Math.pow(3,i+5)-1);
            double avgTime = 0;
            double avgLinks = 0;
            double avgTrees = 0;
            double avgRanksDeleted = 0;

            int precise = 30;
            int temp_precise = precise;
            while(temp_precise-- > 0){
                BinomialHeap BH = new BinomialHeap();
                long startTime = System.currentTimeMillis();
                for(int j = 1; j<=N; j++){
                    BH.insert(j,String.valueOf(j));
                }
                long endTime = System.currentTimeMillis();//-starting_point;
                avgTime+= endTime-startTime;
                avgLinks += BH.linksCount;
                avgTrees += BH.numTrees();
                avgRanksDeleted += BH.ranksDeleted;
            }
            avgTime /= precise;
            avgLinks /= precise;
            avgTrees /= precise;
            avgRanksDeleted /= precise;
            System.out.println("Time for the " + i + "'th experiment : " + String.valueOf(avgTime));
            System.out.println("Total number of links : " + String.valueOf(avgLinks));
            System.out.println("Number of trees : " + String.valueOf(avgTrees));
            System.out.println("Sum of deleteds' rank : " + String.valueOf(avgRanksDeleted));
        }
    }

    public static void secondExperiment(){
        System.out.println("Second experiment");
        for(int i = 1; i <= 6; i++){
            int N = (int)(Math.pow(3,i+5)-1);
            double avgTime = 0;
            double avgLinks = 0;
            double avgTrees = 0;
            double avgRanksDeleted = 0;

            int precise = 30;
            int temp_precise = precise;
            while(temp_precise-- > 0){
                BinomialHeap BH = new BinomialHeap();
                ArrayList<Integer> A = new ArrayList<>();
                for(int j = 1; j<=N; j++){
                    A.add(j);
                }
                Collections.shuffle(A);

                long startTime = System.currentTimeMillis();

                for(int j = 0; j<N; j++){
                    BH.insert(A.get(j),String.valueOf(A.get(j)));
                }
                for(int t = 0; t< N/2; t++){
                    BH.deleteMin();
                }

                long endTime = System.currentTimeMillis();//-starting_point;
                avgTime+= endTime-startTime;
                avgLinks += BH.linksCount;
                avgTrees += BH.numTrees();
                avgRanksDeleted += BH.ranksDeleted;
            }

            avgTime /= precise;
            avgLinks /= precise;
            avgTrees /= precise;
            avgRanksDeleted /= precise;
            System.out.println("Time for the " + i + "'th experiment : " + String.valueOf(avgTime));
            System.out.println("Total number of links : " + String.valueOf(avgLinks));
            System.out.println("Number of trees : " + String.valueOf(avgTrees));
            System.out.println("Sum of deleteds' rank : " + String.valueOf(avgRanksDeleted));
        }
    }

    public static void main(String[] args){
        secondExperiment();
        /*int A[] = {5, 4, 2, 3, 1 };
        BinomialHeap BH = new BinomialHeap();
        int N = A.length;
        for(int i = 0; i < N; i++){
            BH.insert(A[i],String.valueOf(A[i]));
        }

        for(int t = 0; t< N/2; t++){
            BH.deleteMin();
        }*/
        //System.out.println(BH.ranksDeleted);
        //HeapGraph.draw(BH);
    }
}
