package base

class State(oldR: Int, oldC: Int, c: Int) {
  var currentCol: Int = oldC
  var currentRow: Int = oldR
  var color: Int = c
}
