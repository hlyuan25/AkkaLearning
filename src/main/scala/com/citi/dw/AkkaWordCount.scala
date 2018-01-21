package com.citi.dw

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.citi.dw.actor.MasterActor
import com.citi.dw.messages.Result
import akka.pattern.ask

import scala.concurrent.Await
import scala.concurrent.duration._

object AkkaWordCount extends App{

  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("WordCountAkka")

  val master = system.actorOf(Props(classOf[MasterActor]), "master")

  master ! "Hi! Hi!"
  master ! ("My name is Sky. I am so so so happy to be here ")
  master ! ("Today, I am going to introduce word count for Akka ")
  master ! ("I hope hope It is helpful to you ")
  master ! ("Thank you ")

  Thread.sleep(1000)

  val future = master ? Result()

  val result = Await.result(future, timeout.duration).asInstanceOf[Map[String, Int]]
  result.map(m => println(m._1, m._2))

  system.shutdown()

}
