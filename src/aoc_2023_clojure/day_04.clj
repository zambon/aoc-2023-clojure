(ns aoc_2023_clojure.day-04)

(def line-matcher
  #"Card\s+(?<card>\d+):\s+(?<winning>(?:\d+\s*)+)\s[|]\s+(?<have>(?:\d+\s*)+)")

(defn parse-card
  "Returns a map representation of the card."
  [matches]
  {:id      (parse-long (nth matches 1))
   :winning (->> (re-seq #"\d+" (nth matches 2))
                 (map parse-long))
   :have    (->> (re-seq #"\d+" (nth matches 3))
                 (map parse-long))})

(defn get-matches
  "Returns the matches of a card."
  [card]
  (let [winning-numbers (sort (get card :winning))
        have-numbers (sort (get card :have))
        max-winning-index (- (count winning-numbers) 1)
        max-have-index (- (count have-numbers) 1)]
    (loop [matches []
           winning-index 0
           have-index 0]
      (if (or
            (> winning-index max-winning-index)
            (> have-index max-have-index))
        matches
        (let [winning-number (nth winning-numbers winning-index)
              have-number (nth have-numbers have-index)]
          (cond
            (= winning-number have-number) (recur (conj matches winning-number)
                                                  (inc winning-index)
                                                  (inc have-index))
            (> winning-number have-number) (recur matches
                                                  winning-index
                                                  (inc have-index))
            (< winning-number have-number) (recur matches
                                                  (inc winning-index)
                                                  have-index)))))))

(defn get-score
  "Returns the card score given its matches."
  [matches]
  (if (= (count matches) 0)
    0
    (reduce * (repeat
                (- (count matches) 1)
                2))))

(defn solve-p04p1
  "Solves puzzle 4 part 1. https://adventofcode.com/2023/day/4"
  [input]
  (->> input
       (re-seq line-matcher)
       (map parse-card)
       (map get-matches)
       (map get-score)
       (reduce +)))

(defn get-matches-by-id
  "Returns matches keyed by their corresponding card."
  [cards]
  (into (sorted-map)
        (for [card cards]
          [(get card :id) {:matches   (get-matches card)
                           :frequency 1}])))

(defn inc-vals
  "Given a map and a collection of keys, increment their values by x."
  [m ks x]
  (loop [index 0
         map m]
    (let [key (nth ks index nil)]
      (if (nil? key)
        map
        (recur (inc index)
               (update map key (fn [val]
                                 (+ val x))))))))

(defn solve-p04p2
  "Solves puzzle 4 part 1. https://adventofcode.com/2023/day/4#part2"
  [input]
  (let [cards (->> input
                   (re-seq line-matcher)
                   (map parse-card))
        matches-by-id (get-matches-by-id cards)
        ids (keys matches-by-id)
        last-id (last ids)]
    (loop [card-ids ids
           fmap (frequencies card-ids)
           num-cards (count card-ids)]
      (if (nil? (first card-ids))
        num-cards
        (let [card (first card-ids)
              card-freq (get fmap card)
              num-matches (-> matches-by-id
                              (get card)
                              (get :matches)
                              count)
              copies (range
                       (inc card)
                       (inc (min
                              (+ card num-matches)
                              last-id)))
              updated-fmap (inc-vals fmap copies card-freq)
              total-copies (* card-freq (count copies))]
          (recur (next card-ids)
                 updated-fmap
                 (+ num-cards total-copies)))))))
