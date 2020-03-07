package com.evolutiongaming.parser

import com.evolutiongaming.model.Hand

trait InputParser {
  def parseLine(line: String): List[Hand]
}
