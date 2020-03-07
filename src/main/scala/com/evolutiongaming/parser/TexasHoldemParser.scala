package com.evolutiongaming.parser
import com.evolutiongaming.model.{Card, Hand}

object TexasHoldemParser extends InputParser {
  override def parseLine(line: String): List[Hand] = {
    val input = line.trim.split(" ").toList
    val boardCards = Card.fromString(input.head)

    input.tail.map(Card.fromString).map(handCards => Hand(handCards, handCards ::: boardCards))
  }
}
