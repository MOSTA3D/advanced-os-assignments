import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("******************************** first come first served *******************************");
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

        scanner.close();

        int[][] operations = new int[nOps][2];
        for(int i=0; i < nOps; i++){
            System.out.println("\nEnter data location (<track_number> <sector_number>):");
            operations[i][0] = scanner.nextInt();
            operations[i][1] = scanner.nextInt();
        }

        int pTrackNum = 0, pSectorNum = 0, trackNum, sectorNum, total = 0, t1, t2, t3;
        for(int i=0; i < nOps; i++){
            trackNum = operations[i][0];
            sectorNum = operations[i][1];
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
            if(i ==0 ){
                t2++;
            }

            t3 = t1 * trackAccessTime + t2 * sectorAccessTime;

            System.out.println("running operation " + (i+1) + " at " + "track " + operations[i][0] + ", sector " + operations[i][1]);
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