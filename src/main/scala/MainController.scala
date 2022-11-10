import base.Player
import connect4.{Connect4AI, Connect4Engine, Connect4Player}
import javafx.collections.FXCollections
import javafx.scene.control.{Button, ComboBox, TextArea}
import javafx.scene.layout.{AnchorPane, StackPane}
import javafx.util.Pair
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml


@sfxml
class MainController(var gamePane: StackPane, val menuPane: AnchorPane,
                     val returnButton: Button, val returnButtonImg: ImageView,
                     gameMode: ComboBox[String], miniMaxType: ComboBox[String], val input: TextArea,
                     val showTreeImg: ImageView, val showTreeButton: Button) {
  gameMode.getItems.addAll(FXCollections.observableArrayList("PvP", "PvA", "AvP"))
  miniMaxType.getItems.addAll(FXCollections.observableArrayList("Minimax", "Alpha-Beta"))
  val gameModeMap: Map[Pair[String, String], Array[Player]] = Map(
    new Pair[String, String]("PvA", "connect4") -> Array[Player](new Connect4Player, new Connect4AI),
    new Pair[String, String]("PvP", "connect4") -> Array[Player](new Connect4Player, new Connect4Player),
    new Pair[String, String]("AvP", "connect4") -> Array[Player](new Connect4AI, new Connect4Player),
  )
  var gM: String = _
  var miniMaxAIType: String = _
  var depth: Int = _

  /**
   * Start Connect-4 Game when the button is pressed.
   */
  def Connect4Start(): Unit = {
    init()
    val players = gameModeMap(new Pair[String, String](gM, "connect4"))
    players(0).setDepth(depth)
    players(1).setDepth(depth)
    players(0).setMiniMaxType(miniMaxAIType)
    players(1).setMiniMaxType(miniMaxAIType)
    val gameEngine = new Connect4Engine(players, gameMode.getValue)
    showTreeButton.setOnMouseClicked(_ => gameEngine.showTree())
    players(0).setObserver(gameEngine)
    players(1).setObserver(gameEngine)
    gameEngine.startGame(gamePane)
  }

  /**
   * Initialize the game.
   */
  def init(): Unit = {
    gameMode(true)

    depth = if (input.getText == "") 6 else input.getText.toInt
    gM = if (gameMode.getValue == null) "PvA" else gameMode.getValue
    miniMaxAIType = if (miniMaxType.getValue == null) "Alpha-Beta" else miniMaxType.getValue
  }

  /**
   * Sets some boxes to be visible and others invisible.
   *
   * @param boolean indicate which to turn on.
   */
  def gameMode(boolean: Boolean): Unit = {
    gamePane.getChildren.clear()
    gameMode.setVisible(!boolean)
    menuPane.setVisible(!boolean)
    showTreeButton.setVisible(boolean)
    showTreeImg.setVisible(boolean)
    returnButton.setVisible(boolean)
    returnButtonImg.setVisible(boolean)
    gamePane.setVisible(boolean)
  }

  /**
   * Returns to main menu when the button is pressed.
   */
  def returnMenu(): Unit = {
    gameMode(false)
  }

}
