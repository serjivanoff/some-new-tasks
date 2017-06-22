package two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AboutSorting {

    public static void main(String[] args) {
        AboutSorting as=new AboutSorting();
        as.go();
    }

    public void go(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int[] array = arrayReader(reader);
            int k = indexReader(reader, array.length);
            System.out.println("And the result is: " + partialSorter(array, k));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int indexReader(BufferedReader reader,int arrayLength)throws IOException{
        int result=0;
        System.out.println("Now enter the index of an element to find in sorted result, please.");
        System.out.println("Please note, it should be not greater than an entered result's length, which is "+ arrayLength);
        while(result <=0 || result > arrayLength){
            try {
                result = Integer.parseInt(reader.readLine());
            }catch(NumberFormatException e){
                System.out.println("It should be a NUMBER. Try again, please, or press Ctrl+F2 if you want to stop");
            }
        }
        return result;
    }

    public  int[] arrayReader(BufferedReader reader)throws IOException{
        int[]result = null;
        boolean arrayIsOk=false;
//Loop for reading result from console. Values are reading then parsing.
        while(!arrayIsOk) {
            System.out.println("Enter an array of random integer values separated by space symbols:");
            String[] lines = reader.readLine().split(" ");
            result = new int[lines.length];
//try if one of  entered values impossible to parse to integer value
             try{
                for (int i=0; i < lines.length; i++) {
                    result[i] = Integer.parseInt(lines[i]);
                }
//if there's no exceptions so everything is OK and we may exit the loop
                 arrayIsOk = true;
             }
             catch(NumberFormatException exc){
                System.out.println("All entered values should be a NUMBER! You may try again");
             }
        }
        return  result;
   }

//Changes places of two values with indexes "from" and "to" inside an array
   public void swap(int[] array, int from, int to){
        int a=array[from];
        array[from]=array[to];
        array[to]=a;
   }
/*This is wide-known "selection sort" method's implementation, but sorting only first k elements
 to minimize processor's time utilization
*/
   public int partialSorter(int[] array, int k){
    int length= array.length;
        for(int i=0; i < k; i++){
// On i-th iteration : finding the least element in an array from i-th position to the end,
// then swapping elements between minimum's index and i
            int min= array[i], index=i;
            for(int j=i+1; j < length; j++){
                if(min > array[j]) {
                    min=array[j];
                    index=j;
                }
            }
            if(index!=i)swap(array, i, index);
        }
//k begins with 1, but array indexes begin with 0
    return array[k -1];
   }
}