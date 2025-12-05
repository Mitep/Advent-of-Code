(ns advent-of-code-2023.thirteen.code
  (:require [clojure.string :as str])
  (:require [clojure.walk :as walk]))

(defn input []
  (str/split (slurp "src/advent_of_code_2023/thirteen/input.txt") #"\n\n"))

(defn transpose [mat]
  (->> mat
       (map char-array)
       (map seq)
       (apply mapv vector)
       (map #(apply str %))))

(defn check-1d [mat]
  (->> mat
       (partition 2 1)
       (map-indexed (fn [idx itm]
                      (if (apply = itm) (inc idx) 0)))
       (reduce +)))

(defn check [mat inv]
  (let [reg (str/split-lines mat)]
    (check-1d (if inv (transpose reg) reg))))

(let [in (input)
      r (map #(check % false) in)
      i (map #(check % true) in)]
  (+ (* 100 (reduce + r)) (reduce + i)))
65212
     
