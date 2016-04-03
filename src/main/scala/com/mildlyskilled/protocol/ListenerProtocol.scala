package com.mildlyskilled.protocol

import com.mildlyskilled.Image

object ListenerProtocol {


  sealed trait Message
  case class Finish(image: Image, outfile: String) extends Message
}