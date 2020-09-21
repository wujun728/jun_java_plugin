package sample;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import sample.bean.Message;
import sample.utils.DroolsUtil;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            KnowledgeBase kbase = DroolsUtil.readKnowledgeBase("rules/Sample.drl",ResourceType.DRL);
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            Message message = null;
            
            for(int i = 0;i < 5;i++){
            	message = Message.class.newInstance();
            	message.setMessage("for i = "+ i);
                message.setNum(i);
                ksession.insert(message);
                ksession.fireAllRules();
            }
            
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
