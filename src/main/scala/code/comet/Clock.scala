package code.comet

import java.util.Date

import code.lib.DependencyFactory
import net.liftweb.actor._
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Helpers._
import net.liftweb.util.Schedule
import net.liftweb.common.{Box, Full, Logger}
import net.liftweb.http._
import scala.language.postfixOps
import scala.xml._

case object Tick
case object TickWithRed

object ClockMaster extends LiftActor with ListenerManager {
  override def createUpdate = Nil
  override def lowPriority = {
    case Nil =>
    /*    case a:Option[Throwable] => updateListeners(a)
        case a:XMLGenerateProcessStatus => updateListeners(a)*/
    case TickWithRed => sendListenersMessage(TickWithRed)
    case other =>  println("unknownMessage: %s".format(other))
  }

}

class Clock extends CometActor with Logger with CometListener {
  override def registerWith = ClockMaster
  override def lifespan = Full(5 minutes)
  val triggerButtonContainer = nextFuncName
  val noticeContainer = nextFuncName
  val exceptionContainer = nextFuncName
  protected var username = ""
  protected var currentException:Option[Throwable] = None

  override def localSetup(): Unit = {
    super.localSetup()
  }

  def dateTime: Box[Date] = DependencyFactory.inject[Date] // inject the date

  override def fixedRender = NodeSeq.Empty

  Schedule.schedule(this, Tick, 10000L)

  def render =
    "#time2 *" #> dateTime.getOrElse("Unknown").toString

  case object LoadTest
  override def lowPriority = {
    case Tick =>
      println("Got tick " + dateTime)
      //partialUpdate(SetHtml("time2", Text(dateTime.getOrElse("Unknown").toString)))
      reRender()
      Schedule.schedule(this, Tick, 10000L)
    case TickWithRed =>
      println("Got tock ")
      partialUpdate(SetHtml("time2", Text("Tock")))
    case Nil =>
      println("cometActor starting up")
/*    case a:Option[Throwable] =>
      printException(a)*/
    case other =>
      println("cometActor received unknown message: %s".format(other))
  }

  /**
   *
   * @param in
   */
  protected def printException(in:Option[Throwable]):Unit = {
    currentException = in
    reRender()
  }
  protected def renderException:NodeSeq = currentException.map(e => {
    <div class="error">
      <span>{e.getMessage}</span>
      <div>{e.getStackTrace}</div>
    </div>
  }).getOrElse(NodeSeq.Empty)

  /*protected def generateButton:NodeSeq =  ajaxButton(buttonName, startXmlGenerator _, buttonDisabledClass, buttonColorClass)*/
/*  protected def startXmlGenerator(): JsCmd = {
    currentStatus = Start
    currentException = None
    RMERAXMLGenerateBridge ! RequestXmlGenerateStartMessage(username)
    SetHtml(triggerButtonContainer,generateButton) & SetHtml(noticeContainer,status)
  }*/
}