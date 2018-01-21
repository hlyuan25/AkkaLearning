package com.citi.dw.actor

import akka.actor.Actor
import com.citi.dw.messages.{ReduceData, Result}

import scala.collection.mutable

class AggregateActor extends Actor {
  private[this] var finalReduceMap = mutable.HashMap[String, Int]()

  override def receive = {
    case msg: ReduceData => {
      aggregateAndReduce(msg.reduceDataList)
    }

    case msg: Result => {
      sender() ! finalReduceMap.toMap
    }

    case _ => println("AggregateActor receive wrong message.")

  }

  private[this] def aggregateAndReduce(reduceList: Map[String, Int]): Unit = {
    for (key <- reduceList.keys) {
      if (finalReduceMap.contains(key)) {

        val count = finalReduceMap.get(key).get + reduceList.get(key).get
        finalReduceMap += ((key, count))
      } else {
        finalReduceMap += ((key, reduceList.get(key).get))
      }
    }
  }
}
