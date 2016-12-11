(ns advent-of-code-2016.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn- to-int [s] (Integer/parseInt s))
(defn- third [s] (second (next s)))

(def data (->> "day3"
               io/resource
               slurp
               s/split-lines
               (map #(s/split % #"\s+"))
               (map #(filter not-empty %))
               (map #(map to-int %))))
(permutations)

(defn is-triangle? [[side-a side-b side-c]]
  (and (> (+ side-a side-b) side-c)
       (> (+ side-a side-c) side-b)
       (> (+ side-b side-c) side-a)))

(defn solve-part-1 []
  (->> data
       (filter is-triangle?)
       count))

(defn solve-part-2 []
  (->> data
       (partition 3)
       (mapcat (fn [rows]
                 [(map first rows)
                  (map second rows)
                  (map third rows)]))
       (filter is-triangle?)
       count))
