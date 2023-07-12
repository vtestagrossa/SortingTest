import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
/*
 * Vincent Testagrossa
 * CMSC 451
 * 12JUL2023
 */
public class BenchmarkSorts {
    public static void main(String[] args){
        //Set up the required collections
        ArrayList<int[][]> unsortedArrays = new ArrayList<int[][]>();
        ArrayList<double[]> shellData = new ArrayList<double[]>();
        ArrayList<double[]> selectData = new ArrayList<double[]>();

        //Warm up the JVM by instantiating both classes and running them a few thousand times.
        warmUp();
        //JVM is warmed up.
        ShellSort shell = new ShellSort();
        SelectionSort select = new SelectionSort();
        
        //Initialize the randomized arrays, test them, and output the data to the console and the files.
        initializeArrays(unsortedArrays);
        runTest(shell, unsortedArrays, shellData);
        runTest(select, unsortedArrays, selectData);
        System.out.println(dataToCSVString(shellData));
        System.out.println(dataToCSVString(selectData));
        try {
            writeToFile(shellData, "shellData_" + System.currentTimeMillis() + ".csv");
            writeToFile(selectData, "selectData_" + System.currentTimeMillis() +  ".csv");
        } catch (Exception e) {
            System.out.println("Error writing to files");
        }
    }
    public static int[][] generateArray(int size){
        // Generates 40 different arrays of size
        int[][] outputArray = new int[40][size];
        Random rand = new Random();
        for (int i = 0; i < 40; i++){
            for (int j = 0; j < size; j++){
                int n = rand.nextInt(101);
                outputArray[i][j] = n;
            }
        }
        return outputArray;
    }
    
    //Generates and adds the arrays to the arraylist passed to it
    public static void initializeArrays(ArrayList<int[][]> arrays){
        for (int i = 1; i < 13; i++){
            arrays.add(generateArray(i * 100));
        }
    }

    /*
     * This is the Shellsort version of the runTest() method. It runs through 40 iterations of the sort method for each of the 12
     * array sizes and then retrieves the count and time, then runs the mean and coeff methods on the values to generate the output
     * string.
     * @param   shell   a ShellSort object
     * @param   unsortedArrays  ArrayList of 2d integer array to store all of the randomized arrays to be tested
     * @param   data    ArrayList of double arrays to store the output data.
     */
    public static void runTest(ShellSort shell, ArrayList<int[][]> unsortedArrays, ArrayList<double[]> data){
        double[] counts = new double[40];
        double[] times = new double[40];
        int size = 0;
        try {
            for (int j = 0; j < 12; j++){
                for (int k = 0; k < 40; k++){
                    size = unsortedArrays.get(j)[k].length;
                    int[] temp = new int[size];
                    System.arraycopy(unsortedArrays.get(j)[k], 0, temp, 0, size);
                    shell.sort(temp);
                    counts[k] = shell.getCount();
                    times[k] = shell.getTime();
                }
                double meanCt = mean(counts),
                        coeffDevCt = coeff(counts),
                        meanTm = mean(times),
                        coeffTm = coeff(times);
                double[] dataArr = {size, meanCt, coeffDevCt, meanTm, coeffTm};
                data.add(dataArr);
            }
        } catch (UnsortedException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
    /*
     * This is the SelectionSort version of the runTest() method. It runs through 40 iterations of the sort method for each of the 12
     * array sizes and then retrieves the count and time, then runs the mean and coeff methods on the values to generate the output
     * string.
     * @param   shell   a SelectionSort object
     * @param   unsortedArrays  ArrayList of 2d integer array to store all of the randomized arrays to be tested
     * @param   data    ArrayList of double arrays to store the output data.
     */
    public static void runTest(SelectionSort select, ArrayList<int[][]> unsortedArrays, ArrayList<double[]> data){
        double[] counts = new double[40];
        double[] times = new double[40];
        int size = 0;
        try {
            for (int j = 0; j < 12; j++){
                for (int k = 0; k < 40; k++){
                    size = unsortedArrays.get(j)[k].length;
                    int[] temp = new int[size];
                    System.arraycopy(unsortedArrays.get(j)[k], 0, temp, 0, size);
                    select.sort(temp);
                    counts[k] = select.getCount();
                    times[k] = select.getTime();
                }
                double meanCt = mean(counts),
                        coeffDevCt = coeff(counts),
                        meanTm = mean(times),
                        coeffTm = coeff(times);
                double[] dataArr = {size, meanCt, coeffDevCt, meanTm, coeffTm};
                data.add(dataArr);
            }
        } catch (UnsortedException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
    //finds the mean of all the elements in a double array
    static double mean(double[] arr){
        int length = arr.length;
        double sum = 0;
        for (int i = 0; i < length; i++){
            sum += arr[i];
        }
        return sum / length;
    }
    //calculates the standard deviation for all the elements in a double array
    static double stdDev(double[] arr){
        int length = arr.length;
        double mean = mean(arr);
        double standardDeviation = 0;
        for (double i : arr){
            standardDeviation += Math.pow(i - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
    //calculates the coefficient of variance for all the elements in a double array
    static double coeff(double[] arr){
        return stdDev(arr) / mean(arr);
    }
    //Converts the data to a formatted csv string.
    static String dataToCSVString(ArrayList<double[]> data){
        String output = "Size,Avg Count,Coef Count,Avg Time,Coef Time\r\n";
        for (int i = 0; i < data.size(); i++){
            for (int j = 0; j < data.get(i).length; j++){
                String tmp = "";
                switch(j){
                    case 0:
                        tmp += (int)data.get(i)[j] + ",";
                        break;
                    case 1:
                        tmp += String.format("%.2f", data.get(i)[j]) + ",";
                        break;
                    case 2:
                        tmp += String.format("%.2f", data.get(i)[j] * 100) + "%,";
                        break;
                    case 3:
                        tmp += String.format("%.2f", data.get(i)[j]) + ",";
                        break;
                    case 4:
                        tmp += String.format("%.2f", data.get(i)[j] * 100) + "%";
                        break;
                }
                output += tmp;
            }
            output += "\r\n";
        }
        return output;
    }
    //writes the data to a csv file
    static void writeToFile(ArrayList<double[]> data, String fileName) throws IOException{
        String outputStr = dataToCSVString(data);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(outputStr);
        writer.close();
    }
    static void warmUp(){
        /*
         * After some testing of different values of i, this seems to give the best results. Warms up the JVM by constantly
         * instantiating the select and shellsort classes, then running their sort methods.
         */
        for (int i = 0; i < 2000; i++){
            try {
                int[] test = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                int[] test2 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
                SelectionSort select = new SelectionSort();
                select.sort(test);
                ShellSort shell = new ShellSort();
                shell.sort(test2);
            } catch (Exception e) {
                System.out.print(e.getStackTrace());
            }
        }
    }
}
