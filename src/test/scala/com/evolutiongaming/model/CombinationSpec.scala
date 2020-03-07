package com.evolutiongaming.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CombinationSpec extends AnyWordSpec {

  private def buildCombinations(board: String, hands: String): List[Combination] = {
    val boardCards = Card.fromString(board)
    val handsCards = hands.split(" ").map(Card.fromString).map(h => Hand(h, h ::: boardCards))

    handsCards.map(Combination.fromHand).toList
  }

  "Combination" should {
    "HighCard" in {
      val combinations = buildCombinations("Ts5d2c8c4c", "3sJd AcKs 3h9s 7dKc 9c7h")

      combinations.forall(_.isInstanceOf[HighCard]) should be(true)
      combinations.map(_.result.head.value) should be(List('J', 'A', 'T', 'K', 'T'))
    }

    "Pair" in {
      val combinations = buildCombinations("Ts5d6d9c7c", "3s9d 6c4s JdJs 9h3h Qc7d")

      combinations.forall(_.isInstanceOf[Pair]) should be(true)
      combinations.map(_.result.head.value) should be(List('9', '6', 'J', '9', '7'))
    }

    "TwoPair" in {
      val combinations = buildCombinations("Ts5d5c9c7c", "3sTd 6c4s JdJs 9h3h 9d7d")

      combinations.count(_.isInstanceOf[TwoPairs]) should be(4)
      combinations.map(_.result.map(_.value).mkString) should be(List("TT55", "55", "JJ55", "9955", "9977"))
    }

    "ThreeOfKind" in {
      val combinations = buildCombinations("9s5d5c8h7c", "5sTd 6c4s JdJs 9h9d 7s7d 3d5h 2s2c")

      combinations.count(_.isInstanceOf[ThreeOfKind]) should be(2)
      combinations.map(_.result.map(_.value).mkString) should be(List("555", "98765", "JJ55", "99955", "77755", "555", "5522"))
    }

    "Straight" in {
      val combinations = buildCombinations("As2d3cKhQc", "4s5d 6c4s JdJs ThJd AcAd 5c4h 2s2c")

      combinations.count(_.isInstanceOf[Straight]) should be(3)
      combinations.map(_.result.map(_.value).mkString) should be(List("5432A", "A", "JJ", "AKQJT", "AAA", "5432A", "222"))
    }

    "Flush" in {
      val combinations = buildCombinations("8c7c7d9cTc", "5c6d 7h7s QcAc 2c3d JdQs 5sKc 9d9h")

      combinations.count(_.isInstanceOf[Flush]) should be(4)
      combinations.map(_.result.map(_.value).mkString) should be(List("T9875", "7777", "AQT98", "T9872", "QJT98", "KT987", "99977"))
    }

    "FullHouse" in {
      val combinations = buildCombinations("3c7c7d9c3d", "9h9d 7h7s 9c4s 2c3s 3hAd 5sKc 6cTc")

      combinations.count(_.isInstanceOf[FullHouse]) should be(3)
      combinations.map(_.result.map(_.value).mkString) should be(List("99977", "7777", "9977", "33377", "33377", "7733", "T9763"))
    }

    "FourOfKind" in {
      val combinations = buildCombinations("9s5d5c9h7c", "5s5h 6c4s JdJs 9c9d 7s7d 3d8h TsTc")

      combinations.count(_.isInstanceOf[FourOfKind]) should be(2)
      combinations.map(_.result.map(_.value).mkString) should be(List("5555", "9955", "JJ99", "9999", "77799", "9955", "TT99"))
    }

    "StraightFlush" in {
      val combinations = buildCombinations("As2s3sKsQs", "4s5s 6c4s JdJs TsJs TcJd")

      combinations.count(_.isInstanceOf[StraightFlush]) should be(2)
      combinations.map(_.result.map(_.value).mkString) should be(List("5432A", "AKQ43", "AKQJ3", "AKQJT", "AKQ32"))
    }
  }

}
