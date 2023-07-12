public class SelectionSort extends AbstractSort{

    @Override
    public void sort(int[] inOut) throws UnsortedException {
        startSort();
        inOut = sortSelection(inOut);
        endSort();
        if (!this.validateSorted(inOut)){
            String message = "The SelectionSort didn't properly sort the array.";
            throw new UnsortedException(message);
        }
    }
    /*
     * Selection sort implementation from https://www.baeldung.com/java-selection-sort
     */
    private int[] sortSelection(int[] inOut){
        for (int i = 0; i < inOut.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < inOut.length; j++) {
                if (inOut[minElementIndex] > inOut[j]) {
                    minElementIndex = j;
                    incrementCount();
                }
            }
    
            if (minElementIndex != i) {
                int temp = inOut[i];
                inOut[i] = inOut[minElementIndex];
                inOut[minElementIndex] = temp;
            }
            incrementCount();
        }
        return inOut;
    }
}
