//package numbers;
import java.util.*;
import java.lang.RuntimeException;

public class Main {
    public static void main(String[] args) {
//        write your code here
        Scanner in = new Scanner(System.in);
        long num=-1, num2 = -1;
        boolean seq, prop;

        System.out.println("Welcome to Amazing Numbers!");
        printInstr();

        do{
            seq = false;
            prop = false;
            ArrayList<String> inputProps = new ArrayList<String> ();
            System.out.print("\nEnter a request: ");

            String input = in.nextLine();
            Scanner strScan = new Scanner(input);

            if(input.isEmpty()){
                printInstr();
                continue;
            }

            try{
                num = Long.parseLong(input);
                if(num<0){
                    System.out.println("The first parameter should be a natural number or zero.");
                    continue;
                } else if (num==0) {
                    System.out.println("Goodbye!");
                    return;
                }
            } catch(Exception e){
                try{
                    num = strScan.nextLong();
                    if(num<0){
                        throw new Exception();
                    } else if (num==0) {
                        System.out.println("Goodbye!");
                        return;
                    }
                } catch (Exception e1){
                    System.out.println("The first parameter should be a natural number or zero.");
                    continue;
                }
                try {
                    num2 = strScan.nextLong();
                    if(num2<=0){
                        throw new Exception();
                    }
                } catch (Exception e2){
                    System.out.println("The second parameter should be a natural number.");
                    continue;
                }
                seq = true;

                while(true){
                    try{
                        input = strScan.next();
                        inputProps.add(input.toUpperCase());
                        prop = true;
                    } catch (Exception e3){
                        break;
                    }
                }
            }

            if (!seq){
                explainVerbose(num);
            } else if (seq && !prop) {
                explainSequence(num, num2);
            } else if (validProps(inputProps)){
                explainFilterSequence(num, num2, inputProps);
            }
        }while(num!=0);
    }

    public static boolean validProps(ArrayList<String> inputProps){
        List<String> props = List.of("EVEN", "ODD", "BUZZ", "DUCK",
                "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD");

        ArrayList<String> invalidProps = new ArrayList<String>();
        for (String prop : inputProps) {
            if ((prop.charAt(0)!='-' && !props.contains(prop)) ||
                    (prop.charAt(0)=='-' && !props.contains(prop.substring(1))) ) {
                invalidProps.add(prop);
            }
        }

        if(invalidProps.size()==1){
            System.out.printf("The property [%s] is wrong.%n", invalidProps.get(0));
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
            return false;
        } else if (invalidProps.size()>1) {
            String out = "The properties [" + invalidProps.get(0);
            for(int i=1; i<invalidProps.size(); i++){
                out += ", " + invalidProps.get(i);
            }
            System.out.println(out + "] are wrong.");
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
            return false;
        }

        if(inputProps.contains("EVEN") && inputProps.contains("ODD")){
            System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("-EVEN") && inputProps.contains("-ODD")){
            System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("-SPY") && inputProps.contains("-DUCK")){
            System.out.println("The request contains mutually exclusive properties: [-SPY, -DUCK]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("SPY") && inputProps.contains("DUCK")){
            System.out.println("The request contains mutually exclusive properties: [SPY, DUCK]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("-SUNNY") && inputProps.contains("-SQUARE")){
            System.out.println("The request contains mutually exclusive properties: [-SUNNY, -SQUARE]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("SUNNY") && inputProps.contains("SQUARE")){
            System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("-HAPPY") && inputProps.contains("-SAD")){
            System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]\n" +
                    "There are no numbers with these properties.");
            return false;
        } else if(inputProps.contains("HAPPY") && inputProps.contains("SAD")){
            System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                    "There are no numbers with these properties.");
            return false;
        }

        for(String prop : inputProps){
            if(inputProps.contains("-"+prop)){
                System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n" +
                        "There are no numbers with these properties.%n", prop, "-"+prop);
                return false;
            }
        }

