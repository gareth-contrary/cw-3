package com.mildlyskilled.protocol

import com.mildlyskilled.{Scene, Color}

/**
 * Defines the kind of message a CoordinatorActor expects to receive.
 */
object CoordinatorProtocol {

  // Events
  sealed trait Event

  // Data
  sealed trait Data


  // Coordinator States
  sealed trait State

  /**
   * Mesages received by a CoordinatorActor.
   */
  sealed trait Message
  
  /**
   * Informs the CoordinatorActor to set the colour of a pixel.
   * 
   * @param x the x-coordinate on the image. Analogous to a column.
   * @param y the y-coordinate on the image. Analogous to a row.
   * @param c Color object representing the colour of the pixel.
   */
  case class Set(x: Int, y: Int, c: Color) extends Message
  
  /**
   * Informs the CoordinatorActor to start processing.
   */
  case class TraceImage() extends Message
  
  /**
   * TracerActor requests more work from the CoordinatorActor.
   */
  case class RequestMoreWork() extends Message
}
