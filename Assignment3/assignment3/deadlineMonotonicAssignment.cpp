#include <iostream>
#include <chrono>
#include <thread>

using namespace std;
using namespace std::this_thread;
using namespace std::chrono;

int main()
{
    cout << "*************** Deadline Monotonic Assignment ( DMA ) ****************"<<endl<<endl;
    int nTasks;
    cout << "Enter number of tasks: ";
    cin >> nTasks;
    int tasksMtx [nTasks][4];
    for(int i = 0; i < nTasks ; i++){
        cout << "enter parameters of " << i+1 << " task:" << endl;
        for(int j = 0 ; j < 4 ; j++){
            cin>>tasksMtx[i][j];
        }
    }

    cout << "showing processing status in mss for the next 100ms : " << endl;
    int rmnArr[nTasks];

    for(int i = 0; i < nTasks ; i++){
        rmnArr[i] = tasksMtx[i][2];
    }

    for(int i = 0;i < 100; i++){
        int smallestIndex;
        int smallest;
        int tmp;
        for(int j = 0; j < nTasks; j++){
            if(tasksMtx[j][0] < i){
                tmp = tasksMtx[j][3] - i;
            }

            if(rmnArr[j] <= 0){
                break;
            }

            if(smallest > tmp){
                smallest = tmp;
                smallestIndex = j;
            }
        }

        bool x;
        for(int j = 0; j < nTasks; j++){
            if(rmnArr[j] > 0){
                break;
            }
            x = true;
        }
        if(x){
            cout << "task " << smallestIndex+1 << " is now running." << endl;
        }
        sleep_for(milliseconds(200));
    }


    cout << endl << "done";
    return 0;
}
