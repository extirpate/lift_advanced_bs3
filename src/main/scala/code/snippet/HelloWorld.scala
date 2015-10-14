package code 
package snippet 

import java.util.Date

import code.comet.{ClockMaster, TickWithRed}
import code.lib._
import net.liftweb.common._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._

import scala.xml.Text

class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  // replace the contents of the element with id "time" with the date
  def howdy = {
    "#time *" #> date.map(_.toString) &
    ".button_1 *" #> SHtml.ajaxButton(Text("Tock!"),{
      () => ClockMaster ! TickWithRed
      Noop})
  }

  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */
}

