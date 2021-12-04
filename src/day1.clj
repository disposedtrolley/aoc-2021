(ns day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (io/resource "day1/input"))

(def measurements
  (->> (slurp input)
       (str/split-lines)
       (map edn/read-string)))

(defn run
  [opts]
  (->> measurements
       (partition 2 1)
       (map (fn [pair]
              (cond
                (> (last pair) (first pair)) 1
                :else 0)))
       (reduce +)))