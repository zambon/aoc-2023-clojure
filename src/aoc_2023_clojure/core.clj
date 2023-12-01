(ns aoc-2023-clojure.core
  (:require [aoc_2023_clojure.day-01 :refer :all]
            [clojure.string :as str]))

(defn read-input
  "Returns a collection of input lines from the puzzle input."
  [puzzle-number]
  (-> ["resources/puzzle-" (format "%02d" puzzle-number) "-input.txt"]
      str/join
      slurp
      str/trim
      str/split-lines))
