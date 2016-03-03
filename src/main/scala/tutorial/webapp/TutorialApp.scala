package tutorial.webapp

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp

trait Component {
  type Params
  type State
  type Action
  def init(p: Params): State
  def control(a: Action)(s: State): State
  def view(s: State, submit: Action => Unit): ReactElement
}

object Counter extends Component {
  type State = Int
  type Params = State

  abstract class Action
  case class Increment() extends Action
  case class Decrement() extends Action

  def init(p: Params): State = p

  def view(s: State, submit: Action => Unit): ReactElement = {
    <.div(
      <.span(
        ^.classID := "lalala",
        s
      ),
      <.button("+", ^.onClick --> Callback{submit(Increment())}),
      <.button("-", ^.onClick --> Callback{submit(Decrement())})
    )
  }

  def control(a: Action)(s: State): State = a match {
    case Increment() => s + 1
    case Decrement() => s - 1
  }
}

object CounterSet extends Component {
  type Params = Unit
  type State = List[Counter.State]

  abstract class Action
  case class PushCounter(p: Int) extends Action
  case class PopCounter() extends Action
  case class ActCounter(index: Int, act: Counter.Action) extends Action

  def actCounter(index: Int)(act: Counter.Action): Action =
    ActCounter(index, act)

  def init(p: Params): State = List()

  def view(s: State, submit: Action => Unit): ReactElement = {
    val counters = s.zipWithIndex.map {
      case (state, index) =>
        val localSubmit = submit compose actCounter(index)
        Counter.view(state, localSubmit)
    }
    <.div(
      <.button("Add Counter", ^.onClick --> Callback{submit(PushCounter(0))}),
      <.button("Remove Counter", ^.onClick --> Callback{submit(PopCounter())}),
      <.div(^.id := "counters", counters)
    )
  }

  def control(a: Action)(s: State): State = a match {
    case PushCounter(p) => Counter.init(p) +: s
    case PopCounter() => s.drop(1)
    case ActCounter(index, act) => s splitAt index  match {
      case (fore, Nil) => fore
      case (fore, counter :: hind) =>
        val newHind = Counter.control(act)(counter) :: hind
        fore ++ newHind
    }
  }

}

object TutorialApp extends JSApp {

  type State = CounterSet.State
  type Action = CounterSet.Action

  var root: State = CounterSet.init()

  // Update the app state
  def submit(a: Action): Unit = {
    root = CounterSet.control(a)(root)
    println(root)
    render()
  }

  def container = dom.document.querySelector("#container")

  def render(): Unit = {
    ReactDOM.render(CounterSet.view(root, submit), container)
  }

  def main(): Unit = {
    render()
  }

}