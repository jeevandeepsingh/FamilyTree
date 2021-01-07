package TheFamily;

import java.io.File;
import java.io.FileNotFoundException;

public class Geektrust {

    public static void main(String[] args) throws Exception {

        Service family = new Service();
        Geektrust sol = new Geektrust();
        
        //  The Input.txt is use to make the starting tree of relations
        try {
            sol.initFileToProcess(family, "TheFamily/Input.txt" , false);
            sol.initFileToProcess(family, args[0], true);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error : Input Correct Path");
        }

    }

    /**
     * Read file to process.
     *
     * family
     * filePath
     * isInputFile
     * FileNotFoundException
     */
    public void initFileToProcess(Service family, String filePath, boolean isInputFile) {
        File file = new File(filePath);
        Controller processor = new Controller();
        // System.out.println(filePath + "");
        processor.processInputFile(family, file, isInputFile);
    }


}
