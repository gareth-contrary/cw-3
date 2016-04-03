import akka.actor.{Props, ActorSystem}
import com.mildlyskilled._
import com.mildlyskilled.actor.{Listener, CoordinatorActor}
import com.mildlyskilled.protocol.CoordinatorProtocol

object TraceMain extends App {

  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = FileReader.parse(infile)

  render(scene, outfile, Trace.Width, Trace.Height)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    val image = new Image(width, height)
    val system = ActorSystem("rayTracer")
    val listener = system.actorOf(Props[Listener], "listener")
    Thread.sleep(50)
    val props = Props(classOf[CoordinatorActor], outfile, image, listener)
    val coord = system.actorOf(props, "coord")
    
    coord ! CoordinatorProtocol.TraceImage(scene)

    
    

  }
}
