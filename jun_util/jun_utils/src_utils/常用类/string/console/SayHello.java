package book.string.console;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class SayHello extends BaseConsoleClient {

    private static final String SAYHELLO_COMMAND = "SayHello";
    private static final String SAYHELLO_USAGE = SAYHELLO_COMMAND;

    private static final Option NAME =
        OptionBuilder.withDescription("The name of the person. This argument is required.").hasArg()
        .withLongOpt("name")
        .create("n");    
      
    public static void main(String[] args){
        
        String name = null;
        
        SayHello client = new SayHello();
        client.addOption(NAME);

        client.setUsageMsg(SAYHELLO_USAGE);
        client.setHeader("Options:");
        
        try {
            CommandLine line = client.parse(args);
            if (line.hasOption("n")){
            	name = line.getOptionValue("n");
            } else {
                System.err.println("Error: missing -n arguments.");
                System.err.println("Try '" + SAYHELLO_COMMAND 
                		+ " -h' for more information.");
                System.exit(1);
            }
           
        } catch (Exception e){
            if (client.isDebugMode()){
                e.printStackTrace();
            } else {
                System.err.println("Error: " + e.getMessage());
            }
            System.exit(1);
        }
        System.out.println("Hello, " + name + "!");
    }
}
