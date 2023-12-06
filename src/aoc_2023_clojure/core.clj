(ns aoc-2023-clojure.core
  (:require [aoc_2023_clojure.day-01 :refer :all]
            [aoc_2023_clojure.day-02 :refer :all]
            [aoc_2023_clojure.day-03 :refer :all]
            [clojure.string :as str]))

(defn read-input
  "Returns the whole puzzle input as a string."
  [puzzle-number]
  (-> ["resources/puzzle-" (format "%02d" puzzle-number) "-input.txt"]
      str/join
      slurp
      str/trim))

(defn read-lines
  "Returns a collection of input lines from the puzzle input."
  [puzzle-number]
  (-> (read-input puzzle-number)
      str/split-lines))
