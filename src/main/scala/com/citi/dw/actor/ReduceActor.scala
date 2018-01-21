package com.citi.dw.actor

import akka.actor.{Actor, ActorRef}
import com.citi.dw.messages.{MapData, ReduceData, WordCount}

import scala.collection.mutable

class ReduceActor(val aggregateActor: ActorRef) extends Actor {

  override def receive = {
    case msg: MapData => {
      val reduceData = reduce(msg.dataList)
      aggregateActor ! reduceData
    }
    case _ => println("ReduceActor receive wrong message.")
  }

  private[this] def reduce(dataList: List[WordCount]): ReduceData = {
    val reduceMap = mutable.HashMap[String, Int]()

    for (wc <- dataList) {
      wc match {
        case WordCount(key, count) if reduceMap.contains(key) => {
          val localSumCount = reduceMap.get(key).get + count
          reduceMap += ((key, localSumCount))
          //          println(reduceMap)
        }
        case WordCount(key, count) => {
          reduceMap += ((key, 1))
          //          println(reduceMap)
        }
      }

    }
    ReduceData(reduceMap.toMap)
  }
}
