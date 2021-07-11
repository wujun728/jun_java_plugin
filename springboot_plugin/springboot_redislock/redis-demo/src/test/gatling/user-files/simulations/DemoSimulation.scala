
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DemoSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://172.16.14.163:8090")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("zh-CN,en-US;q=0.7,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val uri1 = "http://172.16.14.163:8090/api/user/list"

  val scn = scenario("DemoSimulation")
    .exec(http("request_0")
      .get("/api/user/list")
      .headers(headers_0))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}