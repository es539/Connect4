package base

class TreeNode {
  var score = 0
  var depth = 0
  var id = 0
  var children: Array[TreeNode] = Array()

  def this(score: Int, depth: Int, id: Int) {
    this()
    this.score = score
    this.depth = depth
    this.id = id
  }

  def hash: String = score + " " + depth + " " + id
}
