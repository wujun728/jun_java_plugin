package king.quartz.job;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 可序列化的集群job
 * @author King
 *
 */
public class ClusterJob implements Serializable {
	public static int i = 0;

	private static Log log = LogFactory.getLog(ClusterJob.class);

	public void execute() throws InterruptedException {
		log.debug("begin begin begin begin begin begin begin begin ! job = "+ ++i);
		System.out.print("job = "+i+" is running ");
		for(int j = 1 ; j <= 5;j++){//运行5秒
			System.out.print( j +" ");
			Thread.sleep(1*1000);
		}
		System.out.println();
		log.debug("end end end end end end end end end end end end ! job = "+ i);
	}

}
