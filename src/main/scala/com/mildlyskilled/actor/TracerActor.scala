package com.mildlyskilled.actor

import akka.actor.{ActorLogging, Actor}
import com.mildlyskilled._
import com.mildlyskilled.protocol.TracerProtocol
import com.mildlyskilled.protocol.CoordinatorProtocol

/**
  * Created by kwabena on 29/02/2016.
  */


class TracerActor extends Actor with ActorLogging {


  def receive = {
    case TracerProtocol.TracePixel(scene: Scene, width: Int, height: Int, row: Int) =>     
      val pixels = for(x <- 0 until width) yield (x, row)
      for((x, y) <- pixels) {
        sender ! CoordinatorProtocol.Set(x, y, scene.traceImage(width, height, x,  y))
      }
      sender ! CoordinatorProtocol.RequestMoreWork
  }
}
