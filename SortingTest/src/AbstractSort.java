public abstract class AbstractSort{
    private int criticalCount = -1;
    private long startTime = -1, endTime = -1, elapsedTime = -1;
    public abstract void sort(int[] inOut) throws UnsortedException;

    protected void startSort(){
        criticalCount = 0;
        startTime = System.nanoTime();
    }
    protected void endSort(){
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
    }
    protected void incrementCount(){
        criticalCount++;
    }
    public int getCount(){
        return criticalCount;
    }
    public long getTime(){
        return elapsedTime;
    }
}
