package cloud.sleuth.stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
public class StreamServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StreamServerApplication.class, args);
	}
}