        return true;
    }

    public static void explainSequence(long num, long n){
        for(long j = num; j<num+n; j++){
            explain(j);
        }
    }

    public static void explainFilterSequence(long num, long n, ArrayList<String> filters){
        long ctr = 0;
        long i = 0;
        boolean valid;

        while(ctr<n){
            valid = true;
            for(int j=0; j<filters.size(); j++){
                if(!isFiltered(num+i, filters.get(j))){
                    valid = false;
                }
            }
            if(valid){
                explain(num+i);
                ctr++;
            }
            i++;
        }
    }

    public static boolean isFiltered(long num, String method){
        if(method.charAt(0) == '-'){
            return !isFiltered(num, method.substring(1));
        }
        switch(method.toUpperCase()){
            case "BUZZ":{
                if(!buzz(num)){return false;}
                break;
            }
            case "DUCK":{
                if(!duck(num)){return false;}
                break;
            }
            case "PALINDROMIC":{
                if(!palindrome(num)){return false;}
                break;
            }
            case "GAPFUL":{
                if(!gapful(num)){return false;}
                break;
            }
            case "SPY":{
                if(!spy(num)){return false;}
                break;
            }
            case "SQUARE":{
                if(!square(num)){return false;}
                break;
            }
            case "SUNNY":{
                if(!sunny(num)){return false;}
                break;
            }
            case "JUMPING":{
                if(!jumping(num)){return false;}
                break;
            }
            case "HAPPY":{
                if(!happy(num)){return false;}
                break;
            }
            case "SAD":{
                if(!sad(num)){return false;}
                break;
            }
            case "EVEN":{
                if(!even(num)){return false;}
                break;
            }
            case "ODD":{
                if(!odd(num)){return false;}
                break;
            }
        }
        return true;
    }

    public static void explain(long num){
        String ans = num + " is";
        if(buzz(num)){ans+=" buzz,";}
        if(duck(num)){ans+=" duck,";}
        if(palindrome(num)){ans+= " palindromic,";}
        if(gapful(num)){ans+=" gapful,";}
        if(spy(num)){ans+=" spy,";}
        if(square(num)){ans+=" square,";}
        if(sunny(num)){ans+=" sunny,";}
        if(jumping(num)){ans+=" jumping,";}
        if(happy(num)){ans+=" happy,";}
        if(sad(num)){ans+=" sad,";}
        if(even(num)){ans+=" even";}
        if(odd(num)){ans+=" odd";}
        System.out.println(ans);
    }

    public static void printInstr(){
        System.out.println("\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }
    public static void explainVerbose(long num){
        System.out.println("Properties of "+num);
        System.out.println("buzz: "+buzz(num));
        System.out.println("duck: "+duck(num));
        System.out.println("palindromic: "+palindrome(num));
        System.out.println("gapful: "+gapful(num));
        System.out.println("spy: "+spy(num));
        System.out.println("square: "+square(num));
        System.out.println("sunny: "+sunny(num));
        System.out.println("jumping: "+jumping(num));
        System.out.println("happy: "+happy(num));
        System.out.println("sad: "+sad(num));
        System.out.println("even: "+even(num));
        System.out.println("odd: "+odd(num));
    }
    public static boolean palindrome(long num){
        boolean palindrome = true;
        String str = Long.toString(num);
        int i=0;
        int j=str.length()-1;
        while(i<j){
            if(str.charAt(i)!=str.charAt(j)){
                palindrome=false;
                break;
            }
            i++;
            j--;
        }
        return palindrome;
    }
    public static boolean even(long num){return num%2==0;}
    public static boolean odd(long num){return num%2==1;}
    public static boolean buzz(long num){
        return !(num%10!=7 && num%7!=0);
    }
    public static boolean duck(long num){
        boolean duck = false;
        String str = Long.toString(num);
        for(int i=1; i<str.length(); i++){
            if(str.charAt(i)=='0'){
                duck=true;
                break;
            }
        }
        return duck;
    }
    public static boolean gapful(long num){
        if(num<100){return false;}
        String str = Long.toString(num);
        int s = str.length();
        int div = Integer.parseInt(String.valueOf(str.charAt(0)) + String.valueOf(str.charAt(s-1)));
        return num%div==0;
    }
    public static boolean spy(long num){
        String str = Long.toString(num);
        long product = 1, sum = 0;
        for(int i=0; i<str.length(); i++){
            int dig = Integer.parseInt(String.valueOf(str.charAt(i)));
            product*=dig;
            sum+=dig;
        }
        return product==sum;
    }
    public static boolean square(long num){
        double root = Math.sqrt(num);
        return (root-Math.floor(root)) == 0;
    }
    public static boolean sunny(long num){
        return square(num+1);
    }
    public static boolean jumping(long num){
        String str = Long.toString(num);
        for(int i=1; i<str.length(); i++){
            if(Math.abs(str.charAt(i-1)-str.charAt(i)) != 1){
                return false;
            }
        }
        return true;
    }
    public static boolean happy(long num){
        HashSet<Long> set = new HashSet<Long>();
        while(!set.contains(num)){
            if(num == 1){return true;}
            set.add(num);
            num = digSum(num);
        }
        return false;
    }
    public static boolean sad(long num){
        return !happy(num);
    }
    public static long digSum(long num){
        String str = Long.toString(num);
        long ans = 0;
        for(int i=0; i<str.length(); i++){
            ans += Math.pow(str.charAt(i) - '0', 2);
        }
        return ans;
    }
}