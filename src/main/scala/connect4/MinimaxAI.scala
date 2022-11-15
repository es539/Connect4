package connect4

import base.{AIMove, TreeNode}

import scala.util.control.Breaks.{break, breakable}

class MinimaxAI(maxDepth: Int, alphaBeta: Boolean) {

  private final val INF: Int = 1000
  var tree: TreeNode = _
  private var bestCol: Int = _

  def cpuMove(board: IBoard): AIMove = {
    tree = new TreeNode()
    maximizer(board, 0, 0, INF, tree)
    tree.column = bestCol + 1
    new AIMove(bestCol, tree)
  }

  private def minimizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int, node: TreeNode): Int = {
    if (depth == maxDepth)
      return 0

    var bestScore: Int = INF
    var nextNode: TreeNode = null
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {

          // Tree representation info
          nextNode = new TreeNode()
          nextNode.depth = depth + 1
          nextNode.column = c + 1
          nextNode.row = board.nextValidRow(c) + 1
          nextNode.parent = node.column
          node.children :+= nextNode

          board.addPiece(c, Constant.YELLOW)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.YELLOW)
          val nextBoardScore: Int = maximizer(board, depth + 1, curBoardScore, bestScore, nextNode)
          val totalScore: Int = curBoardScore + nextBoardScore
          nextNode.score = totalScore

          board.removePiece(c) // BackTracking
          bestScore = Math.min(bestScore, totalScore)

          if (alphaBeta && bestScore + lastBoardScore <= lastBoardBestScore)
            break
        }
      }
    }
    // println(node + " ------ min")
    bestScore
  }

  private def maximizer(board: IBoard, depth: Int, lastBoardScore: Int, lastBoardBestScore: Int, node: TreeNode): Int = {
    if (depth == maxDepth)
      return 0

    var bestScore: Int = -INF
    var nextNode: TreeNode = null
    breakable {
      for (c <- 0 until board.getCols) {
        if (board.isValidRow(c)) {

          // Tree representation info
          nextNode = new TreeNode()
          nextNode.depth = depth + 1
          nextNode.column = c + 1
          nextNode.row = board.nextValidRow(c) + 1
          nextNode.parent = node.column
          node.children :+= nextNode

          board.addPiece(c, Constant.RED)
          val curBoardScore: Int = board.getHeuristicScore(c, Constant.RED)
          val nextBoardScore: Int = minimizer(board, depth + 1, curBoardScore, bestScore, nextNode)
          val totalScore: Int = curBoardScore + nextBoardScore
          nextNode.score = totalScore

          board.removePiece(c) // BackTracking
          if (depth == 0 && totalScore > bestScore)
            bestCol = c

          // if (depth == 0) println("score: " + totalScore)

          bestScore = Math.max(bestScore, totalScore)
          if (alphaBeta && bestScore + lastBoardScore >= lastBoardBestScore)
            break
        }
      }
    }
    // println(node + " ------ max")
    // if (depth == 0) print_array(node)
    bestScore
  }
}
