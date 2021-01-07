package TheFamily;

import static TheFamily.Constants.Commands.ADD_CHILD;
import static TheFamily.Constants.Commands.ADD_FAMILY_HEAD;
import static TheFamily.Constants.Commands.ADD_SPOUSE;
import static TheFamily.Constants.Commands.GET_RELATIONSHIP;
import static TheFamily.Constants.Message.INVALID_COMMAND;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;


public class Controller {
    /**
     * Process file.
     *
     * family      - object on which the command to be processed
     * file        - file to be processed
     * isInputFile - flag to check if file being processed is input or init
     *                    file.
     *
     */
    public void processInputFile(Service family, File file, boolean isInputFile) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String command = sc.nextLine();

                if (isInputFile) {
                    processInputCommand(family, command);
                } else {
                    processInitCommand(family, command);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!! Please check the file and the location provided!");
        }
    }

    /**
     * Process a command and return the output string
     *
     * family  - object on which the command to be processed
     * command - input command string to be processed
     *
     */
    private void processInputCommand(Service family, String command) {
        String[] commandParams = command.split(" ");
        String commandResult;
        switch (commandParams[0]) {
            case ADD_CHILD:
                commandResult = family.addchild(commandParams[1], commandParams[2], commandParams[3]);
                break;

            case GET_RELATIONSHIP:
                if(commandParams.length == 4){
                    commandResult = family.getRelationship(commandParams[1]+ " " +commandParams[2], commandParams[3]);    
                }
                else{
                    commandResult = family.getRelationship(commandParams[1], commandParams[2]);
                }
                
                break;

            default:
                commandResult = INVALID_COMMAND;
                break;
        }

        System.out.println(commandResult);
    }

    /**
     * Process command to initialize family tree.
     *
     * family
     * command
     */
    private void processInitCommand(Service family, String command) {
        // System.out.print(command);
        String[] commandParams = command.split(";");
        switch (commandParams[0]) {

            case ADD_FAMILY_HEAD:
                family.addFamilyHead(commandParams[1], commandParams[2]);
                break;

            case ADD_CHILD:
                family.addchild(commandParams[1], commandParams[2], commandParams[3]);
                break;

            case ADD_SPOUSE:
                family.addSpouse(commandParams[1], commandParams[2], commandParams[3]);
                break;

            default:
                System.out.println("INVALID INIT COMMAND!");
                break;
        }
    }
}
