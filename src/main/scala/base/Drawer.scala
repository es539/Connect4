package base

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.{Font, FontWeight}

trait Drawer {
  var gamePane: StackPane
  var gameBoard: GridPane

  /**
   * Returns a grid pane that holds the board in the GUI.
   *
   * @param rows          number of rows in the game.
   * @param cols          number of columns in the game.
   * @param color1        the first color of the squares.
   * @param color2        the second color of the squares.
   * @param showGridLines shows lines between cells if true.
   * @return Grid pane
   */
  def drawBoard(rows: Int, cols: Int, color1: Color, color2: Color, showGridLines: Boolean): GridPane = {
    val board = new GridPane
    for (i <- 0 until rows) {
      if (i % 2 == 0) {
        for (j <- 0 until cols) {
          val rect = new Rectangle(80, 80)

          if (j % 2 == 0) rect.setFill(color1)
          else rect.setFill(color2)

          board.add(rect, j, i)
        }
      } else {
        for (j <- 0 until cols) {
          val rect = new Rectangle(80, 80)
          if (j % 2 != 0) rect.setFill(color1)
          else rect.setFill(color2)

          board.add(rect, j, i)
        }
      }
    }

    board.setVisible(true)
    board.setAlignment(Pos.CENTER)
    board.setGridLinesVisible(showGridLines)
    gamePane.setAlignment(Pos.CENTER)
    gamePane.getChildren.add(board)

    board
  }

  /**
   * Draws the board and anything the game needs.
   */
  def drawInit(): Unit

  def drawEnd(score: Array[Int]): Unit = {
    val text = new Label()
    text.setFont(Font.font("Roboto", FontWeight.BOLD, 40))
    text.setTextFill(Color.rgb(200, 200, 200, 1))

    text.setText(score(0).toString + " - " + score(1).toString)

    gamePane.setAlignment(Pos.TOP_CENTER)
    gamePane.getChildren.add(text)
  }

  /**
   * Set the events of the pieces to its player.
   *
   * @param Event the type of event to set.
   * @param board the game board which has been played so far.
   * @param s     start index of player's pieces.
   * @param e     end index of player's pieces.
   */
  def setEvents(Event: Node => Unit,
                board: Array[Array[Piece]] = Array.ofDim[Piece](0, 0), s: Int = 0, e: Int = 0): Unit

  /**
   * Draws player's new move.
   *
   * @param source the node the player clicked on it.
   * @param state  the state the piece will go to it.
   * @param arg    node to be removed if there is eaten pieces.
   */
  def movementDraw(source: Node, state: State, arg: Node = null): Unit

  /**
   * Sets the game pane.
   *
   * @param newGamePane stack pane that will hold the game pane.
   */
  def setGamePane(newGamePane: StackPane): Unit = {
    gamePane = newGamePane
  }
}
