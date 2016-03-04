package tutorial.webapp

import org.scalajs.dom.{Element, document}
import tel.tokamak._
import tutorial.webapp.components.CounterSet

import scala.scalajs.js.JSApp

object TutorialApp extends JSApp {

  def container: Element = document.querySelector("#container")
  val rx = Reactor(container, CounterSet)

  def main() = {
    rx.render()
  }

}