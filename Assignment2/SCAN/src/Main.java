import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("******************************** SCAN algorithm *******************************");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of operations: ");
        int nOps = scanner.nextInt();

        int sectorAccessTime, trackAccessTime;
        System.out.print("\nEnter Track access time in milliseconds: ");
        trackAccessTime = scanner.nextInt();
        System.out.print("\nEnter Sector access time in milliseconds: ");
        sectorAccessTime = scanner.nextInt();

        System.out.print("\nEnter sectors/track: ");
        int secsPerTrck = scanner.nextInt();

        System.out.print("\nEnter data transfer time (millisecond): ");
        int dt = scanner.nextInt();

        int[][] operations = new int[nOps][2];
        for(int i=0; i < nOps; i++){
            System.out.println("\nEnter data location (<track_number> <sector_number>):");
            operations[i][0] = scanner.nextInt();
            operations[i][1] = scanner.nextInt();
        }

        int[] order = new int[nOps];
        int[] sts = new int[nOps];
        int ps = 0;
        for(int i = 0; i < nOps; i++){
            order[i] = i;

            int cst = operations[i][0];
            int t = cst - ps;
            if(t < 0){
                t*=-1;
            }
            sts[i] = t;
            ps = cst;
        }
        for(int i = 0; i < nOps; i++){
            for(int j = i+1; j < nOps; j++){
                if(sts[i] > sts[j]){
                    int t = sts[i];
                    sts[i] = sts[j];
                    sts[j] = t;

                    t = order[i];
                    order[i] = order[j];
                    order[j] = t;
                }
            }
        }

        scanner.close();
        int pTrackNum = 0, pSectorNum = 0, trackNum, sectorNum, total = 0, t1, t2, t3;
        for(int i=0; i < nOps; i++){
            trackNum = operations[order[i]][0];
            sectorNum = operations[order[i]][1];
            t1 = trackNum - pTrackNum;
            if(t1 < 0){
                t1 *= -1;
            }

            if(sectorNum < pSectorNum){
                t2 = ((pSectorNum+1) % secsPerTrck) + sectorNum;
            }else{
                t2 = sectorNum - pSectorNum;
            }
            t2--;
            if(order[i] ==0 ){
                t2++;
            }

            t3 = t1 * trackAccessTime + t2 * sectorAccessTime;

            System.out.println("running operation " + (order[i]+1) + " at " + "track " + operations[order[i]][0] + ", sector " + operations[order[i]][1]);
            System.out.println("takes track: " + t3 + " milli-second");
            pTrackNum = trackNum;
            pSectorNum = sectorNum;
            total += t3;

            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("got interrupted.");
            }
        }
        total += dt;
        System.out.println("***************************************************************************");
        System.out.println("total time consumed = " + total + " millisecond");
    }
}