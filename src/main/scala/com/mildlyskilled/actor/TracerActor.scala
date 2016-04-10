package com.mildlyskilled.actor

import akka.actor.{ActorLogging, Actor}
import com.mildlyskilled._
import com.mildlyskilled.protocol.TracerProtocol
import com.mildlyskilled.protocol.CoordinatorProtocol

/**
 * Processes a row of pixels in the image.
 */
class TracerActor extends Actor with ActorLogging {

  def receive = {
    
    /**
     * TracePixels message informs the TracerActor which row of pixels to process.
     */
    case TracerProtocol.TracePixels(scene: Scene, width: Int, height: Int, row: Int) =>  
      //Produces a list of co-ordinates for each pixel.
      val pixels = for(x <- 0 until width) yield (x, row)
      //Processes each pixel one at a time. traceImage will determine the colour of a pixel. 
      //The colour and coordinate will then be sent to the CoordinatorActor in a Set mesage.
      for((x, y) <- pixels) {
        sender ! CoordinatorProtocol.Set(x, y, scene.traceImage(width, height, x,  y))
      }
      //After processing every pixel in a row, requests more work from the CoordinatorActor.
      sender ! CoordinatorProtocol.RequestMoreWork
  }
}


