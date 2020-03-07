# Poker Hand Comparison
Comparison of the strength of Texas Hold'em and Omaha Hands

## Run

`sbt run` or `sbt "run --texas-holdem"` - run comparison for texas hold'em hands

`sbt "run --omaha"` - run comparison for Omaha hands

## Input
```<5 board cards> <hand 1> <hand 2> <...> <hand N>```

where: 
* `<5 board cards>` is a 10 character string where each 2 characters encode a card 
* `<hand X>` is a 4 character string where each 2 characters encode a card, with 2 cards per hand
* <card> is a 2 character string with the first character representing the rank (one of "A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2") and the second character representing the suit (one of "h", "d", "c", "s") .
 
## Output

```<hand block 1> <hand block 2> <...> <hand block n>```
where:

* `<hand block 1>` is the hand block with the weakest value
* `<hand block 2>` is the hand block with the second weakest value
* `<hand block n>` is the hand block with the strongest value
* Each hand block consists of one or multiple hands (each represented by 4 character string with 2 characters to encode a card, with 2 cards per hand) with equal strength
* In case there are multiple hands with the same value on the same board they should be ordered alphabetically and separated by "=" signs
* The order of the cards in each hand should remain the same as in the input, e.g., don't reorder `2h3s` into `3s2h`.

## Examples 

### Texas Holdem
`sbt run`

```
//Input
4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d

//Output
Ac4d=Ad4s 5d6d As9s KhKd
```

`sbt run`
```
5c6dAcAsQs Ks4c KdJs 2hAh Kh4h Kc7h 6h7d 2cJc
2h5c8sAsKc Qs9h KdQh 3cKh Jc6s
3d4s5dJsQd 5c4h 7sJd KcAs 9h7h 2dTc Qh8c TsJc

2cJc Kh4h=Ks4c Kc7h KdJs 6h7d 2hAh
Jc6s Qs9h 3cKh KdQh
9h7h 2dTc KcAs 7sJd TsJc Qh8c 5c4h
```

### Omaha
`sbt "run --omaha"`

```
2d4d8sTcTs 7h3sAsJd 5h8cQs6h Ks3h2h2c 7dTh7c9c 5s3cTd9d 6s6c6dAc KhAd9sQh 4cKc2s5d
4s7d7hJdKd ThJs9d8h 4cQd8c2h 6s7cKh6c 8d5h2sAh Ks3s6h4h 2d3dQs2c
4h8s9cAsKh Qc5sAc2h 3h7s2s7h 3d5cTh6d KsAh4s4c QsJh8dJc Td5h2d8h 3s8c2c6s 9d4d7c9h

7h3sAsJd KhAd9sQh 4cKc2s5d 6s6c6dAc 5h8cQs6h 5s3cTd9d=7dTh7c9c Ks3h2h2c
8d5h2sAh 4cQd8c2h ThJs9d8h Ks3s6h4h 2d3dQs2c 6s7cKh6c
3d5cTh6d 3h7s2s7h 3s8c2c6s Td5h2d8h QsJh8dJc Qc5sAc2h KsAh4s4c 9d4d7c9h
```