package connect4

import base.{AIMove, TreeNode}

import scala.util.control.Breaks.{break, breakable}

class MinimaxAI(maxDepth: Int, alphaBeta: Boolean) {

  private final val INF: Int = 1000
  private var bestCol: Int = _
  var tree: TreeNode = _

  def cpuMove(board: IBoard): AIMove = {
    tree = new TreeNode()
    maximizer(board, 0, -INF, INF, tree)
    new AIMove(bestCol, tree)
  }

  private def minimizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int, node: TreeNode): Int = {
    if (depth == maxDepth || board.endGame) return 0

    var bestScore: Int = INF
    var nextNode: TreeNode = null
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {
          board.addPiece(c, Constant.YELLOW)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.YELLOW)

          nextNode = new TreeNode()
          nextNode.depth = depth + 1
          nextNode.column = c + 1
          nextNode.row = board.nextValidRow(c) + 1
          nextNode.parent = node.column
          node.children :+= nextNode

          val nextBoardScore: Int = maximizer(board, depth + 1, curBoardScore, bestScore, nextNode)
          board.removePiece(c)
          val totalScore: Int = curBoardScore + nextBoardScore

          bestScore = Math.min(bestScore, totalScore)
          node.score = totalScore
          if (alphaBeta && bestScore + lastBoardScore <= lastBoardBestScore)
            break
        }
      }
    }
    bestScore
  }

  private def maximizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int, node: TreeNode): Int = {
    if (depth == maxDepth || board.endGame) return 0
    var bestScore: Int = -INF
    var nextNode: TreeNode = null
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {
          board.addPiece(c, Constant.RED)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.RED)

          nextNode = new TreeNode()
          nextNode.depth = depth + 1
          nextNode.column = c + 1
          nextNode.row = board.nextValidRow(c) + 1
          nextNode.parent = node.column
          node.children :+= nextNode

          val nextBoardScore: Int = minimizer(board, depth + 1, curBoardScore, bestScore, nextNode)
          board.removePiece(c)
          val totalScore: Int = curBoardScore + nextBoardScore

          if (depth == 0 && totalScore > bestScore)
            bestCol = c

          bestScore = Math.max(bestScore, totalScore)
          node.score = totalScore
          if (alphaBeta && bestScore + lastBoardScore >= lastBoardBestScore)
            break
        }
      }
    }
    bestScore
  }
}
