package base

class TreeNode {
  var score: Int = Int.MinValue
  var depth: Int = 0
  var column: Int = -1
  var row: Int = -1
  var parent: Int = -1
  var children: Array[TreeNode] = Array()

  def this(score: Int, depth: Int, c: Int, r: Int, p: Int) {
    this()
    this.score = score
    this.depth = depth
    this.column = c
    this.row = r
    this.parent = p
  }

  def hash: String = score + " " + depth + " " + column + " " + row + " " + parent

  override def toString: String = {
    score + " " + depth + " " + column + " " + row + " " + parent
  }
}
