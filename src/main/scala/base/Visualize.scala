package base

import guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM
import guru.nidi.graphviz.attribute.{Color, Rank, Shape}
import guru.nidi.graphviz.engine.{Format, Graphviz}
import guru.nidi.graphviz.model.Factory.{mutGraph, mutNode}
import guru.nidi.graphviz.model.Link.to
import guru.nidi.graphviz.model.{MutableGraph, MutableNode}

import java.awt.Desktop
import java.io.{File, IOException}
import java.util
import scala.collection.mutable

object Visualize {
  @throws[IOException]
  def visualize(tree: TreeNode): Unit = {
    val name: String = "tree_" + uuid
    val output: String = "output/" + name + ".png"

    val g = mutGraph(name).setDirected(true)
      .graphAttrs.add(Color.TRANSPARENT.background, Rank.dir(TOP_TO_BOTTOM))
      .nodeAttrs.add(Color.WHITE, Shape.TRIANGLE, Color.WHITE.font)
      .linkAttrs.add("class", "link-class")

    bfs(tree, g)
    Graphviz.fromGraph(g).height(200).render(Format.PNG).toFile(new File(output))

    val image = new File(output)
    val desktop = Desktop.getDesktop
    desktop.open(image)
  }

  def bfs(tree: TreeNode, g: MutableGraph): Unit = {
    var nodesExpanded: Int = 0
    val queue: mutable.Queue[TreeNode] = mutable.Queue()
    val nodes = new util.HashMap[String, MutableNode]
    queue.enqueue(tree)

    var c1: Color = null
    var c2: Color = null
    var shape: Shape = null

    while (queue.nonEmpty) {
      val current: TreeNode = queue.dequeue()
      nodesExpanded += 1

      if (current.depth % 2 == 0) {
        c1 = Color.WHITE
        c2 = Color.RED
        shape = Shape.TRIANGLE
      }
      else {
        c1 = Color.RED
        c2 = Color.WHITE
        shape = Shape.INV_TRIANGLE
      }

      if (nodes.get(current.hash) == null)
        nodes.put(current.hash, mutNode("\u25CF " + current.column))

      val s = nodes.get(current.hash)
      s.add(shape)

      val children = current.children.filter(treeNode => treeNode.score != Int.MinValue)

      for (child <- children) {
        var e: MutableNode = null
        if (nodes.get(child.hash) == null)
          nodes.put(child.hash, mutNode(s"(${child.score}, ${child.curScore}, ${child.nextScore}), " +
            s"${child.column}, ${child.depth}, ${child.id}"))
        e = nodes.get(child.hash)
        e.add(shape)

        queue.enqueue(child)
        g.add(
          s.add(c1)
            .addLink(
              to(
                e.add(c2)
              ).add(Color.WHITE)
            )
        )
      }
    }
  }

  def uuid: String = new scala.util.Random().between(1, 10000).toString
}
