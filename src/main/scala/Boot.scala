import com.evolutiongaming.comparator.{OmahaComparator, TexasHoldemComparator}
import com.evolutiongaming.parser.{OmahaInputParser, TexasHoldemParser}
import com.evolutiongaming.util.OutputWriter

import scala.io.StdIn
import scala.util.{Failure, Success, Try}


object Boot extends App {
  val pokerType: Try[String] = Try(args(0))

  val (parser, comparator) = pokerType match {
    case Success("--omaha")                => (OmahaInputParser, OmahaComparator)
    case Success("--texas-holdem") | Failure(_)  => (TexasHoldemParser, TexasHoldemComparator)
  }

  val inputs  = Iterator.continually(StdIn.readLine)
    .takeWhile(_.nonEmpty)
    .toList

  inputs
    .map { line =>
      val hands = OmahaInputParser.parseLine(line)
      OmahaComparator.compare(hands)
    }
    .foreach(OutputWriter.write(_, print))
}