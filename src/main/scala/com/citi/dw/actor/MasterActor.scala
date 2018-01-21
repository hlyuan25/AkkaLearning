package com.citi.dw.actor

import akka.actor.{Actor, Props}
import com.citi.dw.messages.Result

class MasterActor extends Actor {

  private val aggregateActor = context.actorOf(Props(classOf[AggregateActor]), "aggregateActor")
  private val reduceActor = context.actorOf(Props(classOf[ReduceActor], aggregateActor), "reduceActor")
  private val mapActor = context.actorOf(Props(classOf[MapActor], reduceActor), "mapActor")

  override def receive = {
    case msg: String => {
      mapActor ! msg
    }

    case msg: Result => {
      aggregateActor.forward(msg)
    }

    case _ => println("MasterActor receive wrong message.")
  }
}
