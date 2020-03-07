import com.evolutiongaming.model.Combination
import com.evolutiongaming.model.ordering.CombinationOrdering
import com.evolutiongaming.util.InputParser

import scala.io.StdIn


object Boot extends App {
  val inputs  = Iterator.continually(StdIn.readLine)
    .takeWhile(_.nonEmpty)
    .toList

  inputs.foreach { line =>
    val hands = InputParser.parseLine(line)

    val sortedCombinations = hands
      .map(Combination.fromHand)
      .sorted(CombinationOrdering.orElseBy(_.hand.toString))

    println(sortedCombinations)
  }

}
