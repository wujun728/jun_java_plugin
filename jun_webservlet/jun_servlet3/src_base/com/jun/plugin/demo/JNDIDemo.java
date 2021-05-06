package com.jun.plugin.demo;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class JNDIDemo {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("��ʽ: java Lookup <filename>");
			System.exit(-1);
		}
		String name = args[0];
		// ָ��ʹ��sun���ļ�ϵͳ
		Hashtable env = new Hashtable(11);
		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			"com.sun.jndi.fscontext.RefFSContextFactory");
		try {
			// ��ʼ��Context,��������JNDI������ʼ��
			Context ctx = new InitialContext(env);
			// ������Ҫ�Ķ���
			Object obj = ctx.lookup(name);
			// ��ӡ�ҵ��Ķ���
			System.out.println(name + "���: " + obj);
			// �ر�Context
			ctx.close();
		} catch (NamingException e) {
			System.err.println("Problem looking up " + name + ": " + e);
		}
	}
}
