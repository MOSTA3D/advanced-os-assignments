import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("enter number of the tasks required");
        Scanner scanner = new Scanner(System.in);

        int nTasks = scanner.nextInt();
        int[][] tasks = new int[nTasks][2];

        for(int i = 0; i < nTasks; i++){
            System.out.println("Enter task " + (i+1) + " parameters (d/p, e): ");
            for(int j = 0; j < 2; j++){
                tasks[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        int[][] runningTasks = new int[nTasks][3];
        for(int i = 0; i < nTasks; i++){
            // running task (<order>, <executed time>, <time remaining for deadline>
            runningTasks[i][0] = 0;
            runningTasks[i][1] = 0;
            runningTasks[i][2] = tasks[i][0];
        }

        int currentTime = 0;
        int hp;
        int temp;
        while(true){
            hp = 0;
            temp = tasks[0][0] * (runningTasks[0][0] + 1) - currentTime - tasks[0][1] + runningTasks[0][1];
            for(int i = 1; i < nTasks; i++){
                // equation = deadline - currentTime - remainingExecTime
                int temp2 = tasks[i][0] * (runningTasks[i][0] + 1) - currentTime - tasks[i][1] + runningTasks[i][1];
                if(temp > temp2){
                    temp = temp2;
                    hp = i;
                }
            }

            System.out.println("task " + hp + " is now running");

            int timeToFinish = (currentTime + tasks[hp][1] - runningTasks[hp][1]);
            int timeBreak = timeToFinish;

            for(int i = 1; i < nTasks; i++){
                int minTaskEnter = (runningTasks[i][0] + 1) * tasks[i][0];
                if(timeBreak > minTaskEnter){
                    timeBreak = minTaskEnter;
                }
            }

            if(timeBreak == timeToFinish){
                runningTasks[hp][0]++;
                runningTasks[hp][1] = 0;
            }else{
                runningTasks[hp][1] += (currentTime - timeBreak);
            }

            currentTime += timeBreak;

            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                System.out.println("got interrupted!");
            }
        }
    }
}