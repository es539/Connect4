package connect4

import javafx.scene.paint.Color

object Constant {
  final val RED: Char = 'R' // CPU
  final val YELLOW: Char = 'Y' // Human
  final val EMPTY: Char = 'E'
  final val SQUARE = 80
  final val CIRCLE_RADIUS = 35
  final val RED_COLOR: Color = Color.rgb(255, 0, 0)
  final val YELLOW_COLOR: Color = Color.rgb(255, 255, 0)
  final val EMPTY_COLOR: Color = Color.rgb(49, 46, 43)
  var TREE_REPRESENTATION: Boolean = true
}
