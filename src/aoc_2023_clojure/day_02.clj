(ns aoc_2023_clojure.day-02
  (:require [clojure.string :as str]))

(def cubes-in-play {:red   12
                    :green 13
                    :blue  14})


(defn get-game-matcher
  "Returns a game regex matcher."
  [games-str]
  (re-matcher #"Game\s(\d+):\s(.+)" games-str))

(defn get-pick-matches
  "Returns the pick matches."
  [pick-str]
  (re-matches #"(\d+)\s(red|green|blue)" pick-str))

(defn get-game-id
  "Returns the game ID."
  [id-str]
  (parse-long id-str))

(defn get-game-rounds
  "Returns a collection of round strings."
  [rounds-str]
  (str/split rounds-str #";\s?"))

(defn get-round-picks
  "Returns a collection of pick strings."
  [round-str]
  (str/split round-str #",\s?"))


(defn is-round-possible
  "Returns true if the round is possible."
  [round-string]
  (let [picks (get-round-picks round-string)]
    (loop [index 0
           pick-str (nth picks 0)]
      (if (nil? pick-str)
        true
        (let [pick-matches (get-pick-matches pick-str)
              color (keyword (nth pick-matches 2))
              amount (parse-long (nth pick-matches 1))]
          (if (> amount (get cubes-in-play color))
            false
            (recur (inc index)
                   (nth picks (inc index) nil))))))))

(defn is-game-possible
  "Returns true if the game is possible, considering the frequency map of the cubes in play."
  [rounds]
  (loop [index 0
         round (nth rounds 0)]
    (if (nil? round)
      true
      (if-not (is-round-possible round)
        false
        (recur (inc index)
               (nth rounds (inc index) nil))))))

(defn sum-results
  "docstring"
  [games result-summing-fn]
  (let [game-matcher (get-game-matcher games)]
    (loop [game (re-find game-matcher)
           result 0]
      (if (nil? game)
        result
        (let [id (get-game-id (nth game 1))
              rounds (get-game-rounds (nth game 2))]
          (recur (re-find game-matcher)
                 (+ result
                    (result-summing-fn id rounds))))))))

(defn solve-p02p1
  "Solves puzzle 2 part 1. https://adventofcode.com/2023/day/2"
  [games]
  (sum-results games
               (fn [id rounds]
                 (if (is-game-possible rounds) id 0))))

(defn get-round-min-frequency-map
  "Returns the minimum cube frequency map of the given round."
  [round-picks]
  (->> (for [pick-str round-picks
             :let [pick-matches (get-pick-matches pick-str)
                   color (keyword (nth pick-matches 2))
                   amount (parse-long (nth pick-matches 1))]]
         (repeat amount color))
       flatten
       frequencies
       (into {:red 0 :green 0 :blue 0})))

(defn get-game-min-frequency-map
  "Returns the minimum cube frequency map for the given collections of rounds of a game."
  [rounds]
  (->> (for [round-str rounds
             :let [picks (get-round-picks round-str)]]
         (get-round-min-frequency-map picks))
       (reduce (fn [m1 m2]
                 {:red   (max (get m1 :red) (get m2 :red))
                  :green (max (get m1 :green) (get m2 :green))
                  :blue  (max (get m1 :blue) (get m2 :blue))}))))

(defn get-game-power
  "Returns the power of the min cubes of the game."
  [rounds]
  (->> rounds
       get-game-min-frequency-map
       vals
       (reduce *)))

(defn solve-p02p2
  "Solves puzzle 2 part 2. https://adventofcode.com/2023/day/2#part2"
  [games]
  (sum-results games
               (fn [id rounds]
                 (get-game-power rounds))))
