(ns aoc-2023-clojure.day-03-test
  (:require [aoc-2023-clojure.core :refer :all]
            [aoc-2023-clojure.runner :as runner]
            [aoc_2023_clojure.day-03 :refer :all]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(def test-input (str/trim "
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598.."))

(def test-map (str/replace test-input "\n" ""))

(def test-schematic {:map          test-map
                     :rows         10
                     :cols         10
                     :first-row    '(0 1 2 3 4 5 6 7 8 9),
                     :first-column '(0 10 20 30 40 50 60 70 80 90),
                     :last-column  '(9 19 29 39 49 59 69 79 89 99),
                     :last-row     '(90 91 92 93 94 95 96 97 98 99)})

(deftest test-parse-schematic
  (runner/run-test-case parse-schematic {[test-input] test-schematic}))

(deftest test-get-symbol-positions
  (runner/run-test-case get-symbol-positions {[test-schematic]       [13 36 43 55 83 85]
                                              [test-schematic #"\*"] [13 43 85]}))

(deftest test-get-valid-directions
  (runner/run-test-case get-valid-directions {[test-schematic 0]  [:e :se :s]
                                              [test-schematic 1]  [:e :se :s :sw :w]
                                              [test-schematic 9]  [:s :sw :w]
                                              [test-schematic 10] [:n :ne :e :se :s]
                                              [test-schematic 89] [:n :s :sw :w :nw]
                                              [test-schematic 90] [:n :ne :e]
                                              [test-schematic 98] [:n :ne :e :w :nw]
                                              [test-schematic 99] [:n :w :nw]
                                              [test-schematic 11] [:n :ne :e :se :s :sw :w :nw]}))

(comment "
00  01  02  ..  08  09
10  11  12  ..  18  19
20  21  ..........  29
30  ..............  39
40  ..............  49
50  ..............  59
60  ..............  69
70  ..........  78  79
80  81  ..  87  88  89
90  91  ..  97  98  99
")
(deftest test-get-neighbours
  (runner/run-test-case get-neighbour-positions {[test-schematic 0]     [1 10 11]
                                                 [test-schematic 1]     [0 2 10 11 12]
                                                 [test-schematic 9]     [8 18 19]
                                                 [test-schematic 10]    [0 1 11 20 21]
                                                 [test-schematic 89]    [78 79 88 98 99]
                                                 [test-schematic 90]    [80 81 91]
                                                 [test-schematic 98]    [87 88 89 97 99]
                                                 [test-schematic 99]    [88 89 98]
                                                 [test-schematic []]    []
                                                 [test-schematic [0]]   [1 10 11]
                                                 [test-schematic [0 1]] [0 1 2 10 11 12]}))

(comment "
4  6  7  .  .  1  1  4  .  .
.  .  .  *  .  .  .  .  .  .
.  .  3  5  .  .  6  3  3  .
.  .  .  .  .  .  #  .  .  .
6  1  7  *  .  .  .  .  .  .
.  .  .  .  .  +  .  5  8  .
.  .  5  9  2  .  .  .  .  .
.  .  .  .  .  .  7  5  5  .
.  .  .  $  .  *  .  .  .  .
.  6  6  4  .  5  9  8  .  .
")
(deftest test-get-overlapping-number
  (runner/run-test-case get-overlapping-number {[{:cols 1 :map "1"} 0]             [0 1]
                                                [{:cols 2 :map "1."} 0]            [0 1]
                                                [{:cols 2 :map ".1"} 1]            [1 1]
                                                [{:cols 3 :map ".1."} 1]           [1 1]
                                                [{:cols 2 :map "11"} 0]            [0 11]
                                                [{:cols 2 :map "11"} 1]            [0 11]
                                                [{:cols 3 :map "11."} 0]           [0 11]
                                                [{:cols 3 :map "11."} 1]           [0 11]
                                                [{:cols 3 :map ".11"} 1]           [1 11]
                                                [{:cols 3 :map ".11"} 2]           [1 11]
                                                [{:cols 4 :map ".11."} 1]          [1 11]
                                                [{:cols 4 :map ".11."} 2]          [1 11]
                                                [{:cols 3 :map "2#..3#..2"} 0]     [0 2]
                                                [{:cols 3 :map "2#..3#..2"} 2]     nil
                                                [{:cols 3 :map "2#..3#..2"} 3]     nil
                                                [{:cols 3 :map "2#..3#..2"} 4]     [4 3]
                                                [{:cols 3 :map "2#..3#..2"} 7]     nil
                                                [{:cols 3 :map "2#..3#..2"} 8]     [8 2]

                                                ; 23 and 12 "abut"
                                                [{:cols 3 :map ".23\n12*\n.2."} 2] [1 23]

                                                [test-schematic 6]                 [5 114]
                                                [test-schematic 0]                 [0 467]}))

(deftest test-get-part-numbers
  (runner/run-test-case get-part-numbers
                        {[test-schematic [2 3 4 12 14 22 23 24 25 26 27
                                          32 33 34 35 37 42 44 45 46 47
                                          52 53 54 56 64 65 66 72 73 74
                                          75 76 82 84 86 92 93 94 95 96]]
                         (sort [467 35 633 617 592 755 664 598])}))

(deftest test-solve-p03-p1
  (runner/run-test-case solve-p03-p1 {; .#.
                                      ; .1.
                                      ; #..
                                      [".#.\n.1.\n#.."] 1

                                      ; 2#.
                                      ; .3#
                                      ; ..2
                                      ["2#.\n.3#\n..2"] 7

                                      ; .23
                                      ; 12*
                                      ; .2.
                                      [".23\n12*\n.2."] 37

                                      [test-input]      4361
                                      [(read-input 3)]  549908}))

(deftest test-solve-p03-p2
  (runner/run-test-case solve-p03-p2 {[test-input] 467835
                                      [(read-input 3)] 81166799}))
