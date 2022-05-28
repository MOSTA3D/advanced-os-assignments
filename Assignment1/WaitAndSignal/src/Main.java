import java.util.Scanner;

public class Main {
    private static int test = 1;
    private static Object testLock = new Object();
    private static int shared = getShared();

    private static int getShared(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("**************** Wait and Signal ****************\n");
        System.out.print("enter the shared variable value : ");
        int x = scanner.nextInt();
        scanner.close();
        return x;
    }
    private static boolean testAndSet(int x){
        synchronized (testLock){
            if (test == 1) {
                test = 0;
                return true;
            }
            test = 1;
            return false;
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try{
                        while(!testAndSet(test)){
                            lock.wait();
                        }
                        test = 0;
                        //accessing the critical region
                        shared++;
                        test = 1;
                        lock.notifyAll();
                    }catch(InterruptedException e){
                        System.out.println("exception from first thread");
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try{
                        while(!testAndSet(test)){
                            lock.wait();
                        }
                        // accessing the critical region
                        test=0;
                        shared += 5;
                        test = 1;
                        lock.notifyAll();
                    }catch(InterruptedException e){
                        System.out.println("exception from second thread");
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Shared variable must be with extra 6");
        for(int i = 0; i < 5; i++){
            try{
                Thread.sleep(800);
                System.out.print(".");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("\nShared variable is now : " + shared);
    }
}