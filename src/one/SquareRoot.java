package one;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SquareRoot {
    public static void main(String[] args) {
        go();
    }
    public static void go(){
        int fromWhichToExtract = consoleHelper();
        int result = rootSolver(fromWhichToExtract);
        System.out.println(result);
    }
    public static int consoleHelper(){
        int result=0;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(System.in))){
            while(result<=0){
                System.out.println("Enter the number, it should be greater than zero");
                try{
                    result=Integer.parseInt(reader.readLine());
                }catch (NumberFormatException ex){
                    System.out.println("It should be NUMBER!");
                }
            }
        }catch(IOException e){
            System.out.println("Something going wrong, please try again");
        }
        return result;
    }
    /*I've decided to use Babylonian method as it has fast convergence with any raw approximation and
    doesn't require any exponentiation, has no embedded iterations.
     It is based on idea that if x is an overestimate
     to the square root of a non-negative real number S then S/x  will be an underestimate, or vice versa,
     and so the average of these two numbers may reasonably be expected to provide a better approximation.
     The next (n+1)-th approximation solving from previous n-th with this formula:

                          Xn+1=0.5*(Xn+S/Xn)

    We can stop iteration when the difference (delta) between results of (n+1)-th and n-th approximations
    delta = |Xn+1 - Xn|
    becomes less than 1
     */
    public static int rootSolver(int arg){
        if(arg==1 || arg ==2)return 1;
// Initial approximation for result
        double result = arg/3;
// it is no sense to continue approximations since delta < 1;
        double delta = result;
        while(delta >= 1){
              double old = result;
              result = 0.5*(result +arg/result);
              delta = Math.abs(old - result);
        }
//Type conversation from double to int will waste all that after decimal point.
        return (int)result;
    }
}
