import java.lang.Exception.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FiboCalculator {

    private static boolean Caching = false;
    private static boolean Storing = false;
    private static boolean Timer = false;
    private static boolean Repeating = false;
    private static ArrayList<Long> cache = new ArrayList<Long>();
    private static int clearAmount = 20;

    public static void main(String[] args) {
        if (processArgunents(args) < 0) {
           return;
        }
        welcome();

        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
            String line = "";
            int[] nums;
            cache.add(0L);
            cache.add(1L);

            do {
                System.out.print("> ");
                line = reader.readLine().toLowerCase();
                System.out.println("");


                nums = processCommands(line);

                if(nums[0]==-2){
                    reader.close();
                    return;
                }
                else if (nums[0]>-1){
                    long[][] results = calculateFibonacci(nums);
                    printResults(results, nums);
                }



            } while (Repeating);

            reader.close();

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static long[][] calculateFibonacci(int[] nums){

        long[][] results = new long[2][nums.length];
        long a;
        long b;
        long c;

        if(Caching){
            for (int i = 0; i < nums.length; i++) {

                long startTime = System.nanoTime();

                if (nums[i]<cache.size()){
                    results[0][i] = cache.get(nums[i]);
                }
                else{

                    for (int j = cache.size(); j < nums[i]+1; j++) {
                        a=cache.get(j-2);
                        b=cache.get(j-1);
                        c=a+b;
                        cache.add(c);
                    }
                    results[0][i]=cache.get(nums[i]);
                }

                long endTime = System.nanoTime();

                long duration = (endTime - startTime);

                results[1][i] = duration;
            }
        }
        else{
            for (int i = 0; i < nums.length; i++) {

                long startTime = System.nanoTime();

                if (nums[i]<2){
                    results[0][i] = cache.get(nums[i]);
                }
                else{

                    a=cache.get(0);
                    b=cache.get(1);

                    for (int j = 2; j < nums[i]+1; j++) {
                        c=a+b;
                        a=b;
                        b=c;
                    }

                    results[0][i] = b;
                }

                long endTime = System.nanoTime();

                long duration = (endTime - startTime);

                results[1][i] = duration;
            }

        }

        return results;

    }




    private static void help(){
        System.out.println("###############################################################");
        System.out.println("                   Fibonacci Calculator");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");
        System.out.println("  Usage:");
        System.out.println("");
        System.out.println("    java FiboCalculator -trcs");
        System.out.println("");
        System.out.println("");
        System.out.println("  Switches:");
        System.out.println("");
        System.out.println("    -c Caches numbers which got calculated before.");
        System.out.println("");
        System.out.println("    -s Stores the cached data in a local file");
        System.out.println("        When storing is enabled, chaching is automatically");
        System.out.println("         turned on. The file is stored, where the executed");
        System.out.println("         FiboCalculator.jar file is.");
        System.out.println("");
        System.out.println("    -t Measures how long it takes to get the data.");
        System.out.println("");
        System.out.println("    -r Repeating number requests infinitly in one run.");
        System.out.println("");
        System.out.println("");
        System.out.println("---------------------------------------------------------------");
        System.out.println("|    FiboCalculator v0.1 - Written by: Duszka Ákos Attila     |");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");
    }

    private static void commandHelp(){
        System.out.println("---------------------------------------------------------------");
        System.out.println("          Manual for the commands, and their usage");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("  Commands:");
        System.out.println("");
        System.out.println("");
        System.out.println("    calc   Calculates the Fibonacci number at the given");
        System.out.println("         coordinate(s).");
        System.out.println("         You can specify the location by using positive integer");
        System.out.println("         numbers. Note that, you can use multiple coordinates");
        System.out.println("         in one run!");
        System.out.println("");
        System.out.println("         Usage:");
        System.out.println("");
        System.out.println("         > calc 1 2 3 ...");
        System.out.println("");
        System.out.println("");
        System.out.println("    clear  Clears the screen");
        System.out.println("         You can specify the new lines amount by");
        System.out.println("         using positive integer numbers.");
        System.out.println("         The default number of new lines is 20.");
        System.out.println("");
        System.out.println("         Usage:");
        System.out.println("");
        System.out.println("         > clear 20");
        System.out.println("");
        System.out.println("");
        System.out.println("    help   Shows this document");
        System.out.println("");
        System.out.println("         Usage:");
        System.out.println("");
        System.out.println("         > help");
        System.out.println("");
        System.out.println("");
        System.out.println("    exit   Closes the program");
        System.out.println("");
        System.out.println("         Usage:");
        System.out.println("");
        System.out.println("         > exit");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("                             FOR DEBUGGING, USE \"cache_dump\"!");
        System.out.println("---------------------------------------------------------------");
        System.out.println("|    FiboCalculator v0.1 - Written by: Duszka Ákos Attila     |");
        System.out.println("---------------------------------------------------------------");
    }




    private static int processArgunents(String[] args){
        try{

            if (args.length < 0 || args.length > 1){
                throw new IllegalArgumentException();
            }
                if(args.length == 1) {
                    char[] switches = args[0].toCharArray();


                    if (switches[0] != '-' || switches.length<2) {
                        throw new IllegalArgumentException();
                    }

                    for (int i = 1; i < switches.length; i++) {
                        if (switches[i]=='h'){
                            help();
                            return -1;
                        }
                        else if (switches[i]=='t'){
                            if (Timer) {
                                throw new IllegalArgumentException();
                            } else {
                                Timer = true;
                            }
                        }
                        else if (switches[i]=='s'){
                            if (Storing) {
                                throw new IllegalArgumentException();
                            } else {
                                Storing = true;
                            }
                        }
                        else if (switches[i]=='c'){
                            if (Caching) {
                                throw new IllegalArgumentException();
                            } else {
                                Caching = true;
                            }
                        }
                        else if (switches[i]=='r'){
                            if (Repeating) {
                                throw new IllegalArgumentException();
                            } else {
                                Repeating = true;
                            }
                        }
                        else{
                            throw new IllegalArgumentException();
                        }
                    }
                }

                if (Storing){
                    Caching = true;
                }

        }
        catch (IllegalArgumentException e){
            System.out.println("");
            System.out.println("ERROR: Bad argument usage, please check usage below!");
            System.out.println("");
            help();
            return -1;
        }

        return 0;

    }




    private static int[] processCommands(String line){

        if (line.equals("exit")) {
            System.out.println("Good bye!");
            return new int[] {(-2)};
        }

        if (line.equals("cache_dump")) {
            System.out.println("Dumping cache...");
            System.out.println("");
            cacheDump();
            return new int[] {(-2)};
        }

        else if (line.split(" ")[0].equals("calc"))
        {
            try {

                if(line.split(" ").length < 2){
                    throw new IllegalArgumentException();
                }

                int[] nums = new int[line.split(" ").length-1];
                for(int i = 1; i < line.split(" ").length; i++) {
                    nums[i - 1] = Integer.parseInt(line.split(" ")[i]);
                    if (nums[i-1]<0){
                        throw new IllegalArgumentException();
                    }
                }
                return nums;
            }
            catch (Exception e){
                System.out.println("#ERROR: Bad parameter is used with the calc command!");
                System.out.println("");
                System.out.println("         Please check out the \"help\" command, if");
                System.out.println("         you don't know how to use the \"calc\" command!");
                return new int[] {(-1)};
            }
        }
        else if(line.equals("help")){
            commandHelp();
        }
        else if(line.equals("")){

        }
        else if(line.equals("clear")){
            for (int i = 0; i < clearAmount; i++) {
                System.out.println("");
            }
        }
        else if(line.split(" ")[0].equals("clear")) {

            try {
                if (line.split(" ").length > 2) {
                    throw new IllegalArgumentException();
                }
                int buffer = Integer.parseInt(line.split(" ")[1]);

                if(buffer<1) {
                    throw new IllegalArgumentException();
                }
                else{
                    clearAmount = buffer;
                }

                System.out.println("#NOTE: Clear amount is set to "+clearAmount+"!");
                System.out.println("");
            }
            catch (Exception e){
                System.out.println("#ERROR: Bad parameter is used with the clear command!");
                System.out.println("");
                System.out.println("         Please check out the \"help\" command, if");
                System.out.println("         you don't know how to use the \"clear\" command!");
                return new int[] {(-1)};
            }
        }
        else{

            System.out.println("#ERROR: Unknown command!");
            System.out.println("");
            System.out.println("         Please check out the \"help\" command, for");
            System.out.println("         all available commands and their usage!");
            return new int[] {(-1)};
        }

        return new int[] {(-1)};
    }




    public static void welcome(){
        System.out.println("");
        System.out.println("###############################################################");
        System.out.println("                   Fibonacci Calculator");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");
        System.out.println("Welcome!");
        System.out.println("");

        if (Timer) {
            System.out.println("#NOTE: Timer is turned on!");
            System.out.println("");
        }
        if (Storing) {
            System.out.println("#WARN: Storing is not implemented yet, but");
            System.out.println("       the Caching will be enabled!");
            System.out.println("");
        }
        if (!Storing && Caching) {
            System.out.println("#NOTE: Caching is turned on!");
            System.out.println("");
        }
        if (Repeating) {
            System.out.println("#NOTE: Repeating is turned on!");
            System.out.println("");
        }

        System.out.println("");
        System.out.println("#HINT: Type \"help\" for help!");
        System.out.println("");
        System.out.println("#HINT: Type \"-h\" when you run the program, like:");
        System.out.println("");
        System.out.println("");
        System.out.println("       java FiboCalculator -h");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }

    public static void printResults(long[][] results, int[] nums){
            System.out.println("#RESULT(S):");
            System.out.println("---------------------------------------------------------------");
            System.out.println("");
            for (int i = 0; i < results[0].length; i++) {
                System.out.println("");
                System.out.println("The "+nums[i]+". element of the Fibonacci numbers: "+results[0][i]);

                if(Timer) {
                    System.out.println("The calculation time is: "
                        + results[1][i] / 1000000 + "s "
                        + results[1][i] / 1000 + "ms "
                        + results[1][i] % 1000 + "ns.");
                }
            }

            System.out.println("---------------------------------------------------------------");
    }

    public static void cacheDump(){
        System.out.println("#CACHE_DUMP:");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");
        int i = 0;
        for(long cacheIntem : cache){
            System.out.println("#"+i+" "+cacheIntem);
            i++;
        }

        System.out.println("---------------------------------------------------------------");
    }
}

