(ns aoc_2023_clojure.day-01)

(defn parse-digit
  "Parses the given puzzle digit and returns the corresponding number."
  [string]
  (let [digit (re-matches #"\d" string)]
    (if digit
      (parse-long digit)
      (case string
        "one" 1
        "two" 2
        "three" 3
        "four" 4
        "five" 5
        "six" 6
        "seven" 7
        "eight" 8
        "nine" 9))))

(defn get-calibration-value
  "Returns the calibration value given the calibration text."
  ([calibration-text]
   (get-calibration-value calibration-text "\\d"))
  ([calibration-text pattern]
   (get-calibration-value calibration-text pattern parse-long))
  ([calibration-text pattern-str parsing-fn]
   (let [pattern (re-pattern (str "(" pattern-str ")"))
         anti-pattern (re-pattern (str ".*(" pattern-str ")"))
         first-digit (last (re-find pattern calibration-text))
         second-digit (last (re-find anti-pattern calibration-text))]
     (+
       (* 10 (parsing-fn first-digit))
       (parsing-fn second-digit)))))

(defn solve-p01p1
  "Solves puzzle 1 part 1. https://adventofcode.com/2023/day/1"
  [calibration-document]
  (reduce +
          (for [calibration-text calibration-document]
            (get-calibration-value calibration-text))))

(defn solve-p01p2
  "Solves puzzle 1 part 2. https://adventofcode.com/2023/day/1#part2"
  [calibration-document]
  (reduce +
          (for [calibration-text calibration-document]
            (get-calibration-value calibration-text
                                   "one|two|three|four|five|six|seven|eight|nine|\\d"
                                   parse-digit))))
