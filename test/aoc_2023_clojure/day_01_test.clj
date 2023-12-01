(ns aoc-2023-clojure.day-01-test
  (:require [aoc-2023-clojure.core :refer :all]
            [aoc-2023-clojure.runner :as runner]
            [aoc_2023_clojure.day-01 :refer :all]
            [clojure.test :refer :all]))

(deftest test-parse-digit
  (runner/run-test-case parse-digit {["0"]     0
                                     ["1"]     1
                                     ["2"]     2
                                     ["3"]     3
                                     ["4"]     4
                                     ["5"]     5
                                     ["6"]     6
                                     ["7"]     7
                                     ["8"]     8
                                     ["9"]     9
                                     ["one"]   1
                                     ["two"]   2
                                     ["three"] 3
                                     ["four"]  4
                                     ["five"]  5
                                     ["six"]   6
                                     ["seven"] 7
                                     ["eight"] 8
                                     ["nine"]  9}))

(deftest test-get-calibration-value
  (runner/run-test-case get-calibration-value {["1abc2"]       12
                                               ["pqr3stu8vwx"] 38
                                               ["a1b2c3d4e5f"] 15
                                               ["treb7uchet"]  77}))

(deftest test-solve-p01p1
  (runner/run-test-case solve-p01p1 {[["1abc2"
                                       "pqr3stu8vwx"
                                       "a1b2c3d4e5f"
                                       "treb7uchet"]] 142
                                     [(read-input 1)] 55834}))

(def pattern "one|two|three|four|five|six|seven|eight|nine|\\d")

(deftest test-get-calibration-value-with-pattern-and-parsing-fn
  (runner/run-test-case get-calibration-value {["two1nine" pattern parse-digit]         29
                                               ["eightwothree" pattern parse-digit]     83
                                               ["abcone2threexyz" pattern parse-digit]  13
                                               ["xtwone3four" pattern parse-digit]      24
                                               ["4nineeightseven2" pattern parse-digit] 42
                                               ["zoneight234" pattern parse-digit]      14
                                               ["7pqrstsixteen" pattern parse-digit]    76
                                               ["45oneight" pattern parse-digit]        48}))

(deftest test-solve-p01p2
  (runner/run-test-case solve-p01p2 {[["two1nine"
                                       "eightwothree"
                                       "abcone2threexyz"
                                       "xtwone3four"
                                       "4nineeightseven2"
                                       "zoneight234"
                                       "7pqrstsixteen"]] 281
                                     [(read-input 1)]    53221}))
