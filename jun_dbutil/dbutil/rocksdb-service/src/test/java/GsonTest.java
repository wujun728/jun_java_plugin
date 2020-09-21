import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by moyong on 17/5/18.
 */
public class GsonTest {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GsonTest.class);

    public static void main(String[] args) {
        People p = new People();
        p.setAge(20);
        p.setName("People");
        p.setSetName(true);
        Gson gson = new Gson();
        String json = gson.toJson(p);
        log.debug(json);
        People abc= gson.fromJson(json,People.class);
        log.debug(":::{1}",abc.getAge());

        try (Writer writer = new FileWriter("Output.json")) {
            Gson gson1 = new GsonBuilder().create();
            gson1.toJson("Hello", writer);
            gson1.toJson(123, writer);
            System.out.println(gson1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}