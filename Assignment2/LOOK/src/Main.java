import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("******************************** LOOK algorithm *******************************");
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

        System.out.print("\nEnter arm location <0, 40> : ");
        int trackLocation = scanner.nextInt();

        int[][] operations = new int[nOps][2];
        for(int i=0; i < nOps; i++){
            System.out.println("\nEnter data location (<track_number> <sector_number>):");
            operations[i][0] = scanner.nextInt();
            operations[i][1] = scanner.nextInt();
        }
        scanner.close();

        int[] order = new int[nOps];
        int[] tracks = new int[nOps];
        for(int i = 0; i < nOps; i++){
            order[i] = i;
            tracks[i] = operations[i][0];
        }
        for(int i = 0; i < nOps; i++){
            for(int j = i+1; j < nOps; j++){
                if(tracks[i] > tracks[j]){
                    int t = tracks[i];
                    tracks[i] = tracks[j];
                    tracks[j] = t;

                    t = order[i];
                    order[i] = order[j];
                    order[j] = t;
                }
            }
        }
        int pTrackNum = trackLocation, pSectorNum = 0, trackNum, sectorNum, total = 0, t1, t2, t3;
        for(int i = 0; i < nOps; i++){
            if(operations[order[i]][0] >= trackLocation){
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
        }

        for(int i = (nOps-1); i > -1; i--){
            if(operations[order[i]][0] < trackLocation){
                System.out.println("second for loop");
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
        }

        total += dt;
        System.out.println("***************************************************************************");
        System.out.println("total time consumed = " + total + " millisecond");
    }
}