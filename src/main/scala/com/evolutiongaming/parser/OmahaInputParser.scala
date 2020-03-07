package com.evolutiongaming.parser

import com.evolutiongaming.model.{Card, Hand}

object OmahaInputParser extends InputParser {
  override def parseLine(line: String): List[Hand] = {
    val input: List[String] = line.trim.split(" ").toList

    val boardCards: List[Card] = Card.fromString(input.head)
    val hands: List[List[Card]] = input.tail.map(Card.fromString)

    for {
      hand <- hands
      handSubset <- hand.combinations(2)
      boardSubset <- boardCards.combinations(3)
    } yield Hand(hand, handSubset ::: boardSubset)
  }
}
