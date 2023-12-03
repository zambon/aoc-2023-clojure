(ns aoc-2023-clojure.day-02-test
  (:require [aoc-2023-clojure.core :refer :all]
            [aoc-2023-clojure.runner :as runner]
            [aoc_2023_clojure.day-02 :refer :all]
            [clojure.test :refer :all]))

(deftest test-if-round-possible
  (runner/run-test-case is-round-possible {["1 red"]                   true
                                           ["99 red, 1 green"]         false
                                           ["1 red, 2 green"]          true
                                           ["1 red, 99 green, 2 blue"] false}))


(deftest test-is-game-possible
  (runner/run-test-case is-game-possible {[["3 blue", "4 red"]]              true
                                          [["1 red", "2 green", "6 blue"]]   true
                                          [["2 green"]]                      true
                                          [["1 blue", "2 green"]]            true
                                          [["3 green", "4 blue", "1 red"]]   true
                                          [["1 green", "1 blue"]]            true
                                          [["8 green", "6 blue", "20 red"]]  false
                                          [["5 blue", "4 red", "13 green"]]  true
                                          [["5 green", "1 red"]]             true
                                          [["1 green", "3 red", "6 blue"]]   true
                                          [["3 green", "6 red"]]             true
                                          [["3 green", "15 blue", "14 red"]] false
                                          [["6 red", "1 blue", "3 green"]]   true
                                          [["2 blue", "1 red", "20 green"]]  false}))

(def games "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(deftest test-solve-p02p1
  (runner/run-test-case solve-p02p1 {[games]          8
                                     [(read-input 2)] 2600}))

(deftest test-get-round-min-frequency-map
  (runner/run-test-case get-round-min-frequency-map
                        {[["1 green"]]                  {:red 0 :green 1 :blue 0}
                         [["3 blue" "4 red"]]           {:red 4 :green 0 :blue 3}
                         [["1 red" "2 green" "6 blue"]] {:red 1 :green 2 :blue 6}}))

(deftest test-get-game-min-frequency-map
  (->> {[["3 blue, 4 red" "1 red, 2 green, 6 blue" "2 green"]]
        {:red 4 :green 2 :blue 6}

        [["1 blue, 2 green" "3 green, 4 blue, 1 red" "1 green, 1 blue"]]
        {:red 1 :green 3 :blue 4}

        [["8 green, 6 blue, 20 red" "5 blue, 4 red, 13 green" "5 green, 1 red"]]
        {:red 20 :green 13 :blue 6}

        [["1 green, 3 red, 6 blue" "3 green, 6 red" "3 green, 15 blue, 14 red"]]
        {:red 14 :green 3 :blue 15}

        [["6 red, 1 blue, 3 green" "2 blue, 1 red, 2 green"]]
        {:red 6 :green 3 :blue 2}}

       (runner/run-test-case get-game-min-frequency-map)))

(deftest test-get-game-power
  (runner/run-test-case get-game-power {[["3 blue", "4 red"]]              0 ;12
                                        [["1 red", "2 green", "6 blue"]]   12
                                        [["2 green"]]                      0 ;2
                                        [["1 blue", "2 green"]]            0 ;2
                                        [["3 green", "4 blue", "1 red"]]   12
                                        [["1 green", "1 blue"]]            0 ;1
                                        [["8 green", "6 blue", "20 red"]]  960
                                        [["5 blue", "4 red", "13 green"]]  260
                                        [["5 green", "1 red"]]             0 ;5
                                        [["1 green", "3 red", "6 blue"]]   18
                                        [["3 green", "6 red"]]             0 ;18
                                        [["3 green", "15 blue", "14 red"]] 630
                                        [["6 red", "1 blue", "3 green"]]   18
                                        [["2 blue", "1 red", "20 green"]]  40}))

(deftest test-solve-p02p2
  (runner/run-test-case solve-p02p2 {[games] 2286
                                     [(read-input 2)] 86036}))
