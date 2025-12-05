(ns advent-of-code-2023.three
  (:require [clojure.string :as str]))

(def input-data
  (str/split (slurp "src/advent_of_code_2023/three.txt") #"\n"))

(defn getChar [data pos]
  (let [line (get data (:x pos) ".")
        char (get line (:y pos) ".")]
    (str char)))cid

(defn genNeighbours [pos]
  (let [axis (fn [x] [(dec x) x (inc x)])
        byX (map (fn [x] {:x x})(axis (:x pos)))
        byY (map (fn [y] {:y y}) (axis (:y pos)))]
    (remove #{pos}
            (mapcat (fn [x] (map (fn [y] (merge x y)) byY))
                          byX))))

(defn getInt [x]
  (try (Integer/parseInt x) (catch Throwable t nil)))

(defn adj? [data pos]
  (let [numOrDot? (fn [c] (or (getInt c) (= "." c)))
        neighbours (genNeighbours pos)
        chars (map (fn [x] (getChar data x)) neighbours)]
    (not (every? numOrDot? chars))))

(defn parseRow [data xrow idx]
  (remove nil? (map-indexed (fn [idy y]
                              (let [i {:x idx :y idy}]
                                (if (adj? data i) i))) xrow)))
  
(defn allAdj [data]
  (flatten (map-indexed (fn [idx xrow] (parseRow data xrow idx)) data)))

(defn extractNumbers [data idxs]
  (let [grouped (group-by :x idxs)
        onlyYs (map (fn [gp] {:x (first gp) :ys (map :y (second gp))}) grouped)
        ]
    onlyYs))

