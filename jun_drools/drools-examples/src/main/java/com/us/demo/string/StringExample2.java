package com.us.demo.string;

import java.io.UnsupportedEncodingException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class StringExample2 {
	public static void main(String[] args) {

		String rule = "package com.us.demo.string\r\n";
		rule += "import com.us.demo.string.StringExample2.Message;\r\n";
		rule += "rule \"rule1\"\r\n";
		rule += "\twhen\r\n";
		rule += "Message( status == 1, myMessage : msg )";
		rule += "\tthen\r\n";
		rule += "\t\tSystem.out.println( 1+\":\"+myMessage );\r\n";
		rule += "end\r\n";

		String rule2 = "package com.us.demo.string\r\n";
		rule += "import com.us.demo.string.StringExample2.Message;\r\n";

		rule += "rule \"rule2\"\r\n";
		rule += "\twhen\r\n";
		rule += "Message( status == 2, myMessage : msg )";
		rule += "\tthen\r\n";
		rule += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
		rule += "end\r\n";

		StatefulKnowledgeSession kSession = null;
		try {

			KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
			// 装入规则，可以装入多个
			kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")),
					ResourceType.DRL);
			kb.add(ResourceFactory.newByteArrayResource(
					rule2.getBytes("utf-8")), ResourceType.DRL);

			KnowledgeBuilderErrors errors = kb.getErrors();
			for (KnowledgeBuilderError error : errors) {
				System.out.println(error);
			}
			KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
			kBase.addKnowledgePackages(kb.getKnowledgePackages());

			kSession = kBase.newStatefulKnowledgeSession();

			Message message1 = new Message();
			message1.setStatus(1);
			message1.setMsg("hello world!");

			Message message2 = new Message();
			message2.setStatus(2);
			message2.setMsg("hi world!");

			kSession.insert(message1);
			kSession.insert(message2);
			kSession.fireAllRules();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (kSession != null)
				kSession.dispose();
		}

	}
	public static class Message {
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

		public void setType(String type) {
			this.type = type;
		}
		public String getType() {
			return this.type;
		}

	}

}
