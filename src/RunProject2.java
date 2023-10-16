import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Driver of project 2
 *
 * @author Michael Muzafarov
 */
public class RunProject2 {

    private static final boolean TEST_MODE = false;
    private static final String OUTPUT_OF_THIS_RUN = "this_run_output.txt";
    private static final String SAMPLE_OUTPUT = "sample_outputs.txt";
    private static final String INPUT_OF_THIS_RUN = "sample_inputs.txt";

    /**
     * will compare program output to sample output and print differences to
     * console, used for testing only
     */
    private static void compareFiles() {
        Scanner programOutputScanner;
        Scanner expectedOutputScanner;
        try {
            programOutputScanner =
                    new Scanner(new java.io.File(OUTPUT_OF_THIS_RUN));
            expectedOutputScanner =
                    new Scanner(new java.io.File(SAMPLE_OUTPUT));
        }
        catch (FileNotFoundException e) {
            System.out.println(
                    "Couldn't open either this run or the sample outputs");
            return;
        }
        int allowedErrors = 1;
        int line = 1;
        while (programOutputScanner.hasNext() &&
               expectedOutputScanner.hasNext()) {
            String programOutput = programOutputScanner.nextLine();
            String expectedOutput = expectedOutputScanner.nextLine();

            if (!programOutput.equals(expectedOutput)) {
                System.out.printf("""
                                  program output: '%s'
                                  expected output: '%s'
                                  line %d\s
                                  %n\s
                                  """, programOutput, expectedOutput, line);
                if(allowedErrors == 0){
                    break;
                }
                else{
                    allowedErrors --;
                }
            }
            line++;
        }
        programOutputScanner.close();
        expectedOutputScanner.close();
        System.out.println("Those are all the incongruities");
    }

    /**
     * Driver method for project 2
     *
     * @param args this is unused, it does not take command line arguments
     */
    public static void main(String[] args) {

        PrintStream oldOut = System.out;
        InputStream oldIn = System.in;
        if (RunProject2.TEST_MODE) {
            try {
                System.setOut(
                        new java.io.PrintStream(
                            new java.io.FileOutputStream(
                                    OUTPUT_OF_THIS_RUN)
                            )
                );
                System.setIn(new FileInputStream(INPUT_OF_THIS_RUN));
            }
            catch (FileNotFoundException e) {
                System.out.println(
                        "Couldn't find file to write program's prints to");
            }
        }

        TransactionManager transactionManager = new TransactionManager();
        transactionManager.run();

        if (RunProject2.TEST_MODE) {
            try{
                System.out.close();
                System.in.close();
            }
            catch (java.io.IOException e){
                System.out.println("Failed to close out and in redirection files");
            }

            System.setOut(oldOut);
            System.setIn(oldIn);
            RunProject2.compareFiles();
        }


    }
}
