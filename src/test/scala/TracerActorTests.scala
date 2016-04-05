package sample.akka.testkit

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.MustMatchers
import org.scalatest.WordSpecLike
import com.mildlyskilled._
import com.mildlyskilled.actor.TracerActor
import com.mildlyskilled.actor._
import com.mildlyskilled.protocol._

import scala.concurrent.duration._

class TracerActorTests extends TestKit(ActorSystem("testSystem"))
  with WordSpecLike
  with MustMatchers
  with ImplicitSender {

  val infile = "src/main/resources/input.dat"
  val scene = FileReader.parse(infile)
  val row = 0
  val image = new Image(Trace.Width, Trace.Height)
  val color = Color.black

  "A Tracer actor" must {
    // Creation of the TestActorRef
    val actorRef = TestActorRef[TracerActor]

    "send a set message" in {
      within(10000 millis) {
        // This call is synchronous. The actor receive() method will be called in the current thread
        actorRef ! TracerProtocol.TracePixels(scene, Trace.Width, Trace.Height, row)
        // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
        expectMsg(CoordinatorProtocol.Set(0, 0, color))
      }
    }
    "send 800 set messages" in {
      actorRef ! TracerProtocol.TracePixels(scene, Trace.Width, Trace.Height, row)
      // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
      receiveN(800)
    }
  }

 /* "A Tracer actor" must {
    // Creation of the TestActorRef
    val actorRef2 = TestActorRef[CoordinatorActor]

    "send a set message" in {
      within(10000 millis) {
        // This call is synchronous. The actor receive() method will be called in the current thread
        actorRef2 ! CoordinatorProtocol.TraceImage
        // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
        expectMsg(TracerProtocol.TracePixels(scene, Trace.Width, Trace.Height, row))
      }
    }
    "send a set message" in {
      within(10000 millis) {
        // This call is synchronous. The actor receive() method will be called in the current thread
        actorRef2 ! CoordinatorProtocol.RequestMoreWork
        // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
        expectMsg(TracerProtocol.TracePixels(scene, Trace.Width, Trace.Height, 802))
      }
    }
    "send a set message" in {
      within(10000 millis) {
        // This call is synchronous. The actor receive() method will be called in the current thread
        actorRef2 ! CoordinatorProtocol.Set(0, 0, Color.black)
        // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
        expectNoMsg()
      }
    }
    /*"send 800 set messages" in {
      actorRef ! TracerProtocol.TracePixels(scene, Trace.Width, Trace.Height, row)
      // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
      expectNoMsg
    }*/
  }*/
}