package connect4

import scala.collection.mutable

class Board(rows: Int, cols: Int) extends IBoard {

  private final val dim: Array[(Int, Int)] = Array((0, 1), (1, 1), (1, 0), (1, -1))
  private val board: Array[Array[Char]] = Array.ofDim[Char](rows, cols)

  init()

  override def getCols: Int = {
    cols
  }

  override def addPiece(c: Int, pieceType: Char): Unit = {
    if (isValidMove(c)) {
      board(nextValidRow(c))(c) = pieceType
    }
  }

  override def isValidMove(c: Int): Boolean = {
    inBound(c) && isValidRow(c)
  }

  override def isValidRow(c: Int): Boolean = {
    nextValidRow(c) != -1
  }

  override def removePiece(c: Int): Unit = {
    if (inBound(c)) {
      val r: Int = nextValidRow(c)
      board(if (r == -1) {
        0
      } else {
        r + 1
      })(c) = Constant.EMPTY
    }
  }

  override def inBound(c: Int): Boolean = {
    c >= 0 && c < cols
  }

  override def nextValidRow(c: Int): Int = {
    for (r <- rows - 1 to 0 by -1) {
      if (board(r)(c) == Constant.EMPTY)
        return r
    }
    -1
  }

  override def getHeuristicScore(c: Int, player: Char): Int = {
    val t = nextValidRow(c)
    val r = if (t == -1) {
      0
    } else {
      t + 1
    }
    var score: Int = 0
    for (move <- dim) {
      val dx: Int = move._2
      val dy: Int = move._1
      for (i <- 0 until 4) {
        score += windowScore(r - dx * i, c - dy * i, dx, dy, player)
      }
    }
    if (player == Constant.YELLOW) {
      -1 * score
    } else score
  }

  private def windowScore(r: Int, c: Int, dx: Int, dy: Int, player: Char): Int = {
    var curPlayer: Int = 0
    var oppPlayer: Int = 0
    var _c: Int = c
    var _r: Int = r
    for (_ <- 0 until 4) {
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
      return if (curPlayer > 1) {
        0
      } else {
        10 * oppPlayer
      }
    if (curPlayer == 4) {
      40
    } else {
      3 * curPlayer
    }
  }

  private def inRange(r: Int, c: Int): Boolean = {
    r >= 0 && r < rows && c >= 0 && c < cols
  }

  override def endGame: Boolean = {
    for (i <- 0 until rows; j <- 0 until cols)
      if (empty(i, j)) return false
    true
  }

  private def empty(r: Int, c: Int): Boolean = {
    board(r)(c) == Constant.EMPTY
  }

  override def getBoardScore: Array[Int] = {
    var redScore: Int = 0
    var yellowScore: Int = 0
    yellowScore = calcScore(Constant.YELLOW)
    redScore = calcScore(Constant.RED)
    Array(yellowScore, redScore)
  }

  def calcScore(turn: Char): Int = {
    var score: Int = 0

    var dx: Array[Int] = Array(0, 1, 2, 3)
    var dy: Array[Int] = Array(0, 0, 0, 0)
    score += calcGroup(0, 0, rows - 3, cols, dx, dy, turn)

    dx = Array(0, 0, 0, 0)
    dy = Array(0, 1, 2, 3)
    score += calcGroup(0, 0, rows, cols - 3, dx, dy, turn)

    dx = Array(0, 1, 2, 3)
    dy = Array(0, 1, 2, 3)
    score += calcGroup(0, 0, rows - 3, cols - 3, dx, dy, turn)

    dx = Array(0, 1, 2, 3)
    dy = Array(0, -1, -2, -3)
    score += calcGroup(0, 3, rows - 3, cols, dx, dy, turn)

    score
  }

  def calcGroup(startRow: Int, startCol: Int, endRow: Int, endCol: Int, rowPolicy: Array[Int], colPolicy: Array[Int], turn: Char): Int = {
    var score: Int = 0
    for (row <- startRow until endRow)
      for (col <- startCol until endCol) {
        if (board(row)(col) == turn) {
          var equal: Int = 0
          for (i <- 0 until 4) {
            val newRow: Int = row + rowPolicy(i)
            val newCol: Int = col + colPolicy(i)
            equal += (if (board(newRow)(newCol) == turn) 1 else 0)
          }
          score += (if (equal == 4) 1 else 0)
        }
      }
    score
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

  private def init(): Unit = {
    for (i <- 0 until rows; j <- 0 until cols) {
      board(i)(j) = Constant.EMPTY
    }
  }
}
