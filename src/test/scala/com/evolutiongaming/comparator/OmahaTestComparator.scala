package com.evolutiongaming.comparator

import com.evolutiongaming.util.TestComparator
import org.scalatest.wordspec.AnyWordSpec

class OmahaTestComparator extends AnyWordSpec {
  val fileName = "omaha-10000"
  TestComparator.fromDataset(fileName, OmahaComparator)
}
