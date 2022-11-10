package base

class Piece(row: Int, col: Int, team: Int) {
  val color: Int = team
  var curRow: Int = row
  var curCol: Int = col
}