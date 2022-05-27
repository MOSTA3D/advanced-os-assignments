import java.lang.Thread;
import java.util.Scanner;

public class Main
{
    public static int shared = 5;
    public static int lock = 0;
    class P1 extends Thread{
        int x;
        public P1(int x){
            this.x = x;
        }

        private synchronized boolean testAndSet(){
            if(lock == 1) {
                lock = 0;
                return true;
            }
            lock=1;
            return false;
        }

        public void run(){
            while(testAndSet());
            shared+=5;
            lock = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*********************** Test And Set ***********************\n");
        System.out.print("Enter shared value : ");
        shared = scanner.nextInt();
        P1 p1 = new Main().new P1(1);
        P1 p2 = new Main().new P1(2);
        System.out.println("Shared value must have extra 10");
        for(int i = 0; i < 5; i++){
            System.out.print(".");
            try{
                Thread.sleep(800);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        p1.start();
        p2.start();
        p1.join();
        p2.join();
        System.out.println("\nshared value is now " + shared);
    }
}
