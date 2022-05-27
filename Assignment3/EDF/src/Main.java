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
        int hp =0 ;
        int temp=0;
        int highestDl = 0;

        while(true){
            // getting the highest deadline
            for(int i = 0; i < nTasks; i++){
                if((tasks[i][0] * (runningTasks[i][0] + 1)) > temp){
                    temp = tasks[i][0] * (runningTasks[i][0] + 1);
                }
            }
            highestDl = (++temp);

            // getting the lowest deadline for the current order tasks[0][0] * (runningTasks[0][0] + 1)
            for(int i = 0; i < nTasks; i++){
                if(currentTime >= (runningTasks[i][0] * tasks[i][0])){
                    int temp2 = tasks[i][0] * (runningTasks[i][0] + 1) ;
                    if(temp > temp2){
                        temp = temp2;
                        hp = i;
                    }
                }
            }

            if(temp == highestDl){
                System.out.println("no tasks running");
                int leastPeriod = runningTasks[0][0] * tasks[0][0];
                for(int i = 1; i < nTasks; i++){
                    if(leastPeriod > runningTasks[i][0] * tasks[i][0]){
                        leastPeriod = runningTasks[i][0] * tasks[i][0];
                    }
                }

                currentTime = leastPeriod;
            }else{
                System.out.println("task J(" + hp + ',' + runningTasks[hp][0] + ") is now running at time " + currentTime);

                int timeToFinish = (currentTime + tasks[hp][1] - runningTasks[hp][1]);
                int timeBreak = timeToFinish;

                for(int i = 0; i < nTasks; i++){
                    if(i != hp){
                        int minTaskEnter = runningTasks[i][0] * tasks[i][0];
                        if(timeBreak > minTaskEnter && minTaskEnter > currentTime){
                            timeBreak = minTaskEnter;
                        }
                    }
                }

                if(timeBreak == timeToFinish){
                    runningTasks[hp][0]++;
                    runningTasks[hp][1] = 0;
                }else{
                    runningTasks[hp][1] += (timeBreak - currentTime);
                }

                currentTime = timeBreak;
            }
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                System.out.println("got interrupted!");
            }
        }
    }
}