import connect4.Game
import javafx.collections.FXCollections
import javafx.scene.control.{Button, ComboBox, TextArea}
import javafx.scene.layout.{AnchorPane, StackPane}
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

@sfxml
class MainController(var gamePane: StackPane, val menuPane: AnchorPane,
                     val returnButton: Button, val returnButtonImg: ImageView,
                     miniMaxType: ComboBox[String], val input: TextArea,
                     val showTreeImg: ImageView, val showTreeButton: Button) {

  miniMaxType.getItems.addAll(FXCollections.observableArrayList("Minimax", "Alpha-Beta"))

  var pruning: Boolean = _
  var depth: Int = _

  /**
   * Start Connect-4 Game when the button is pressed.
   */
  def Connect4Start(): Unit = {
    init()
    val game: Game = new Game(gamePane, depth, pruning)
    showTreeButton.setOnMouseClicked(_ => game.drawTree())
  }

  /**
   * Initialize the game.
   */
  def init(): Unit = {
    gameMode(true)
    depth = if (input.getText == "") 6 else input.getText.toInt
    val pruningType = if (miniMaxType.getValue == null) "Alpha-Beta" else miniMaxType.getValue
    pruning = if (pruningType == "Alpha-Beta") true else false
  }

  /**
   * Returns to main menu when the button is pressed.
   */
  def returnMenu(): Unit = {
    gameMode(false)
  }

  /**
   * Sets some boxes to be visible and others invisible.
   *
   * @param boolean indicate which to turn on.
   */
  def gameMode(boolean: Boolean): Unit = {
    gamePane.getChildren.clear()
    menuPane.setVisible(!boolean)
    showTreeButton.setVisible(boolean)
    showTreeImg.setVisible(boolean)
    returnButton.setVisible(boolean)
    returnButtonImg.setVisible(boolean)
    gamePane.setVisible(boolean)
  }
}
