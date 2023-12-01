(ns aoc-2023-clojure.runner
  (:require [clojure.test :refer :all]))

(defn run-test-case
  "Asserts the outputs of the function with the given inputs match the corresponding expected output."
  [fn-under-test expected-output-by-input]
  (testing "input is not empty"
           (is (not (empty? expected-output-by-input))))
  (doall
    (for [entry expected-output-by-input
          :let [input (key entry)
                expected-output (val entry)]]
      (testing (str "input: '" input "' expected output: '" expected-output "'")
               (is (= (apply fn-under-test input) expected-output))))))
