import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BenchmarkSorts {
    public static void main(String[] args){
        ArrayList<int[][]> unsortedArrays = new ArrayList<int[][]>();
        ArrayList<double[]> shellData = new ArrayList<double[]>();
        ArrayList<double[]> selectData = new ArrayList<double[]>();
        warmUp();
        ShellSort shell = new ShellSort();
        SelectionSort select = new SelectionSort();
        
        initializeArrays(unsortedArrays);
        runTest(shell, unsortedArrays, shellData);
        runTest(select, unsortedArrays, selectData);
        System.out.println(dataToString(shellData));
        System.out.println(dataToString(selectData));
        try {
            writeToFile(shellData, "shellData.csv");
            writeToFile(selectData, "selectData.csv");
        } catch (Exception e) {
            System.out.println("Error writing to files");
        }
    }
    public static int[][] generateArray(int size){
        // TODO need to generate 40 arrays of size for each item in unsortedArrays
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
    public static void initializeArrays(ArrayList<int[][]> arrays){
        for (int i = 1; i < 13; i++){
            arrays.add(generateArray(i * 100));
        }
    }
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
            // TODO: handle exception
        }
    }
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
            // TODO: handle exception
        }
    }
    static double mean(double[] arr){
        int length = arr.length;
        double sum = 0;
        for (int i = 0; i < length; i++){
            sum += arr[i];
        }
        return sum / length;
    }
    static double stdDev(double[] arr){
        int length = arr.length;
        double mean = mean(arr);
        double standardDeviation = 0;
        for (double i : arr){
            standardDeviation += Math.pow(i - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
    static double coeff(double[] arr){
        return stdDev(arr) / mean(arr);
    }
    static String dataToString(ArrayList<double[]> data){
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
    static void writeToFile(ArrayList<double[]> data, String fileName) throws IOException{
        String outputStr = dataToString(data);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(outputStr);
        writer.close();
    }
    static void warmUp(){
        /*
         * After some testing of different values of i, this seems to give better results
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
                // TODO: handle exception
            }
        }
    }
}
