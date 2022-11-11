package connect4

trait IBoard {
  def inBound(c: Int): Boolean

  def getCols: Int

  def nextValidRow(c: Int): Int

  def isValidRow(c: Int): Boolean

  def isValidMove(c: Int): Boolean

  def addPiece(c: Int, pieceType: Char): Unit

  def removePiece(c: Int): Unit

  def getHeuristicScore(c: Int, player: Char): Int

  def endGame: Boolean

  def getBoardScore: Int
}
