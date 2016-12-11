(ns advent-of-code-2016.day2
  (:require [clojure.zip :as zip]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.pprint :refer [pprint]]))

(def keypad-1 [[1 2 3]
               [4 5 6]
               [7 8 9]])

(def keypad-2 [[nil nil  1     ]
               [nil   2  3  4  ]
               [  5   6  7  8 9]
               [nil  \A \B \C  ]
               [nil nil \D     ]])

(def start-pos [1 1])
(def dims-min-max [[0 2] [0 2]])


(defn move [pos move]
  (map + move pos))



(def moves
  {\U [0 -1]
   \D [0  1]
   \L [-1  0]
   \R [1  0]})

(def data (->> "day2"
               (io/resource)
               (slurp)
               (s/split-lines)
               (map (fn [line]
                      (->> line
                           (map moves))))))

(defn valid-pos? [keypad pos]
  (get-in keypad (reverse pos)))

(defn solve-part [keypad]
  (->> data
       (reductions
        (fn [line-start line]
          (->> line
               (reduce (fn [current step]
                         (let [new-pos (move current step)]
                           (if (valid-pos? keypad new-pos)
                             new-pos
                             current)))
                       line-start)))
        start-pos)
       rest
       (map reverse)
       (map (partial get-in keypad))
       (#(apply str %))))
(defn solve-part-1 [] (solve-part keypad-1))
(defn solve-part-2 [] (solve-part keypad-2))
