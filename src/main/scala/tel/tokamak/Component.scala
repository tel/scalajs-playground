package tel.tokamak

import japgolly.scalajs.react.ReactElement

trait HandleActions[Action] {
  def submit(a: Action): Unit
}

/**
  * Created by tel on 3/3/16.
  */
trait Component {
  type Params
  type State
  type Action
  type Context

  def init(p: Params): State
  def control(a: Action, s: State): State
  def view(s: State, ctx: Context): ReactElement
}

