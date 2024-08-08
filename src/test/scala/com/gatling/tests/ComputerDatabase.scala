package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ComputerDatabase extends Simulation {

	val httpProtocol = http
		.baseUrl("https://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9,kn;q=0.8,de;q=0.7")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
	
	val scn = scenario("ComputerDatabase")
		.exec(http("request_0")
			.get("/computers"))
		.pause(2)
		.exec(http("request_1")
			.get("/computers/new"))
		.pause(10)
		.exec(http("request_2")
			.post("/computers")
			.formParam("name", "Playwright_Project")
			.formParam("introduced", "2024-07-11")
			.formParam("discontinued", "2024-07-10")
			.formParam("company", "13")
			.check(status.is(400)))
		.pause(10)
		.exec(http("request_3")
			.post("/computers")
			.formParam("name", "Playwright_Project")
			.formParam("introduced", "2024-07-11")
			.formParam("discontinued", "2024-07-12")
			.formParam("company", "13"))
		.pause(10)
		.exec(http("request_4")
			.get("/computers?f=Playwright_Project"))
	setUp(scn.inject(atOnceUsers(2))).protocols(httpProtocol)
}