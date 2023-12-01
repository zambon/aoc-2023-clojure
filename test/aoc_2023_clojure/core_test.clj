(ns aoc-2023-clojure.core-test
  (:require [aoc-2023-clojure.core :refer :all]
            [aoc-2023-clojure.runner :as runner]
            [clojure.test :refer :all]))

(deftest test-read-input
  (runner/run-test-case read-input {[0] ["line 1" "line 2" "line 3"]}))
