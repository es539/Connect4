package connect4

import base.TreeNode

import scala.util.control.Breaks.{break, breakable}

class MinimaxAI(maxDepth: Int, alphaBeta: Boolean) {

  private final val INF: Int = 1000
  private var bestCol: Int = _

  def cpuMove(board: IBoard): Int = {
    val root: TreeNode = new TreeNode()
    maximizer(board, 0, -INF, INF)
    bestCol
  }

  private def minimizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int): Int = {
    if (depth == maxDepth || board.endGame) return 0
    var bestScore: Int = INF
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {
          board.addPiece(c, Constant.YELLOW)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.YELLOW)
          val nextBoardScore: Int = maximizer(board, depth + 1, curBoardScore, bestScore)
          board.removePiece(c)
          val totalScore: Int = curBoardScore + nextBoardScore
          bestScore = Math.min(bestScore, totalScore)
          if (alphaBeta && bestScore + lastBoardScore <= lastBoardBestScore)
            break
        }
      }
    }
    bestScore
  }

  private def maximizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int): Int = {
    if (depth == maxDepth || board.endGame) return 0
    var bestScore: Int = -INF
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {
          board.addPiece(c, Constant.RED)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.RED)
          val nextBoardScore: Int = minimizer(board, depth + 1, curBoardScore, bestScore)
          board.removePiece(c)
          val totalScore: Int = curBoardScore + nextBoardScore
          if (depth == 0 && totalScore > bestScore)
            bestCol = c
          bestScore = Math.max(bestScore, totalScore)
          if (alphaBeta && bestScore + lastBoardScore >= lastBoardBestScore)
            break
        }
      }
    }
    bestScore
  }
}
