(ns advent-of-code-2023.nineteen
  (:require [clojure.string :as str])
  (:require [clojure.walk :as walk]))

(defn load-input [] (slurp "src/advent_of_code_2023/nineteen.txt"))

(defn parse-p [p]
  (->> p
       (re-find #"\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}")
       (rest)
       (map (fn [x] (Integer/parseInt x)))
       (zipmap [:x :m :a :s])))

(defn parse-w [w]
  (defn parse-flow [flow]
    (let [f (rest (re-find #"([a-z])([<|>])(\d+):([a-zA-Z]+)" flow))]
      (if (= f ())
        (fn [in] (keyword flow))
        (let [[key ltgt value next] f
              k (keyword key)
              o (resolve (symbol ltgt))
              v (Integer/parseInt value)
              n (keyword next)]
          (fn [in] (if (o (k in) v) n))))))
  (let [res (re-find #"(\S+)\{(.*)\}" w)
      [name raw-flow] (rest res)
      flow (map parse-flow (str/split raw-flow #","))]
  {(keyword name) flow}))

(defn parse-input [input]
  (let [[w p] (str/split input #"\n\n")]
    {:workflows (reduce merge (map #(parse-w %) (str/split-lines w)))
     :parts (map #(parse-p %) (str/split-lines p))}))

(defn run-on-part [part workflows]
  (defn loop [w]
    (let [r (first (filter identity (map #(% part) w)))]
      (case r
        :A part
        :R nil
        (loop (r workflows)))))
  (loop (:in workflows)))

(defn run [input] (map #(run-on-part % (:workflows input)) (:parts input)))

(->> (load-input)
     (parse-input)
     (run)
     (filter identity)
     (map vals)
     (map #(apply + %))
     (reduce +))
