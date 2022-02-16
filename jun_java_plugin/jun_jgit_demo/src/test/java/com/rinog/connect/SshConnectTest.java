package com.rinog.connect;

import java.io.IOException;

import org.junit.Test;

import ch.ethz.ssh2.Connection;

public class SshConnectTest {

	@Test
	public void test() {
		SshConnect sc=new SshConnect();
		Connection conn=null;
		try {
			if((conn=sc.login())!=null) {
				System.out.println(sc.exec(conn, "sh /gitroot/creatgit.sh java"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
