package tel.tokamak

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom


/**
  * Created by tel on 3/3/16.
  */
class Reactor[A, S](el: dom.Element, c: Reactor.SimpleComponent[A, S]) {
  var root: S = c.init(())

  private def submit(a: c.Action): Unit = {
    root = c.control(a, root)
    render()
  }

  def render(): Unit = {
    ReactDOM.render(c.view(root, submit), el)
  }
}

object Reactor {
  type SimpleComponent[A, S] =
    Component {
      type Params = Unit
      type State = S
      type Action = A
      type Context = A => Unit
    }

  def apply[A, S](el: dom.Element, c: SimpleComponent[A, S]) =
    new Reactor(el, c)
}
