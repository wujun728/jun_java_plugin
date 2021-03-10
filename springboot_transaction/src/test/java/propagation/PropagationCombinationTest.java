package propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这个例子用来测试transaction propagation
 * 相关文档：
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#tx-propagation
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html
 * Created by qianjia on 2017/1/22.
 */
@SpringBootTest
@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class PropagationCombinationTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private FooService fooService;

  @Autowired
  private BarService barService;

  @Autowired
  private List<FooTxExecutor> fooTxExecutors = new ArrayList<>();

  @Autowired
  private List<BarTxExecutor> barTxExecutors = new ArrayList<>();

  private List<TxCombinationResult> txCombinationResults = new ArrayList<>();

  @AfterMethod
  public void clearData() {
    fooService.deleteAll();
    barService.deleteAll();
  }

  @AfterClass
  public void printResult() {

    System.out.println("---------------------------------------------------------");
    System.out.println("Foo(P)\tFoo(T)\tBar(P)\tBar(T)\tFoo(R)\tBar(R)\tException");
    System.out.println("---------------------------------------------------------");
    for (TxCombinationResult txCombinationResult : txCombinationResults) {
      System.out.println(txCombinationResult.toString());
    }

  }

  @DataProvider
  public Object[][] combinations() {

    String[] names = new String[] { "Alice", "Bob", "Mike" };
    List<String> argNames = Arrays.asList(names);

    List<TxCombination> txCombinationList = new ArrayList<>();
    for (Propagation fooMode : Propagation.values()) {

      for (Propagation barMode : Propagation.values()) {

        txCombinationList.add(new TxCombination(fooMode, false, barMode, false, argNames));
        txCombinationList.add(new TxCombination(fooMode, true, barMode, false, argNames));
        txCombinationList.add(new TxCombination(fooMode, false, barMode, true, argNames));
        txCombinationList.add(new TxCombination(fooMode, true, barMode, true, argNames));

      }

    }

    Object[][] params = new Object[txCombinationList.size()][];
    for (int i = 0; i < txCombinationList.size(); i++) {
      params[i] = new Object[] { txCombinationList.get(i) };
    }

    return params;

  }

  /**
   * 测试{@link Propagation#REQUIRED}的各种组合
   */
  @Test(dataProvider = "combinations")
  public void testCombination(TxCombination txCombination) {

    FooTxExecutor fooTxExecutor = getFooTxExecutor(txCombination.getFooMode());
    BarTxExecutor barTxExecutor = getBarTxExecutor(txCombination.getBarMode());

    Exception exception = null;
    try {

      fooTxExecutor.insert(
          barTxExecutor,
          txCombination.isFooThrowException(),
          txCombination.isBarThrowException(),
          txCombination.getNames().toArray(new String[0])
      );

    } catch (Exception e) {
      exception = e;
    }

    analyzeResult(txCombination, exception);

  }

  private FooTxExecutor getFooTxExecutor(Propagation propagation) {
    for (FooTxExecutor fooTxExecutor : fooTxExecutors) {
      if (fooTxExecutor.getPropagationMode().equals(propagation)) {
        return fooTxExecutor;
      }
    }
    return null;
  }

  private BarTxExecutor getBarTxExecutor(Propagation propagation) {
    for (BarTxExecutor barTxExecutor : barTxExecutors) {
      if (barTxExecutor.getPropagationMode().equals(propagation)) {
        return barTxExecutor;
      }
    }
    return null;
  }

  private void analyzeResult(TxCombination txCombination, Exception caughtException) {

    List<String> argNames = txCombination.getNames();

    Propagation fooMode = txCombination.getFooMode();
    Propagation barMode = txCombination.getBarMode();
    boolean fooThrowException = txCombination.isFooThrowException();
    boolean barThrowException = txCombination.isBarThrowException();

    List<String> fooActualNames = fooService.getAll();
    boolean fooSuccess = argNames.containsAll(fooActualNames) && fooActualNames.containsAll(argNames);

    List<String> barActualNames = barService.getAll();
    boolean barSuccess = argNames.containsAll(barActualNames) && barActualNames.containsAll(argNames);


    txCombinationResults.add(
        new TxCombinationResult(fooMode, fooThrowException, barMode, barThrowException, fooSuccess, barSuccess, caughtException)
    );

  }


  public static class TxCombination {

    private Propagation fooMode;

    private boolean fooThrowException;

    private Propagation barMode;

    private boolean barThrowException;

    private List<String> names;

    public TxCombination(Propagation fooMode, boolean fooThrowException,
        Propagation barMode, boolean barThrowException, List<String> names) {
      this.fooMode = fooMode;
      this.fooThrowException = fooThrowException;
      this.barMode = barMode;
      this.barThrowException = barThrowException;
      this.names = names;
    }

    public Propagation getFooMode() {
      return fooMode;
    }

    public Propagation getBarMode() {
      return barMode;
    }

    public List<String> getNames() {
      return names;
    }

    public boolean isFooThrowException() {
      return fooThrowException;
    }

    public boolean isBarThrowException() {
      return barThrowException;
    }

  }

  private static class TxCombinationResult {

    private Propagation fooMode;

    private boolean fooThrowException;

    private Propagation barMode;

    private boolean barThrowException;

    private boolean fooSuccess;

    private boolean barSuccess;

    private Exception caughtException;

    public TxCombinationResult(Propagation fooMode, boolean fooThrowException,
        Propagation barMode, boolean barThrowException, boolean fooSuccess, boolean barSuccess,
        Exception caughtException) {
      this.fooMode = fooMode;
      this.fooThrowException = fooThrowException;
      this.barMode = barMode;
      this.barThrowException = barThrowException;
      this.fooSuccess = fooSuccess;
      this.barSuccess = barSuccess;
      this.caughtException = caughtException;
    }

    @Override
    public String toString() {

      String exception =
          caughtException != null && !caughtException.getClass().equals(FakeException.class) ?
              caughtException.getClass().getName() + ": " + caughtException.getMessage()
              : "";

      final StringBuilder sb = new StringBuilder();
      sb
          .append(translate(fooMode))
          .append('\t')
          .append(translate(fooThrowException))
          .append('\t')
          .append(translate(barMode))
          .append('\t')
          .append(translate(barThrowException))
          .append('\t')
          .append(translate(fooSuccess))
          .append('\t')
          .append(translate(barSuccess))
          .append('\t')
          .append(exception);

      return sb.toString();

    }

    private String translate(boolean bool) {
      return bool ? "Y" : "N";
    }

    private String translate(Propagation propagation) {
      return propagation.toString();
    }

  }
}
