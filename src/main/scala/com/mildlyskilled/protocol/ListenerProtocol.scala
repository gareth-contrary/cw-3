package com.mildlyskilled.protocol

import com.mildlyskilled.Image

/**
 * Defines the kind of messages which a Listener expects to receive.
 */
object ListenerProtocol {
  
  /**
   * A message for Listeners.
   */
  sealed trait Message
  
  /**
   * Tells the Listener procesing is complete.
   * 
   * @param image a finished image object,
   * @param outfile the path to print the image to.
   */
  case class Finish(image: Image, outfile: String) extends Message
}