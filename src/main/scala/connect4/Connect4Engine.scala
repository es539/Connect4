package connect4

import base._
import javafx.scene.Node
import javafx.scene.layout.{GridPane, StackPane}

class Connect4Engine(players: Array[Player], gameType: String) extends GameEngine(players, gameType) {
  override var gameBoard: Array[Array[Piece]] = Array.ofDim[Piece](6, 7)
  override var score: Array[Int] = Array(0, 0)
  override var tree: TreeNode = _

  override def startGame(gamePane: StackPane): Unit = {
    Connect4Drawer.setGamePane(gamePane)
    players(1).color = 1
    Connect4Drawer.drawInit()

    players.foreach(player => player.run())
    Connect4Drawer.setEvents(Movement)

    play()
  }

  override def Movement(source: Node): Unit = {
    source.setOnMouseClicked(_ => {
      if (players(turn).isInstanceOf[Connect4AI]) return
      if (gameBoard(5 - GridPane.getRowIndex(source))(GridPane.getColumnIndex(source)) == null) {
        val validation = Connect4Controller.movementValidation(gameBoard,
          new State(0, GridPane.getColumnIndex(source), turn))
        if (validation.valid) {
          val s: State = new State(validation.state.currentRow, GridPane.getColumnIndex(source), turn)
          Connect4Drawer.movementDraw(source, s)
          update()
        }
      }
    })
  }

  override def update(): Unit = {
    turn = 1 - turn

    if (Connect4Controller.checkEndGame(gameBoard) && !gameEnded) {
      gameEnded = true
      Connect4Controller.getScore(gameBoard, turn)
      Connect4Drawer.drawEnd(score)
    }

    play()
  }

  override def play(): Unit = {
    if (players(turn).isInstanceOf[Connect4AI] && !gameEnded) {
      tree = null
      players(turn).Movement()
    }
  }

  override def showTree(): Unit = {
    Visualize.visualize(tree)
  }
}
