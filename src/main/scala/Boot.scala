import com.evolutiongaming.model.Combination
import com.evolutiongaming.util.InputParser

import scala.io.StdIn


object Boot extends App {
  val inputs  = Iterator.continually(StdIn.readLine)
    .takeWhile(_.nonEmpty)
    .toList

  inputs.foreach { line =>
    val hands = InputParser.parseLine(line)
    println(hands.map(Combination.fromHand))
  }

}
