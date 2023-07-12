public abstract class AbstractSort{
    // critical count, starttime, endtime, and elapsedtime are all
    // set to -1 in case they aren't initialized by calling sort()
    private int criticalCount = -1;
    private long startTime = -1, endTime = -1, elapsedTime = -1;

    //Abstract sorting method
    public abstract void sort(int[] inOut) throws UnsortedException;

    //initialize the criticalCount and startTime
    protected void startSort(){
        criticalCount = 0;
        startTime = System.nanoTime();
    }

    //initialize the endTime and find elapsedTime
    protected void endSort(){
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
    }
    //increments the critical count
    protected void incrementCount(){
        criticalCount++;
    }
    //returns the count of operations
    public int getCount(){
        return criticalCount;
    }
    //returns the elapsed time
    public long getTime(){
        return elapsedTime;
    }
    //Helper function that will validate that the array is sorted.
    public boolean validateSorted(int[] inout){
        for (int i = 0; i < inout.length - 1; i++){
            if (inout[i] > inout[i + 1]){
                return false;
            }
        }
        return true;
    }
}
