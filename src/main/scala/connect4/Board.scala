package connect4

import scala.collection.mutable

class Board(rows: Int, cols: Int) extends IBoard {

  private val board: Array[Array[Char]] = Array.ofDim[Char](rows, cols)
  private final val dim: Array[(Int, Int)] = Array((0, 1), (1, 1), (1, 0), (1, -1))

  {
    init()
  }

  private def init(): Unit = {
    for (i <- 0 until rows; j <- 0 until cols) {
      board(i)(j) = Constant.EMPTY
    }
  }

  private def inRange(r: Int, c: Int): Boolean = {
    r >= 0 && r < rows && c >= 0 && c < cols
  }

  private def empty(r: Int, c: Int): Boolean = {
    board(r)(c) == Constant.EMPTY
  }

  override def inBound(c: Int): Boolean = {
    c >= 0 && c < cols
  }

  override def getCols: Int = {
    cols
  }

  override def nextValidRow(c: Int): Int = {
    for (r <- rows - 1 to 0 by -1) {
      if (board(r)(c) == Constant.EMPTY)
        return r
    }
    -1
  }

  override def isValidRow(c: Int): Boolean = {
      nextValidRow(c) != -1
  }

  override def isValidMove(c: Int): Boolean = {
    inBound(c) && isValidRow(c)
  }

  override def addPiece(c: Int, pieceType: Char): Unit ={
    if (isValidMove(c)) {
      board(nextValidRow(c))(c) = pieceType
    }
  }

  override def removePiece(c: Int): Unit = {
    if (inBound(c)) {
      val r: Int = nextValidRow(c)
      board(if (r == -1) {0} else {r + 1})(c) = Constant.EMPTY
    }
  }

  private def windowScore(r: Int, c: Int, dx: Int, dy: Int, player: Char): Int = {
    var curPlayer: Int = 0
    var oppPlayer: Int = 0
    var _c: Int = c
    var _r: Int = r
    for (i <- 0 until 4) {
      if (!inRange(_r, _c)) return 0
      if (board(_r)(_c) != Constant.EMPTY) {
        if (board(_r)(_c) == player) curPlayer += 1
        else oppPlayer += 1
      }
      _r += dx
      _c += dy
    }

    // Score Policy
    if (oppPlayer > 0)
      return if (curPlayer > 1) {0} else {10 * oppPlayer}
    if (curPlayer == 4) {40} else {3 * curPlayer}
  }

  override def getHeuristicScore(c: Int, player: Char): Int = {
    val t = nextValidRow(c)
    val r = if (t == -1) {0} else {t + 1}
    var score: Int = 0
    for (move <- dim) {
      val dx: Int = move._2
      val dy: Int = move._1
      for (i <- 0 until 4) {
        score += windowScore(r - dx * i, c - dy * i, dx, dy, player)
      }
    }
    if (player == Constant.YELLOW) {-1 * score} else score
  }

  override def endGame: Boolean = {
    for (i <- 0 until rows; j <- 0 until cols)
      if (empty(i, j)) return false
    true
  }

  override def getBoardScore: Int = {
    // go ahead ya mamdouh
    0
  }

  override def toString: String = {
    val s = new mutable.StringBuilder()
    board.foreach(row => {
      row.foreach(cell => {
        s ++= cell + " "
      })
      s += '\n'
    })
    s.toString
  }
}
