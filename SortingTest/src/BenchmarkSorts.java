public class BenchmarkSorts {
    public static void main(String[] args){
        int[] testArray = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        ShellSort shell = new ShellSort();
        SelectionSort select = new SelectionSort();
        try {
            for(int i = 0; i < 10000; i++){
                testArray = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                shell.sort(testArray);
                select.sort(testArray);
            }
        } catch (Exception e) {
            System.out.println("Unsorted");
        }
        String out = "Shell count: " + shell.getCount() +
                    "\nTime: " + shell.getTime();
        out += "\nSelect count: " + select.getCount() +
                    "\nTime: " + select.getTime();
        System.out.println(out);
        for (int i = 0; i < testArray.length; i++){
            System.out.println(testArray[i]);
        }
    }
}
