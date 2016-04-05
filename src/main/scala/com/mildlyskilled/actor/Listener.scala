package com.mildlyskilled.actor

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.mildlyskilled.protocol.ListenerProtocol
import com.mildlyskilled.Trace

/**
 * Prints the image and shuts down the actor system.
 */
class Listener extends Actor with ActorLogging {
  
  //Used for measuring excecution time.
  val start = System.currentTimeMillis

  def receive = {
    
    //A Finish message informs the Listener to print the image and
    //shut down the system.
    case ListenerProtocol.Finish(image, outfile) =>
      //prints file
      image.print(outfile)
      
      //Determines execution time.
      val duration = (System.currentTimeMillis - start).toString()
      log.info("Duration: " + duration + "ms")
      
      //Prints logs to console.
      log.info("rays cast " + Trace.rayCount)
      log.info("rays hit " + Trace.hitCount)
      log.info("light " + Trace.lightCount)
      log.info("dark " + Trace.darkCount)
      
      //Shuts down system.
      context.system.terminate()
  }
}