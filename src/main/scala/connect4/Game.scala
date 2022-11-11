package connect4

import javafx.geometry.{HPos, Pos, VPos}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{GridPane, StackPane}
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, Rectangle}

class Game(pane: StackPane, depth: Int, alphaBeta: Boolean) {

  var board: IBoard = _
  var ai: MinimaxAI = _
  var gridPane: GridPane = _

  {
    drawInit()
  }

  def drawInit(): Unit = {
    board = new Board(6, 7)
    ai = new MinimaxAI(depth, alphaBeta)
    gridPane = new GridPane()
    drawBoard(6, 7, Color.rgb(0, 0, 139))
  }

  def drawBoard(rows: Int, cols: Int, color: Color): Unit = {
    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        val rect = new Rectangle(Constant.SQUARE, Constant.SQUARE)
        rect.setFill(color)
        gridPane.add(rect, j, i)
        drawCircle(i, j, Constant.EMPTY_COLOR)
      }
    }

    gridPane.setVisible(true)
    gridPane.setAlignment(Pos.CENTER)
    gridPane.setOnMouseClicked(e => doAction(e))
    pane.setAlignment(Pos.CENTER)
    pane.getChildren.add(gridPane)
  }

  private def doAction(e: MouseEvent): Unit ={
    var col: Int = (e.getX.toInt - 260) / Constant.SQUARE
    if (!board.endGame && board.inBound(col)) {
      drawCircle(board.nextValidRow(col), col, Constant.YELLOW_COLOR)
      board.addPiece(col, Constant.YELLOW)
      println("Board After Human Move: \n" + board)
      if (!board.endGame) {
        col = ai.cpuMove(board)
        println("AI col: " + col)
        drawCircle(board.nextValidRow(col), col, Constant.RED_COLOR)
        board.addPiece(col, Constant.RED)
        println("Board After AI Move: \n" + board)
      }
    }
  }

  private def drawCircle(i: Int, j: Int, color: Color): Unit = {
    val circle = new Circle(Constant.CIRCLE_RADIUS)
    circle.setFill(color)
    gridPane.add(circle, j, i, 1, 1)
    GridPane.setHalignment(circle, HPos.CENTER)
    GridPane.setValignment(circle, VPos.CENTER)
  }

}
