package net.jueb.util4j.beta.tools.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * 只能监控根目录下的文件发生变化,子目录文件发生变化只会提示文件夹变化
 * @author Administrator
 */
public class FileMonitorJdkImpl {
	
	public void simpleTest(Path path) throws Exception
	{
		WatchService watchService=FileSystems.getDefault().newWatchService();  
		path.register(watchService,   
	            StandardWatchEventKinds.ENTRY_CREATE,  
	            StandardWatchEventKinds.ENTRY_DELETE,  
	            StandardWatchEventKinds.ENTRY_MODIFY);  
	    while(true)  
	    {  
	        WatchKey watchKey=watchService.take();  
            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();  
            for(WatchEvent<?> event : watchEvents){  
                //TODO 根据事件类型采取不同的操作。。。。。。。  
                System.out.println("["+event.context()+"]文件发生了["+event.kind()+"]事件");    
            }  
            watchKey.reset(); 
	    } 
	}
	
	//WatchService 是线程安全的，跟踪文件事件的服务，一般是用独立线程启动跟踪  
    public void watchRNDir(Path path) throws IOException, InterruptedException {  
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {  
            //给path路径加上文件观察服务  
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,  
                    StandardWatchEventKinds.ENTRY_MODIFY,  
                    StandardWatchEventKinds.ENTRY_DELETE);  
            // start an infinite loop  
            while (true) {  
                // retrieve and remove the next watch key  
                final WatchKey key = watchService.take();  
                // get list of pending events for the watch key  
                for (WatchEvent<?> watchEvent : key.pollEvents()) {  
                    // get the kind of event (create, modify, delete)  
                    final Kind<?> kind = watchEvent.kind();  
                    // handle OVERFLOW event  
                    if (kind == StandardWatchEventKinds.OVERFLOW) {  
                        continue;  
                    }  
                    //创建事件  
                    if(kind == StandardWatchEventKinds.ENTRY_CREATE){  
                          
                    }  
                    //修改事件  
                    if(kind == StandardWatchEventKinds.ENTRY_MODIFY){  
                          
                    }  
                    //删除事件  
                    if(kind == StandardWatchEventKinds.ENTRY_DELETE){  
                          
                    }  
                    // get the filename for the event  
                    @SuppressWarnings("unchecked")
					final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;  
                    final Path filename = watchEventPath.context();  
                    // print it out  
                    System.out.println(kind + " -> " + filename);  
  
                }  
                // reset the keyf  
                boolean valid = key.reset();  
                // exit loop if the key is not valid (if the directory was  
                // deleted, for  
                if (!valid) {  
                    break;  
                }  
            }  
        }  
    }  
    
	public static void main(String[] args) throws Exception {
		String dir="C:/Users/Administrator/git/fuckGame/fuckGame-root/fuckGame-scripts/fuckGame-script-center/target/classes";
		Path path=Paths.get(dir);
		 new FileMonitorJdkImpl().watchRNDir(path);
	}
}