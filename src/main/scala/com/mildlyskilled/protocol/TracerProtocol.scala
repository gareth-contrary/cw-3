package com.mildlyskilled.protocol

import akka.actor.ActorRef
import com.mildlyskilled.Scene

/**
  * Created by kwabena on 29/02/2016.
  */



object TracerProtocol {

  sealed trait Message
  case class TracePixel(scene: Scene, width: Int, height: Int, x: Int, y: Int) extends Message
  
}
