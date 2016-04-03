package com.mildlyskilled.protocol

import com.mildlyskilled.{Scene, Color}

object CoordinatorProtocol {

  // Events
  sealed trait Event

  // Data
  sealed trait Data


  // Coordinator States
  sealed trait State


  sealed trait Message
  case class Set(x: Int, y: Int, c: Color) extends Message
  case class TraceImage(scene: Scene) extends Message
 // case class GiveMeWorkBitch() extends Message
}
