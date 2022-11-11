package connect4

class Connect4ControllerTest extends org.scalatest.FunSuite {
  var gameBoard: Array[Array[Piece]] = _
  var testBoard: Array[Array[Piece]] = _

  test("testMovementValidation") {
    gameBoard = Array(
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(
        null,
        new Piece(5, 1, Connect4En.Red),
        new Piece(5, 2, Connect4En.Yellow),
        null, null, null, null
      )
    )

    assert(Connect4Controller.movementValidation(gameBoard, new State(1, 1, 0)).valid)
    assert(Connect4Controller.movementValidation(gameBoard, new State(4, 1, 0)).valid)
    assert(Connect4Controller.movementValidation(gameBoard, new State(5, 5, 0)).valid)

  }

  test("testCheckEndGame") {
    gameBoard = Array(
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(
        new Piece(4, 0, Connect4En.Yellow),
        null,
        null,
        null,
        null,
        null,
        null
      ),
      Array(
        new Piece(5, 0, Connect4En.Yellow),
        new Piece(5, 1, Connect4En.Yellow),
        new Piece(5, 2, Connect4En.Yellow),
        new Piece(5, 3, Connect4En.Red),
        new Piece(5, 4, Connect4En.Red),
        new Piece(5, 5, Connect4En.Red),
        new Piece(5, 6, Connect4En.Red)
      )
    )

    assert(!Connect4Controller.checkEndGame(gameBoard, 1))
  }

  test("testCheckNotEndGame") {
    gameBoard = Array(
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(
        null,
        new Piece(5, 1, Connect4En.Red),
        new Piece(5, 2, Connect4En.Yellow),
        null, null, null, null
      )
    )

    assert(!Connect4Controller.checkEndGame(gameBoard, 0))
  }

  test("testCheckNotTie") {
    gameBoard = Array(
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(null, null, null, null, null, null, null),
      Array(
        null,
        new Piece(5, 1, Connect4En.Red),
        new Piece(5, 2, Connect4En.Yellow),
        null, null, null, null
      )
    )

    assert(!Connect4Controller.checkEndGame(gameBoard, 0))
  }

  test("testCheckTie") {
    gameBoard = Array(
      Array(
        new Piece(0, 0, Connect4En.Yellow),
        new Piece(0, 1, Connect4En.Red),
        new Piece(0, 2, Connect4En.Yellow),
        new Piece(0, 3, Connect4En.Red),
        new Piece(0, 4, Connect4En.Yellow),
        new Piece(0, 5, Connect4En.Red),
        new Piece(0, 6, Connect4En.Yellow)
      ),
      Array(
        new Piece(1, 0, Connect4En.Yellow),
        new Piece(1, 1, Connect4En.Red),
        new Piece(1, 2, Connect4En.Yellow),
        new Piece(1, 3, Connect4En.Yellow),
        new Piece(1, 4, Connect4En.Yellow),
        new Piece(1, 5, Connect4En.Red),
        new Piece(1, 6, Connect4En.Yellow)
      ),
      Array(
        new Piece(2, 0, Connect4En.Red),
        new Piece(2, 1, Connect4En.Yellow),
        new Piece(2, 2, Connect4En.Red),
        new Piece(2, 3, Connect4En.Red),
        new Piece(2, 4, Connect4En.Red),
        new Piece(2, 5, Connect4En.Yellow),
        new Piece(2, 6, Connect4En.Red)
      ),
      Array(
        new Piece(3, 0, Connect4En.Yellow),
        new Piece(3, 1, Connect4En.Red),
        new Piece(3, 2, Connect4En.Yellow),
        new Piece(3, 3, Connect4En.Yellow),
        new Piece(3, 4, Connect4En.Yellow),
        new Piece(3, 5, Connect4En.Red),
        new Piece(3, 6, Connect4En.Yellow)
      ),
      Array(
        new Piece(4, 0, Connect4En.Red),
        new Piece(4, 1, Connect4En.Yellow),
        new Piece(4, 2, Connect4En.Red),
        new Piece(4, 3, Connect4En.Red),
        new Piece(4, 4, Connect4En.Red),
        new Piece(4, 5, Connect4En.Yellow),
        new Piece(4, 6, Connect4En.Red)
      ),
      Array(
        new Piece(5, 0, Connect4En.Red),
        new Piece(5, 1, Connect4En.Yellow),
        new Piece(5, 2, Connect4En.Red),
        new Piece(5, 3, Connect4En.Yellow),
        new Piece(5, 4, Connect4En.Red),
        new Piece(5, 5, Connect4En.Yellow),
        new Piece(5, 6, Connect4En.Red)
      )
    )

    assert(Connect4Controller.checkEndGame(gameBoard, 0))
  }

}
