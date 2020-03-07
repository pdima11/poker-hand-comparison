import com.evolutiongaming.comparator.OmahaComparator
import com.evolutiongaming.parser.OmahaInputParser
import com.evolutiongaming.util.OutputWriter

import scala.io.StdIn


object Boot extends App {
  val inputs  = Iterator.continually(StdIn.readLine)
    .takeWhile(_.nonEmpty)
    .toList

  inputs.foreach { line =>
    val hands = OmahaInputParser.parseLine(line)

    val combinations = OmahaComparator.compare(hands)

    OutputWriter.write(combinations, print)
  }

}