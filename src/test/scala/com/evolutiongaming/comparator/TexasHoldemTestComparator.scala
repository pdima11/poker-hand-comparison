package com.evolutiongaming.comparator

import com.evolutiongaming.util.TestComparator
import org.scalatest.wordspec.AnyWordSpec

class TexasHoldemTestComparator extends AnyWordSpec {
  val fileName = "texas-25000"
  TestComparator.fromDataset(fileName, TexasHoldemComparator)
}
