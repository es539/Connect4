package base

class TreeNode {
  var score: Int = Int.MinValue
  var curScore: Int = Int.MinValue
  var nextScore: Int = Int.MinValue
  var depth: Int = 0
  var column: Int = -1
  var id: String = scala.util.Random.between(1, 10000).toString
  var children: Array[TreeNode] = Array()

  def this(depth: Int, c: Int) {
    this()
    this.depth = depth
    this.column = c
  }

  def setScores(score: Int, curScore:Int, nextScore:Int): Unit = {
    this.score = score
    this.curScore = curScore
    this.nextScore = nextScore
  }

  def hash: String = score + " " + depth + " " + column + " " + id

  override def toString: String = {
    score + " " + depth + " " + column + " " + id
  }
}
