package com.us.demo.string;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class StringExample {

    private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    private static Collection<KnowledgePackage> pkgs;
    private static KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    private static StatefulKnowledgeSession ksession;

    public static void main(final String[] args) {

        initDrools();
        initMessageObject();
        fireRules();

    }

    private static void initDrools(){

        // this will parse and compile in one step
        // read from file
        // kbuilder.add( ResourceFactory.newClassPathResource( "/hellodrools/testrules.drl",StringExample.class),ResourceType.DRL );

        // read second rule from String
        String myRule = "package com.us.demo.string import com.us.demo.string.StringExample.Message ; rule \"Hello World 2\" when message:Message (type==\"Test\") then System.out.println(\"Test, Drools!\"); end";
        Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(myRule));
        kbuilder.add(myResource, ResourceType.DRL);

        // Check the builder for errors
        if ( kbuilder.hasErrors() ) {
            System.out.println( kbuilder.getErrors().toString() );
            throw new RuntimeException( "Unable to compile drl\"." );
        }

        // get the compiled packages (which are serializable)
        pkgs = kbuilder.getKnowledgePackages();

        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addKnowledgePackages( pkgs );

        ksession = kbase.newStatefulKnowledgeSession(); }

    private static void fireRules(){
        ksession.fireAllRules();
        ksession.dispose();
    }

    private static void initMessageObject() {
        Message msg = new Message();
        msg.setType("Test");
        ksession.insert(msg);
    }
    public static class Message{    		  
    	    public static final int HELLO = 0;  
    	    public static final int GOODBYE = 1;  
    	    @org.kie.api.definition.type.Label("消息")  
    	    private String msg = "test";  
    	    private int status;  
    	    private String type;
    	  
    	    public Message() {  
    	        super();  
    	    }  
    	  
    	    public String getMsg() {  
    	        return this.msg;  
    	    }  
    	  
    	    public void setMsg(String message) {  
    	        this.msg = message;  
    	    }  
    	  
    	    public int getStatus() {  
    	        return this.status;  
    	    }  
    	  
    	    public void setStatus(int status) {  
    	        this.status = status;  
    	    }  
    	    
    	    public void setType(String type){
    	    	this.type=type;
    	    }
    	    public String getType(){
    	    	return this.type;
    	    }
    	      
    }
}
