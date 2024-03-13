public class experimental {
    public static void main(String[] args){
        System.out.println("First experiment");
        for(int i = 1; i <= 6; i++){
            int N = (int)(Math.pow(3,i+5)-1);
            double avgTime = 0;
            double avgLinks = 0;
            double avgTrees = 0;

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
            }
            avgTime /= precise;
            avgLinks /= precise;
            avgTrees /= precise;
            System.out.println("Time for the " + i + "'th experiment : " + String.valueOf(avgTime));
            System.out.println("Total number of links : " + String.valueOf(avgLinks));
            System.out.println("Number of trees : " + String.valueOf(avgTrees));
        }
    }
}
