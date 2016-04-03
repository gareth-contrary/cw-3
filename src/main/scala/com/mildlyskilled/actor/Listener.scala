package com.mildlyskilled.actor

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.mildlyskilled.protocol.ListenerProtocol


class Listener extends Actor with ActorLogging {
  val start = System.currentTimeMillis

  def receive = {
    case ListenerProtocol.Finish(image, outfile) =>
      image.print(outfile)
      val duration = (System.currentTimeMillis - start).toString()
      log.info("Duration: " + duration + "ms")
      context.system.shutdown()
  }
}