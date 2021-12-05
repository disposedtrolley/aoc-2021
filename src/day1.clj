(ns day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (io/resource "day1/input"))

(def measurements
  (->> (slurp input)
       (str/split-lines)
       (map edn/read-string)))

(defn sum [coll]
  (reduce + coll))

(def part-1
  (->> measurements
       (partition 2 1)
       (map (fn [pair]
              (cond
                (> (last pair) (first pair)) 1
                :else 0)))
       (sum)))

(def sliding-window
  (->> measurements
       (partition 3 1)))

(def part-2
  (->> sliding-window
       (partition 2 1)
       (map (fn [pair]
              (cond
                (> (sum (last pair)) (sum (first pair))) 1
                :else 0)))
       (sum)))

(defn run
  [opts]
  (println part-1)
  (println part-2))