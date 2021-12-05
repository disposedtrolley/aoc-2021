(ns day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (io/resource "day2/input"))

(def course
  (->> (slurp input)
       (str/split-lines)
       (map (fn [line]
              (let [split (str/split line #" ")]
                (assoc
                  split
                  1
                  (edn/read-string (last split))))))))

(defn next-depth
  [step]
  (let [direction (first step)
        units (last step)]
    (cond (= "up" direction) (- units)
          (= "down" direction) units
        :else 0)))

(defn next-horizontal
  [step]
  (let [direction (first step)
        units (last step)]
    (cond (= "forward" direction) units
          :else 0)))

(def part-1
  (->>
    (reduce (fn [[horizontal depth :as acc]
                 step]
                (assoc acc
                  0 (+ horizontal (next-horizontal step))
                  1 (+ depth (next-depth step))))
            [0 0]
            course)
    (reduce *)))

(defn run
  [opts]
  (println part-1))