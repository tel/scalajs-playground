package tutorial.webapp.components

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactElement}
import tel.tokamak.Component

/**
  * Created by tel on 3/3/16.
  */
object Counter extends Component {
  type State = Int
  type Params = State

  case class Context(
                      submit: Action => Unit,
                      remove: () => Unit
                    )

  abstract class Action
  case object Increment extends Action
  case object Decrement extends Action

  def init(p: Params): State = p

  def view(s: State, ctx: Context): ReactElement = {
    <.div(
      <.span(
        ^.classID := "lalala",
        s
      ),
      <.button("+", ^.onClick --> Callback{ ctx submit Increment }),
      <.button("-", ^.onClick --> Callback{ ctx submit Decrement }),
      <.button("X", ^.onClick --> Callback{ ctx.remove() })
    )
  }

  def control(a: Action, s: State): State = a match {
    case Increment => s + 1
    case Decrement => s - 1
  }
}
