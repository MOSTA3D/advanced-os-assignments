class Algorithms{
	public void sstf(int[] q, int head, int n){
		if (n <= 0){
			return;
		}
		double seekTime = 0.0;
		double minimum = 0.0;	
		int[] skeek = new int[n + 1];	
		int[][] auxiliary = new int[n][2];
		for (int i = 0; i < n; ++i){
			auxiliary[i][0] = 0;
			auxiliary[i][1] = 0;
		}
		int i = 0;
		int j = 0;
		int location = 0;
		for (i = 0; i < n; i++){
			skeek[i] = head;
			for (j = 0; j < n; ++j){
				auxiliary[j][0] = queue[j] - head;
				if (auxiliary[j][0] < 0){
					auxiliary[j][0] = -auxiliary[j][0];
				}
			}
			minimum = Integer.MAX_VALUE;
			location = -1;
			for (j = 0; j < n; ++j){
				if (auxiliary[j][1] == 0 && auxiliary[j][0] <= minimum){
					location = j;
					minimum = auxiliary[j][0];
				}
			}
			auxiliary[location][1] = 1;
			head = queue[location];
			seekTime += auxiliary[location][0];
		}
		if (head != 0){
			skeek[n] = head;
		}
		System.out.print("\n Seek Sequence : ");
		for (i = 0; i <= n; i++){
			System.out.print(" " + skeek[i] + "");
		}
		System.out.println("Total Seek Time : " + seekTime);
		System.out.println("Average Seek Time : " + seekTime / n + "\n");
	}

	public void fcfs(int[] q, int head, int n){	
		double seekTime = 0.0;
		int distance = 0;
		int i = 0;
		System.out.println("Starting Head : " + head);
		System.out.print("the sequence is : ");
		for (i = 0; i < n; i++){
			System.out.print(" " + q[i] + "");
		}
		for (i = 0; i < n; i++){
			distance = q[i] - head;
			if (distance < 0){
				distance = -distance;
			}
			head = q[i];
			seekTime += distance;
		}
		System.out.println("\nTotal Time : " + seekTime);
		System.out.println("Average Seek Time : " + seekTime/n);
	}

	public static void main(String args[]){
		System.out.println("Enter disk sequence length");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextint();
		int[] sequence = new int[n];
		System.out.println("Enter sequence");
		for(int i = 0;i < n;i++){
			sequence[i] = scanner.nextint();
		}
		System.out.println("enter the algorithm you want to use.\n1) fcfs\n2) sstf\n");
		int choice = scanner.nextint();
		Algorithms instance = new Algorithms();
		switch(choice){
			case 1:
				instance.fcfs(sequence, 25, n);
				break;
			case 2:
				instance.sstf(sequence, 25, n);
				break;
			case 3:
				System.out.println("please enter 1 or 2 only");
		}
	}
}
