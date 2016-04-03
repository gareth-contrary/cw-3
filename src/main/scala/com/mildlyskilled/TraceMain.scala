import akka.actor.{Props, ActorSystem}
import com.mildlyskilled._
import com.mildlyskilled.actor.{Listener, CoordinatorActor}
import com.mildlyskilled.protocol.CoordinatorProtocol

/**
 * Main method.
 */
object TraceMain extends App {

  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = FileReader.parse(infile)

  render(scene, outfile, Trace.Width, Trace.Height)
  
  /**
   * Renders and prints the ray-traced image.
   * 
   * @param scene the Scene file produced from input.dat
   * @param outfile the path for printing the final image.
   * @param width the width of the image.
   * @param height the height of the image.
   */
  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    val image = new Image(width, height)
    
    //Instantiate an actor system.
    val system = ActorSystem("rayTracer")
    
    //Add a listener actor which will print the file an terminate the system.
    val listener = system.actorOf(Props[Listener], "listener")

    //Defines the properties for instantiating a Coordinator actor incuding 
    //instructor arguments.
    val props = Props(classOf[CoordinatorActor], outfile, image, listener, scene)
    
    //Add a coordinator actor to the system. The coordinator delegates work to
    //TracerActors.
    val coord = system.actorOf(props, "coord")
    
    //Send a TraceImage message to the CoordinatorActor. TraceImage kickstarts 
    //the processing.
    coord ! CoordinatorProtocol.TraceImage
  }
}
