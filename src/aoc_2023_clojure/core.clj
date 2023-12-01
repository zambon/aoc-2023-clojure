(ns aoc-2023-clojure.core
  (:require [clojure.string :as str]))

(defn read-input
  "Returns a collection of input lines from the puzzle input."
  [puzzle-number]
  (-> ["resources/puzzle-" (format "%02d" puzzle-number) "-input.txt"]
      str/join
      slurp
      str/trim
      str/split-lines))
