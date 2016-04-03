package sample.akka.testkit

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.testkit.{TestKit, TestActorRef}
import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import com.mildlyskilled._
import com.mildlyskilled.actors._
import com.mildlyskilled.protocol._

class TracerActorTests extends TestKit(ActorSystem("testSystem"))
  with WordSpec
  with MustMatchers {

  val infile = "src/main/resources/input.dat"
  val scene = FileReader.parse(infile)
  val row = 0
  val image = new Image(Trace.width, Trace.height)
  
  "A simple actor" must {
    // Creation of the TestActorRef
    val actorRef = TestActorRef[TracerActor]

    "receive messages" in {
      // This call is synchronous. The actor receive() method will be called in the current thread
      actorRef ! TracerProtocol.TracePixels(scene, width, height, row)
      // With actorRef.underlyingActor, we can access the SimpleActor instance created by Akka
      actorRef.underlyingActor.lastMsg must equal(CoordinatorProtocol.RequestMoreWork)
    }
  }
}