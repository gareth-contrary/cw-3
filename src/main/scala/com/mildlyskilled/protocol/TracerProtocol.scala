package com.mildlyskilled.protocol

import akka.actor.ActorRef
import com.mildlyskilled.Scene

/**
  * Created by kwabena on 29/02/2016.
  */


/**
 * Mesages received by a TracerActor.
 */
object TracerProtocol {
  
  /**
   * A message which a TracerActor understands.
   */
  sealed trait Message
  
  /**
   * Informs the TracerActor which pixels to process.
   * 
   * @param scene describes the objects an light in a scene.
   * @param width of the image to be processed.
   * @param height of the image to be processed.
   * @param row to be processed. by the TracerActor.
   */
  case class TracePixels(scene: Scene, width: Int, height: Int, row: Int) extends Message
  
}
