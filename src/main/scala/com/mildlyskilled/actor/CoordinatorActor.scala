package com.mildlyskilled.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.mildlyskilled.{Image, Color, Scene}
import com.mildlyskilled.protocol.{CoordinatorProtocol, ListenerProtocol, TracerProtocol}
import akka.routing.{Router, RoundRobinRoutingLogic, ActorRefRoutee}

/**
 * Delegates work to TracerActors, monitors progress and inform the Listener when complete.
 * 
 * @param outputFile the path for printing the final image.
 * @param image the Image file.
 * @param listener reference to contact the listener.
 * @param scene describes the image to be ray-traced.
 */
class CoordinatorActor(outputFile: String, image: Image, listener: ActorRef, scene: Scene) 
  extends Actor with ActorLogging {
  
  //waiting monitors the number of pixels which need to be processed. The starting value is the
  //total.
  private var waiting = image.width * image.height
  
  //Monitors which row was last dispatched for processing. Initially set to -1 because the first
  //row should be 0 and each call to this variable hould increment it by 1.
  private var currentRow = -1
  
  //The number of TracerActors that the Coordinator will create.
  val nrOfWorkers = 150
  log.info("Number of TracerActors: " + nrOfWorkers.toString())

  //Creates a router to manage the TracerActors.
  var router = {
    val routees = Vector.fill(nrOfWorkers) {
      val r = context.actorOf(Props[TracerActor])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees) 
  }

  def receive = {
    case CoordinatorProtocol.TraceImage =>
      for (i <- 0 until nrOfWorkers) {
        var row = nextRow()
        router.route(TracerProtocol.TracePixel(scene, image.width, image.height, row), self)
      }
    case CoordinatorProtocol.Set(x: Int, y: Int, c: Color) =>
      image(x, y) = c
      waiting -= 1
    case CoordinatorProtocol.RequestMoreWork =>
      var row = nextRow()
      if (currentRow < image.height) {
         router.route(TracerProtocol.TracePixel(scene, image.width, image.height, row), self)
      } else if (waiting == 0) {
        listener ! ListenerProtocol.Finish(image, outputFile)
      }
  }

  private def nextRow(): Int = {
    //if (currentYAxis != image.height) {
      currentRow += 1
    //}
    currentRow
  }

}
