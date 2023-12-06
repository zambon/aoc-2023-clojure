(ns aoc_2023_clojure.day-03
  (:require [clojure.string :as str]))

(def dirs
  "The cardinal directions."
  [:n :ne :e :se :s :sw :w :nw])

(defn incrementer-by-dir
  "Given the number of columns in a square schematic, returns a map of increment functions by
  their corresponding direction. This can be used to navigate neighbours of a position.

  Direction  Increment
  N          - cols
  NE         - cols + 1
  E          + 1
  SE         + cols + 1
  S          + cols
  SW         + cols - 1
  W          - 1
  NW         - cols - 1"
  [cols]
  {:n  (fn [x] (- x cols))
   :ne (fn [x] (+ (- x cols) 1))
   :e  (fn [x] (+ x 1))
   :se (fn [x] (+ (+ x cols) 1))
   :s  (fn [x] (+ x cols))
   :sw (fn [x] (- (+ x cols) 1))
   :w  (fn [x] (- x 1))
   :nw (fn [x] (- (- x cols) 1))})

(defn parse-schematic
  "Returns a map of the trimmed schematic and its boundaries."
  [input]
  (let [cols (str/index-of input "\n")
        rows (+ 1 (count (re-seq #"\n" input)))
        tm-schematic (str/replace input "\n" "")
        first-col-range (->> 0
                             (iterate (fn [x] (+ rows x)))
                             (take rows))]
    {:map          tm-schematic
     :rows         rows
     :cols         cols
     :first-row    (range 0 cols)
     :first-column first-col-range
     :last-column  (->> (- cols 1)
                        (iterate (fn [x] (+ cols x)))
                        (take rows))
     :last-row     (range (last first-col-range)            ; todo add inc-by-dir?
                          (+ cols (last first-col-range)))}))

(defn get-symbol-positions
  "Returns a collection of symbol char indices."
  ([schematic]
   (get-symbol-positions schematic #"[\W&&[^.\n]]"))
  ([schematic re-symbol]
   (let [symbol-matcher (re-matcher re-symbol (get schematic :map))]
     (loop [result []
            match (re-find symbol-matcher)]
       (if (nil? match)
         result
         (recur (conj result (.start symbol-matcher))
                (re-find symbol-matcher)))))))

(defn get-valid-directions
  "Returns the valid (i.e., within bounds) cardinal directions from the position.

  Direction  Invalid for
  N          first row
  NE         first row, last column
  E          last column
  SE         last column, last row
  S          last row
  SW         first column, last row
  W          first column
  NW         first row, first column"
  [schematic position]
  (remove (fn [dir]
            (let [in-first-row (some #(= position %) (get schematic :first-row))
                  in-last-column (some #(= position %) (get schematic :last-column))
                  in-last-row (some #(= position %) (get schematic :last-row))
                  in-first-column (some #(= position %) (get schematic :first-column))]
              (case dir
                :n in-first-row
                :ne (or in-first-row in-last-column)
                :e in-last-column
                :se (or in-last-column in-last-row)
                :s in-last-row
                :sw (or in-first-column in-last-row)
                :w in-first-column
                :nw (or in-first-row in-first-column))))
          dirs))

(defn get-neighbour-positions
  "Returns a collection of neighbouring char indices given positions."
  [schematic positions]
  (if (coll? positions)
    (if (empty? positions)
      []
      (-> (for [pos positions]
            (get-neighbour-positions schematic pos))
          flatten
          distinct
          sort))
    (let [position positions                                ; for readability
          valid-directions (get-valid-directions schematic position)
          inc-by-dir (incrementer-by-dir (get schematic :cols))]
      (-> (for [dir valid-directions]
            (apply (get inc-by-dir dir) [position]))
          sort))))

(defn get-overlapping-number
  "Returns a vector containing the starting position of the number and the number itself.
  If the position does not contain a number, returns nil."
  [schematic position]
  (let [map (get schematic :map)
        cols (get schematic :cols)
        to-number (fn [pos] (-> (nth map pos nil)
                                str
                                parse-long))
        get-row-start (fn [pos]
                        (* (unchecked-divide-int pos cols) cols))
        get-row-end (fn [pos]
                      (-> pos
                          get-row-start
                          (+ cols)
                          (- 1)))]
    (when (re-matches #"\d" (subs map position (inc position)))
      (let [min-index (get-row-start position)
            max-index (get-row-end position)]
        (loop [index position]
          (if (or
                (not (number? (to-number index)))
                (< index min-index))
            [(inc index) (->> (subs map (inc index) (inc max-index))
                              (re-find #"\d+")
                              parse-long)]
            (recur (dec index))))))))

(defn get-part-numbers
  "Returns the part numbers at the given positions, if they exist."
  [schematic positions]
  (->> (for [position positions
             :let [schematic-map (get schematic :map)]
             :when (-> schematic-map
                       (nth position)
                       str
                       parse-long
                       number?)]
         (get-overlapping-number schematic position))
       distinct
       (map (fn [pair]
              (nth pair 1)))
       sort))

(defn solve-p03-p1
  "Solves puzzle 3 part 1. https://adventofcode.com/2023/day/3"
  [input]
  (let [schematic (parse-schematic input)]
    (->> schematic
         get-symbol-positions
         (get-neighbour-positions schematic)
         (get-part-numbers schematic)
         (reduce +))))

(defn solve-p03-p2
  "Solves puzzle 3 part 2. https://adventofcode.com/2023/day/3#part2"
  [input]
  (let [schematic (parse-schematic input)
        stars (get-symbol-positions schematic #"\*")]
    (->> (for [star stars
               :let [part-numbers (->> star
                                       (get-neighbour-positions schematic)
                                       (get-part-numbers schematic))]
               :when (= (count part-numbers) 2)]
           (*
             (nth part-numbers 0)
             (nth part-numbers 1)))
         (reduce +))))
