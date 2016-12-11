(ns advent-of-code-2016.day1
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.pprint :refer [pprint]]))

;; turning is easier with matrix mult, but i won't add a dependency for two turns
(def turns
  {[0 -1] {\L [-1  0]
           \R [1  0]}
   [1  0] {\L [0 -1]
           \R [0  1]}
   [0  1] {\L [1  0]
           \R [-1  0]}
   [-1  0] {\L [0  1]
            \R [0 -1]}})

(def initial-position
  {:dir [0 -1] ;NORTH
   :pos [0 0]})

(def create-turn
  (memoize
   (fn [t]
     (fn [{:keys [dir pos]}]
       (let [new-dir ((turns dir) t)]
         {:pos (map + pos new-dir)
          :dir new-dir})))))

(defn step [{:keys [pos dir]}]
  {:dir dir
   :pos (map + pos dir)})

(defn create-step [] step)

(defn to-steps [[turn & steps]]
  (concat [(create-turn turn)]
          (repeat (dec (Integer/parseInt (apply str steps))) (create-step))))

(defn load-data []
  (->> "day1"
       (io/resource)
       (slurp)
       (#(s/split % #","))
       (map s/trim)
       (mapcat to-steps)))

(defn manhatten-distance
  ([a] (manhatten-distance [0 0] a))
  ([a b]
   (reduce + (map #(Math/abs %) (map - a b)))))

(defn solve-part-1 []
  (->> (load-data)
       (reduce (fn [current step-fn] (step-fn current)) initial-position)
       :pos
       manhatten-distance))

(defn solve-part-2 []
  (->> (load-data)
       (reductions (fn [current step-fn] (step-fn current)) initial-position)
       (map :pos)
       (reductions conj (list))
       (filter (fn [[pos & visited]] ((set visited) pos)))
       (ffirst)
       (manhatten-distance)))
