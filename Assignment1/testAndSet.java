import java.lang.Thread;



public class Main
{
    
    public int shared = 5;
    public int lock = 0;    
    
    class P1 extends Thread{
        
        int x;
        
        public P1(int x){
            this.x = x;
        }
        
        private boolean testAndSet(int lock){
            if(lock == 1) return true;
            return false;
        }
        
        public void run(){
            while(testAndSet(lock));
            Main.this.lock = 1;
            try{
                Thread.sleep(2000);
            }catch(InterruptedException err){
                System.out.println(err);
            }

            Main.this.shared = this.x;
            System.out.println("shared is " + Main.this.shared);
            Main.this.lock = 0;
        }
    }
    
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello World");
		P1 p1 = new Main().new P1(1);
		P1 p2 = new Main().new P1(2);
		
		p1.start();
		p2.start();
	}
}

