package com.citi.dw.actor

import akka.actor.{Actor, ActorRef}
import com.citi.dw.messages.{MapData, WordCount}

import scala.collection.mutable.ListBuffer

class MapActor(val reduceActor: ActorRef) extends Actor{

  private [this] def evaluateExpression(line: String) = {
    val dataList = ListBuffer[WordCount]()
    line.split(" ").map(word => dataList += WordCount(word, 1))
    MapData(dataList.toList)
  }

  override def receive = {
    case msg: String =>{
      val mapData = evaluateExpression(msg)
      reduceActor ! mapData
    }
    case _ => println("MapActor receive wrong message.")
  }
}
