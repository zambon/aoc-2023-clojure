(ns aoc-2023-clojure.day-04-test
  (:require [aoc-2023-clojure.core :refer :all]
            [aoc-2023-clojure.runner :as runner]
            [aoc_2023_clojure.day-04 :refer :all]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(def test-input (str/trim "
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"))

(deftest test-parse-card
  (runner/run-test-case parse-card {[[nil "1" "41 48 83 86 17" "83 86  6 31 17  9 48 53"]]
                                    {:id      1
                                     :winning '(41 48 83 86 17)
                                     :have    '(83 86 6 31 17 9 48 53)}}))

(deftest test-get-matches
  (runner/run-test-case get-matches {[{:winning '(41 48 83 86 17)
                                       :have    '(83 86 6 31 17 9 48 53)}]   [17 48 83 86]

                                     [{:winning '(13 32 20 16 61)
                                       :have    '(61 30 68 82 17 32 24 19)}] [32 61]

                                     [{:winning '(1 21 53 59 44)
                                       :have    '(69 82 63 72 16 21 14 1)}]  [1 21]

                                     [{:winning '(41 92 73 84 69)
                                       :have    '(59 84 76 51 58 5 54 83)}]  [84]

                                     [{:winning '(87 83 26 28 32)
                                       :have    '(88 30 70 12 93 22 82 36)}] []

                                     [{:winning '(31 18 13 56 72)
                                       :have    '(74 77 10 23 35 67 36 11)}] []}))

(deftest test-get-score
  (runner/run-test-case get-score {[[17 48 83 86]] 8
                                   [[32 61]]       2
                                   [[1 21]]        2
                                   [[84]]          1
                                   [[]]            0}))

(deftest test-solve-p04p1
  (runner/run-test-case solve-p04p1 {[test-input]     13
                                     [(read-input 4)] 18653}))

(deftest test-get-matches-by-id
  (runner/run-test-case get-matches-by-id {[[{:id      0
                                              :winning '(41 48 83 86 17)
                                              :have    '(83 86 6 31 17 9 48 53)}]]

                                           {0 {:matches   [17 48 83 86]
                                               :frequency 1}}


                                           [[{:id      0
                                              :winning '(41 92 73 84 69)
                                              :have    '(59 84 76 51 58 5 54 83)}
                                             {:id      1
                                              :winning '(87 83 26 28 32)
                                              :have    '(88 30 70 12 93 22 82 36)}]]

                                           {0 {:matches   [84]
                                               :frequency 1}
                                            1 {:matches   []
                                               :frequency 1}}}))

(deftest test-inc-vals
  (runner/run-test-case inc-vals {[{:a 1} [:a] 1]         {:a 2}
                                  [{:a 1 :b 2} [:a :b] 1] {:a 2 :b 3}}))

(deftest test-solve-p04p2
  (runner/run-test-case solve-p04p2 {[test-input]     30
                                     [(read-input 4)] 5921508}))

; 6653 too low
