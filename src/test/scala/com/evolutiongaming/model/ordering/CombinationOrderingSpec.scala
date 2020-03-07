package com.evolutiongaming.model.ordering

import com.evolutiongaming.model._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec


class CombinationOrderingSpec extends AnyWordSpec {
  private val hand = Hand(Card.fromString("3s4d"), Card.fromString("3s4d"))
  private val defaultBoard: List[Card] = Card.fromString("3s5d7cJd9s")

  private def buildCardsCombination(combinations: List[String], hands: List[String]) = {
    combinations.map(Card.fromString)
      .zip(hands.map(c => Hand(Card.fromString(c), defaultBoard ::: Card.fromString(c))))
  }

  "CombinationOrdering" should {
    "provide right ordering" when {
      "multiple High Cards" in {
        val highCards = List("5s", "3d", "7c", "4h").map(str => HighCard(Card.fromString(str), hand))
        val result = highCards.sorted(CombinationOrdering)

        result.map(hc => hc.result.head.toString) should be(List( "3d", "4h", "5s", "7c"))
      }

      "multiple Pairs" in {
        val combinations = List("KsKd", "8d8s", "KcKh", "AdAs", "8h8c", "JdJc", "3s3d")
        val hands = List("AsAd", "5dTs", "QcQh", "2d2s", "2h9c", "4d4c", "JsJh")

        val pairs = buildCardsCombination(combinations, hands).map{ case (comb, hand) => Pair(comb, hand) }
        val result = pairs.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("3s3d", "8h8c", "8d8s", "JdJc", "KcKh", "KsKd", "AdAs"))
        result.map(pair => pair.hand.toString) should be(List("JsJh", "2h9c", "5dTs", "4d4c", "QcQh", "AsAd", "2d2s"))
        CombinationOrdering.compare(pairs(1), pairs(4)) should be(1)
        CombinationOrdering.compare(pairs.head, pairs(2)) should be(1)
      }

      "multiple Two Pairs" in {
        val combinations = List("QsQdJdJc", "3c3h5s5h", "KsKd6d6c", "2d2c5d5c", "KcKh6s6h")
        val hands = List("AsAd", "7d7s", "Td4s", "9h9d", "Qh2c")

        val twoPairs = buildCardsCombination(combinations, hands).map{ case (comb, hand) => TwoPairs(comb, hand) }
        val result = twoPairs.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("2d2c5d5c", "3c3h5s5h", "QsQdJdJc", "KsKd6d6c", "KcKh6s6h"))
        result.map(pair => pair.hand.toString) should be(List("9h9d", "7d7s", "AsAd", "Td4s", "Qh2c"))
        CombinationOrdering.compare(twoPairs(1), twoPairs(3)) should be(1)
        CombinationOrdering.compare(twoPairs(2), twoPairs(4)) should be(-1)
      }

      "multiple Three Of Kinds" in {
        val combinations = List("KsKdKh", "5d5s5c", "AcAhAs", "7s7d7h" , "KsKdKc")
        val hands = List("Td3s", "JsJd", "2h2c", "4d4s", "Qh9d")

        val threeOfKinds = buildCardsCombination(combinations, hands).map{ case (comb, hand) => ThreeOfKind(comb, hand) }
        val result = threeOfKinds.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("5d5s5c", "7s7d7h", "KsKdKh", "KsKdKc", "AcAhAs"))
        result.map(pair => pair.hand.toString) should be(List("JsJd", "4d4s", "Td3s", "Qh9d", "2h2c"))
        CombinationOrdering.compare(threeOfKinds.head, threeOfKinds.last) should be(-1)
      }

      "multiple Straights" in {
        val combinations = List("6s3h2d4h5d", "AdQcKsJsTs", "4s5c6h7c8s")
        val hands = List("Td3s", "JsJd", "2h2c")

        val straights = buildCardsCombination(combinations, hands).map{ case (comb, hand) => Straight(comb, hand) }
        val result = straights.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("6s3h2d4h5d", "4s5c6h7c8s", "AdQcKsJsTs"))
        result.map(pair => pair.hand.toString) should be(List("Td3s", "2h2c", "JsJd"))
      }

      "multiple Flushs" in {
        val combinations = List("Ah2h3h4h5h", "6s3s2s4s5s", "3c8c9c4cQc", "9d2d6d5dTd")
        val hands = List("2h3h", "2s4s", "9c4c", "2d6d")

        val flushs = buildCardsCombination(combinations, hands).map{ case (comb, hand) => Flush(comb, hand) }
        val result = flushs.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("6s3s2s4s5s", "9d2d6d5dTd", "3c8c9c4cQc", "Ah2h3h4h5h"))
        result.map(pair => pair.hand.toString) should be(List("2s4s", "2d6d", "9c4c", "2h3h"))
      }

      "multiple Full Houses" in {
        val combinations = List("7s7d7hQdQs", "AcAhAs2d2s", "KsKdKh9h9s", "5d5s5cAcAd", "KsKdKcTsTd")
        val hands = List("QdQs", "AhAs", "Kh9h", "5cAc", "KcTs")

        val fullHouses = buildCardsCombination(combinations, hands).map{ case (comb, hand) => FullHouse(comb, hand) }
        val result = fullHouses.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("5d5s5cAcAd", "7s7d7hQdQs", "KsKdKh9h9s", "KsKdKcTsTd", "AcAhAs2d2s"))
        result.map(pair => pair.hand.toString) should be(List("5cAc", "QdQs", "Kh9h", "KcTs", "AhAs"))
        CombinationOrdering.compare(fullHouses(2), fullHouses(4)) should be(-1)
      }

      "multiple Four Of Kinds" in {
        val combinations = List("KsKdKhKc", "5d5s5c5h", "7s7d7h7c")
        val hands = List("Td3s", "JsJd", "4d4s")

        val fourOfKinds = buildCardsCombination(combinations, hands).map{ case (comb, hand) => FourOfKind(comb, hand) }
        val result = fourOfKinds.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("5d5s5c5h", "7s7d7h7c", "KsKdKhKc"))
        result.map(pair => pair.hand.toString) should be(List("JsJd", "4d4s", "Td3s"))
      }

      "multiple Straight Flushs" in {
        val combinations = List("8c7c6c5c4c", "6s5s4s3s2s", "AdKdQdJdTd")
        val hands = List("JsJd", "Td3s", "2h2c")

        val straightFlushs = buildCardsCombination(combinations, hands).map{ case (comb, hand) => StraightFlush(comb, hand) }
        val result = straightFlushs.sorted(CombinationOrdering)

        result.map(pair => pair.result.map(_.toString).mkString) should be(List("6s5s4s3s2s", "8c7c6c5c4c", "AdKdQdJdTd"))
        result.map(pair => pair.hand.toString) should be(List("Td3s", "JsJd", "2h2c"))
      }
    }

  }

}
