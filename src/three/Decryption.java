package three;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*The main idea I've used is to store encrypted source string in the StringBuilder instance, then passing to chain
of filtering methods, which, the first, return required String value, and the second - cut off returned value from
source string, making resulting string less and less method by method.

Text file data.txt is useful for testing, you may use it if you want.
* */
public class Decryption {
    private String fileName="";
    public static void main(String[] args) {
        Decryption d=new Decryption();
        d.go();
    }

    public void go(){
        List<String>lines=consoleReader();
        List<Cypher>cyphers=new ArrayList<>();
        for(String s:lines){
            Cypher cypher = new Cypher(s);
            List<String> fields=cypher.getFields();
            fields.add(s);
            fields.add(driverCode(cypher.getEncrypted()));
            fields.add(pathList(cypher));
            fields.add(String.valueOf(cypher.isDangerous()));
            fields.add(String.valueOf(cypher.isFragile()));
            fields.add(temperature(cypher.getEncrypted()));
            fields.add(name(cypher.getEncrypted()));
            cyphers.add(cypher);
        }
        csvWriter(cyphers, fileName);
    }
//    Used to write parsed data as comma-separated-values in the file
    public void csvWriter(List<Cypher> arg, String fileName){
        try(PrintWriter pw = new PrintWriter(new File(fileName))){
            String firstLine="шифр,код водителя,код путевого листа,опасный,хрупкий,температура,наименование\n";
            StringBuilder sb = new StringBuilder(firstLine);
            for(Cypher cypher:arg){
                sb.append(cypher.getFields().get(0));
                for(int i=1; i<cypher.getFields().size(); i++) {
                    sb.append(',');
                    sb.append(cypher.getFields().get(i));
                }
                sb.append('\n');
                pw.write(sb.toString());
                sb=new StringBuilder();
            }
          } catch (FileNotFoundException e){e.printStackTrace();}
    }
//Filters Driver's Code from source string
    public String driverCode(StringBuilder arg){
        String result=arg.substring(0, 4);
        arg.delete(0, 4);
        return result;
    }
//Filters Path List Code
    public String pathList(Cypher cypher){
        StringBuilder sb = cypher.getEncrypted();
        StringBuilder result = new StringBuilder();
// Throwing away insignificant symbols which may be between Driver'sCode and PathlListCode
        while(sb.charAt(0) != 'R' && sb.charAt(0) != 'r'){
            sb.delete(0, 1);}
//Caring about 'd' or/and 'f' symbols by rising corresponding flags
        if(sb.charAt(1) == 'd'){
            cypher.setDangerous(true);
            if(sb.charAt(2) == 'f'){
                cypher.setFragile(true);
                result.append(sb.substring(0, 6));
                sb.delete(0, 6);
            }else{
                result.append(sb.substring(0, 5));
                sb.delete(0, 5);
            }
        }else {
            if (sb.charAt(1) == 'f') {
                cypher.setFragile(true);
            }
            result.append(sb.substring(0, 5));
            sb.delete(0, 5);
        }
//If the source string contains Driver'sCode twice then we have to add it to the PathListCode too
        String driverCode=sb.substring(0, 4);
        if(driverCode.equals(cypher.getFields().get(1))){
            result.append(sb.substring(0, 4));
            sb.delete(0, 4);
        }
        return  result.toString();
    }
//Filters temperature if it presents, throwing away prefix zeros (if they present) and '+' symbol
    public String temperature(StringBuilder arg){
        String result="";
        if(arg.charAt(0) == '+' || arg.charAt(0) == '-'){
            int howMuchRemain=4;
            while(arg.charAt(1) == '0'){
                arg.delete(1, 2);
                howMuchRemain--;
            }
            if(arg.charAt(0) == '+') {
                arg.delete(0, 1);
                howMuchRemain--;
            }
            result=arg.substring(0, howMuchRemain);
            arg.delete(0, howMuchRemain);

        }
        return result;
    }
//As we may have 0-2 insignificant values in the end of string, so we need to get until remainder is greater or equals
//    than 3. After cutting off of three digits, they become converted from octal to decimal format, and then they become
//    the character, at least.
    public String name(StringBuilder arg){
        StringBuilder result=new StringBuilder();
        try{
        while(arg.length() >= 3){
            char c=(char)(Integer.parseInt(arg.substring(0, 1))*64 +
                    Integer.parseInt(arg.substring(1, 2))*8 +
                    Integer.parseInt(arg.substring(2, 3)));
                    arg.delete(0, 3);
            result.append(c);
        }}catch (NumberFormatException e){
            System.out.println("One of entered strings is invalid! Please read the manual! System will exit now.");
            e.printStackTrace();
            System.exit(1);
        }
        return result.toString();
    }

//    All about console reading and verifying entered data.
    public List<String>consoleReader(){
        boolean allRight=false;
        List<String>result=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(System.in))){
            while(!allRight){
                System.out.println("Please enter the strings to process, press \"ENTER\" key after each string");
                System.out.println("Note, that strings, containing less then 12 symbols will be ignored!");
                System.out.println("When you're done with strings entering, just type 'c' or 'C' and press \"ENTER\" to continue ");
                String line=reader.readLine();
                    while(!"c".equals(line.toLowerCase())){
                        if(line.length()>11)result.add(line);
                        line=reader.readLine();
                    }
// Caring about filename output to
                while(fileName.length()==0){
                    System.out.println("Plesae enter the filename you want to save to:");
                    fileName=reader.readLine();
                }
//  If there was no exceptions then all values are read and we may return from method
                allRight=true;
            }
        }catch (IOException e){
            System.out.println("Something wrong, you may try again or press Ctrl+F2 to abort");
        }
        return result;
    }

}
