import java.io.{File, FileWriter, PrintWriter}
import java.net.URL
import java.nio.file.Paths

import com.evolutiongaming.model.Combination
import com.evolutiongaming.model.ordering.CombinationOrdering
import com.evolutiongaming.util.{InputParser, OutputWriter}
import org.scalatest.wordspec.AnyWordSpec

import scala.io.{BufferedSource, Source}
import org.scalatest.matchers.should.Matchers._


object Comparator {
  def compare(actual: URL, expected: URL): Unit = {
    val actualSource = Source.fromURL(actual)
    val expectedSource = Source.fromURL(expected)

    for (
      (actual, expected) <- actualSource.getLines().zip(expectedSource.getLines())
    ) actual should be(expected)

  }
}


class TestComparator extends AnyWordSpec {
  val fileName = "texas-25000"

  val files = Map(
    "input"    -> getClass.getResource(s"/data/input/$fileName.in"),
    "output"   -> getClass.getResource(s"/data/output/$fileName.out"),
    "expected" -> getClass.getResource(s"/data/expected/$fileName.reference")
  )

  val source: BufferedSource = Source.fromURL(files("input"))
  val writer = new PrintWriter(Paths.get(files("output").toURI).toFile)

  for (line <- source.getLines()) {
    val sortedCombinations = InputParser.parseLine(line)
      .map(Combination.fromHand)
      .sorted(CombinationOrdering.orElseBy(_.hand.toString))

    OutputWriter.write(sortedCombinations, writer.write)
  }

  source.close()
  writer.close()

  Comparator.compare(files("output"), files("expected"))
}
