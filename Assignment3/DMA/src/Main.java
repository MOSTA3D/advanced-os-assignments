import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n**************************** Deadline Monotonic Assignment **************************\n");
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
            // running task (<order>, <executed time>, <priority>
            runningTasks[i][0] = 0;
            runningTasks[i][1] = 0;
            runningTasks[i][2] = tasks[i][0];
        }

        int[] taskOrder = new int[nTasks];
        for(int i = 0; i < nTasks; i++){
            taskOrder[i] = i;
        }
        int [] periods = new int[nTasks];
        for(int i = 0; i < nTasks; i++){
            periods[i] = tasks[i][0];
        }

        for(int i = 0; i < nTasks; i++){
            for(int j=i+1; j < nTasks; j++){
                if(periods[j] < periods[i]){
                    int t = periods[j];
                    periods[j] = periods[i];
                    periods[i] = t;

                    t=taskOrder[i];
                    taskOrder[i] = taskOrder[j];
                    taskOrder[j] = t;
                }
            }
        }

        int currentTime = 0;

        while(true){
            boolean once = true;
            for(int i = 0; i < nTasks; i++){
                int hp = taskOrder[i];
                if(currentTime >= (tasks[hp][0] * runningTasks[hp][0])){
                    System.out.println("task J(" + hp + ',' + runningTasks[hp][0] + ") is now running at time " + currentTime);

                    int timeToFinish = (currentTime + tasks[hp][1] - runningTasks[hp][1]);
                    int timeBreak = timeToFinish;

                    for(int j = 0; j < nTasks; j++){
                        if(j != hp){
                            int minTaskEnter = runningTasks[j][0] * tasks[j][0];
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
                    try {
                        Thread.sleep(500);
                    } catch(InterruptedException e) {
                        System.out.println("got interrupted!");
                    }
                    currentTime = timeBreak;
                }else{
                    try {
                        if(once){
                            System.out.println("No tasks running at time " + currentTime);
                            once = false;
                        }
                        int lowest = runningTasks[0][0] * tasks[0][0];
                        for(int j = 1; j < nTasks; j++){
                            if(lowest > (runningTasks[j][0] * tasks[j][0])){
                                lowest = runningTasks[j][0] * tasks[j][0];
                            }
                        }
                        currentTime = lowest;
                        Thread.sleep(500);
                    } catch(InterruptedException e) {
                        System.out.println("got interrupted!");
                    }
//                    break;
                }
            }
        }
    }
}