package tutorial.webapp.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactElement}
import tel.tokamak.Component

/**
  * Created by tel on 3/3/16.
  */
object CounterSet extends Component {
  type Params = Unit
  type State = List[Counter.State]
  type Context = Action => Unit

  abstract class Action
  final case class PushCounter(p: Int) extends Action
  final case class RemoveCounter(index: Int) extends Action
  final case class ActCounter(index: Int, act: Counter.Action) extends Action

  def actCounter(index: Int)(act: Counter.Action): Action =
    ActCounter(index, act)

  def init(p: Params): State = List()

  def view(s: State, ctx: Context): ReactElement = {
    val counters = s.zipWithIndex.map {
      case (state, index) =>
        val localSubmit = ctx compose actCounter(index)
        def remove() = ctx(RemoveCounter(index))
        Counter.view(state, Counter.Context(localSubmit, remove))
    }
    <.div(
      <.button("New Counter", ^.onClick --> Callback{ctx(PushCounter(0))}),
      <.div(^.id := "counters", counters)
    )
  }

  def control(a: Action, s: State): State = a match {
    case PushCounter(p) => Counter.init(p) +: s
    case RemoveCounter(index) => s splitAt index match {
      case (fore, Nil) => fore
      case (fore, counte :: hind) => fore ++ hind
    }
    case ActCounter(index, act) => s splitAt index match {
      case (fore, Nil) => fore
      case (fore, counter :: hind) =>
        val newHind = Counter.control(act, counter) :: hind
        fore ++ newHind
    }
  }

}

