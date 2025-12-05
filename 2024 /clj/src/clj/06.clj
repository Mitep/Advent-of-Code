(ns clj.05
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [clj.core :refer :all]))

(def input (slurp "../inputs/06.in"))
(def input
"....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...")

(defn find-guard
  "Finds guard in territory and returns [x y g] vector
     If guars is not found [] will be returned"
  [area]
  (defn ctns [s] (re-find #"\^|>|<|v" s))
  (defn find-with-idx [s] (flatten (keep-indexed (fn [i x] (if (ctns x) [i x])) s)))
  (let [[xi row] (find-with-idx area)
        [yi grd] (find-with-idx (str/split (or row "") #""))]
    (filter #(not (nil? %)) [xi yi grd])))

(defn look-forward [area x y dir]
  (try
  (case dir
    "^" (nth (nth area (- x 1)) y)
    ">" (nth (nth area x) (+ y 1 ))
    "<" (nth (nth area x) (- y 1 ))
    "v" (nth (nth area (+ x 1)) y))
  (catch IndexOutOfBoundsException e nil)))

(defn replace-char-in-list [lst x y replacement]
  (map-indexed
   (fn [i s]
     (if (= i x)
       (str (subs s 0 y) replacement (subs s (inc y)))
       s))
   lst))

(defn move-guard
  "Returns new area with guard moved to new position.
     Old position is marked with X."
  [area x y dir]
  (replace-char-in-list
   (case dir
     "^" (replace-char-in-list area (- x 1) y "^")
     "<" (replace-char-in-list area x (- y 1) "<")
     ">" (replace-char-in-list area x (+ y 1) ">")
     "v" (replace-char-in-list area (+ x 1) y "v"))
   x y "X"))

(defn rotate-guard
  "Returns new area with guard moved to new position.
     Old position is marked with X."
  [area x y dir]
  (case dir
    "^" (replace-char-in-list area x y ">")
    "<" (replace-char-in-list area x y "^")
    ">" (replace-char-in-list area x y "v")
    "v" (replace-char-in-list area x y "<")))

(defn first-part [input]
  (let [area (atom (str/split-lines input))
        guard (atom (find-guard @area))]
    (while (not (empty? @guard))
      (let [[x y dir] @guard
            forward (look-forward @area x y dir)]
        (if (= forward \#)
          (swap! area rotate-guard x y dir)
          (swap! area move-guard x y dir))

        (reset! guard (find-guard @area))))
    (reduce + (map (fn [row] (count (filter #(= % \X) row))) @area))))

(testing "First part is 41"
  (is (= (first-part input) 41)))

;; Part Two
(defn get-path
  "consumes mapped area
  returns path and boolean representing if path is obstructed"
  [area]
  
  
  )


(map %() (get-path )))



1. get all paths without obstruction
2. put obstruction on path
3. check if new map is obstructed

