(ns aoc-2023-clojure.core-test
  (:require [aoc-2023-clojure.core :refer :all]
            [clojure.test :refer :all]))

(deftest test-read-input
  (testing "read-input"
    (is (= (read-input 0) ["line 1" "line 2" "line 3"]))))
