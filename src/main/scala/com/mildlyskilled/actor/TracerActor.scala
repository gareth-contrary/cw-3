package com.mildlyskilled.actor

import akka.actor.{ActorLogging, Actor}
import com.mildlyskilled._
import com.mildlyskilled.protocol.TracerProtocol
import com.mildlyskilled.protocol.CoordinatorProtocol

/**
  * Created by kwabena on 29/02/2016.
  */


class TracerActor extends Actor with ActorLogging {  
  //log.info(self.toString() + " is keeping it real")
  
  
  def receive = {
    case TracerProtocol.TracePixel(scene: Scene, width: Int, height: Int, x: Int, y: Int) =>      
        val colour = scene.traceImage(width, height, x,  y)
        //log.info(x.toString() + " " + y.toString())
        sender ! CoordinatorProtocol.Set(x, y, colour)
    //case _ =>
      //log.info("he's dead jim")
      //sender ! CoordinatorProtocol.GiveMeWorkBitch
    }


}
