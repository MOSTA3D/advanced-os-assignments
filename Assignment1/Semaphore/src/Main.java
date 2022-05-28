import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int count = 0;
    private static int allThreadsCount = 0;
    private static Object lockObject = new Object();
    private static int lock = 1;
    private static int shared;
    private synchronized static boolean testAndSet(){
        if(lock == 0){
            return false;
        }
        lock--;
        count++;
        return true;
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObject){
                    try{
                        lockObject.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    shared++;
                    lock = 1;
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObject){
                    try{
                        lockObject.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    shared+=5;
                    lock = 1;
                }
            }
        });
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n********************** Semaphore **********************\n");
        System.out.print("Enter the shared variable value: ");
        shared = scanner.nextInt();

        t1.start();
        t2.start();
        allThreadsCount = Thread.activeCount() - 2;

        while(count != allThreadsCount){
            System.out.println(count + " " + allThreadsCount);
            if(testAndSet()){
                synchronized (lockObject){
                    lockObject.notify();
                }
            }
        }
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Shared variable must must have an extra 6");
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