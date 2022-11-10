package base

trait Controller {
  /**
   * Returns an object that holds two values the whole state and true if the move is valid.
   *
   * @param gameBoard the game board which has been played so far.
   * @param state     object that holds the current state (if exists) and the new state.
   * @return an object that holds a state and true if the move is valid.
   */
  def movementValidation(gameBoard: Array[Array[Piece]], state: State): MoveValidation

  /**
   * Returns ture if the game has ended as tie.
   *
   * @param gameBoard the game board which has been played so far.
   * @param turn      identifies who will play.
   * @return ture if the game has ended as tie.
   */
  def checkEndGame(gameBoard: Array[Array[Piece]], turn: Int = -1): Boolean

  def getScore(gameBoard: Array[Array[Piece]], turn: Int = -1): Array[Int]
}
