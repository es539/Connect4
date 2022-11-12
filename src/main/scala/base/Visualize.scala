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
import scala.util.control.Breaks.{break, breakable}

object Visualize {
  @throws[IOException]
  def visualize(tree: TreeNode): Unit = {
    //val dummyTree = new TreeNode(1, 0, 1)
    //dummyTree.children :+= new TreeNode(1, 1, 1)
    //val two = new TreeNode(2, 1, 2)
    //val three = new TreeNode(3, 1, 3)
    //dummyTree.children :+= two
    //dummyTree.children :+= three
    //dummyTree.children :+= new TreeNode(4, 1, 4)
    //two.children :+= new TreeNode(5, 2, 1)
    //two.children :+= new TreeNode(7, 2, 2)
    //two.children :+= new TreeNode(9, 2, 3)
    //three.children :+= new TreeNode(10, 2, 1)
    //three.children :+= new TreeNode(11, 2, 2)
    //three.children :+= new TreeNode(12, 2, 3)

    val g = mutGraph("tree").setDirected(true)
      .graphAttrs.add(Color.TRANSPARENT.background, Rank.dir(TOP_TO_BOTTOM))
      .nodeAttrs.add(Color.WHITE, Shape.TRIANGLE, Color.WHITE.font)
      .linkAttrs.add("class", "link-class")

    bfs(tree, g)
    Graphviz.fromGraph(g).height(2000).render(Format.PNG).toFile(new File("output/tree.png"))

    val image = new File("output/tree.png")
    val desktop = Desktop.getDesktop
    desktop.open(image)
  }

  def bfs(tree: TreeNode, g: MutableGraph): Unit = {
    val queue: mutable.Queue[TreeNode] = mutable.Queue()
    val nodes = new util.HashMap[String, MutableNode]
    queue.enqueue(tree)

    var c1: Color = null
    var c2: Color = null

    breakable{
      while (queue.nonEmpty) {
        val current: TreeNode = queue.dequeue()
        if (current.depth == 2)
          break()

        if (current.depth % 2 == 0) {
          c1 = Color.WHITE
          c2 = Color.RED
        }
        else {
          c1 = Color.RED
          c2 = Color.WHITE
        }

        if (nodes.get(current.hash) == null)
          nodes.put(current.hash, mutNode(current.score + ", " + current.depth +
            ", " + current.column + ", " + current.row + ", " + current.parent))
        val s = nodes.get(current.hash)

        if (current.depth % 2 == 0)
          s.add(Shape.TRIANGLE)
        else
          s.add(Shape.INV_TRIANGLE)

        for (child <- current.children) {
          var e: MutableNode = null
          if (nodes.get(child.hash) == null)
            nodes.put(child.hash, mutNode(child.score + ", " + child.depth + ", " +
              child.column + ", " + child.row + ", " + child.parent))
          e = nodes.get(child.hash)

          if (current.depth % 2 == 0)
            e.add(Shape.TRIANGLE)
          else
            e.add(Shape.INV_TRIANGLE)

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

  }
}
