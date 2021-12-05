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

;; Part 2 introduces more complex rules for calculating how much to
;; change the depth and horizontal position by. To make this cleaner,
;; the functions have been reorganised to process specific directions
;; and mutate the position hash, rather than only computing the
;; horizontal position and depth.
;;
;; This would probably work just as well for Puzzle 1, but I'll keep
;; the solution as-is so we can compare and contrast the changes.
(defn process-forward
  [{horizontal :horizontal depth :depth aim :aim :as position} units]
  (assoc position
    :horizontal (+ horizontal units)
    :depth (+ depth (* aim units))))

(defn process-up
  [{aim :aim :as position} units]
  (assoc position
    :aim (- aim units)))

(defn process-down
  [{aim :aim :as position} units]
  (assoc position
    :aim (+ aim units)))

(def part-2
  (let [position
        (reduce (fn [acc
                     [direction units :as step]]
                  (cond (= "up" direction) (process-up acc units)
                        (= "down" direction) (process-down acc units)
                        (= "forward" direction) (process-forward acc units)
                        :else acc))
                {:horizontal 0 :depth 0 :aim 0}
                course)]
    (* (position :horizontal) (position :depth))))

(defn run
  [opts]
  (println part-1)
  (println part-2))