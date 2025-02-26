//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package javax.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueConstraint {
    String name() default "";

    String[] columnNames();
}
