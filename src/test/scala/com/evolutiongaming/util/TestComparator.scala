package com.evolutiongaming.util

import java.io.PrintWriter
import java.net.URL
import java.nio.file.Paths

import com.evolutiongaming.comparator.{Comparator, OmahaComparator, TexasHoldemComparator}
import com.evolutiongaming.parser.{OmahaInputParser, TexasHoldemParser}
import org.scalatest.matchers.should.Matchers._

import scala.io.{BufferedSource, Source}

object TestComparator {

  def fromDataset(datasetName: String, comparator: Comparator): Unit = {
    val files = Map(
      "input"    -> getClass.getResource(s"/data/input/$datasetName.in"),
      "output"   -> getClass.getResource(s"/data/output/$datasetName.out"),
      "expected" -> getClass.getResource(s"/data/expected/$datasetName.reference")
    )

    val source: BufferedSource = Source.fromURL(files("input"))
    val writer = new PrintWriter(Paths.get(files("output").toURI).toFile)

    for (line <- source.getLines()) {
      val combinations = comparator match {
        case OmahaComparator => OmahaComparator.compare(OmahaInputParser.parseLine(line))
        case TexasHoldemComparator => TexasHoldemComparator.compare(TexasHoldemParser.parseLine(line))
      }

      OutputWriter.write(combinations, writer.write)
    }

    source.close()
    writer.close()

    compare(files("output"), files("expected"))
  }

  def compare(actual: URL, expected: URL): Unit = {
    val actualSource = Source.fromURL(actual)
    val expectedSource = Source.fromURL(expected)

    for (
      (actual, expected) <- actualSource.getLines().zip(expectedSource.getLines())
    ) actual should be(expected)

  }
}
