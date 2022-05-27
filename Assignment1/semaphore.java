
import java.util.Random;
import java.lang.Thread;

public class Semaphore {
	private boolean locked = false;

	Semaphore(int initial) {
		locked = (initial == 0);
	}

	public synchronized void waitForNotify() throws InterruptedException {
		while (locked) {
			wait();
		}
		locked = true;
	}

	public synchronized void notifyToWakeup() {
		if (locked) {
			notify();
		}
		locked = false;
	}
}

public class Main {
	protected static final Semaphore s0 = new Semaphore(0);

	protected static final Semaphore s1 = new Semaphore(1);

	protected static final Random random = new Random();

	public static void main(String args[]) throws InterruptedException {

		Thread p1 = new Process();
		Thread p2 = new Process();

		Thread.sleep(9000);

		System.exit(0);
	}
}





public class Process extends SemaphoreABC implements Runnable {
	public void run() {
		while (true) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 800));
				s1.waitForNotify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("B");
			s0.notifyToWakeup();
		}
	}
}

