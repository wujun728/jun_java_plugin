package mybatis.mate.hotreload;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class WatchServiceTest {

    // https://www.cnblogs.com/zimug/p/11774958.html
    public static void main(String[] args) throws IOException, InterruptedException {
        // 构造监听服务
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path targetPath = Paths.get(
                "D:\\IdeaProjects\\mybatis-mate-examples\\mybatis-mate-hot-reload\\src\\main\\resources"
        ).toAbsolutePath();
        Files.walkFileTree(targetPath, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
                //监听注册，监听实体的创建、修改、删除事件，并以高频率(每隔2秒一次，默认是10秒)监听
                path.register(watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE},
                        SensitivityWatchEventModifier.HIGH);
                return FileVisitResult.CONTINUE;
            }
        });
        WatchKey watchKey;
        do {
            //获取一个watch key
            watchKey = watchService.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
                WatchEvent.Kind<Path> kind = watchEvent.kind();
                Path watchable = ((Path) watchKey.watchable()).resolve(watchEvent.context());

                // 在监听到文件夹创建的时候要把这个 path 注册到 watchService 上
                if (Files.isDirectory(watchable)) {
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("新建目录 = " + watchable);
                        watchable.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                    }

                } else {
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("新建文件 = " + watchable);

                    } else if (StandardWatchEventKinds.ENTRY_MODIFY == event.kind()) {
                        System.out.println("修改文件 = " + watchable);

                    } else if (StandardWatchEventKinds.ENTRY_DELETE == event.kind()) {
                        System.out.println("删除文件 = " + watchable);
                    }
                }
            }
            System.out.println("目录内容发生改变");
        } while (watchKey.reset());
        System.out.println("目录内容sssss发生改变");
    }
}
