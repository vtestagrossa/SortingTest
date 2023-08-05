/*
 * Vincent Testagrossa
 * CMSC 451
 * 12JUL2023
 */
public class ShellSort extends AbstractSort {
    //Implemented abstract sort. Calls the inherited methods startSort(), sorts the input array, then endSort().
    @Override
    public void sort(int[] inOut) throws UnsortedException {
        startSort();
        inOut = sortShell(inOut);
        endSort();
        if (!this.validateSorted(inOut)){
            String message = "The ShellSort didn't properly sort the array.";
            throw new UnsortedException(message);
        }
    }
    /*
     * Implementation from https://www.baeldung.com/java-shell-sort
     */
    private int[] sortShell(int[] inOut){
        int n = inOut.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = inOut[i];
                int j = i;
                while (j >= gap && inOut[j - gap] > key) {
                    inOut[j] = inOut[j - gap];
                    j -= gap;
                    incrementCount();
                }
                inOut[j] = key;
                incrementCount();
            }
        }
        return inOut;
    }
}