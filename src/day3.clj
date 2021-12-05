(ns day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (io/resource "day3/input"))

(def bitfield-length 12)

(def bits-as-ints
  (->> (slurp input)
       (str/split-lines)
       (map (fn [binary-str] (read-string (str "2r" binary-str))))))

(defn bit-at-position
  [position number]
  (bit-test number (- 12 1 position)))

(defn bits-at-position
  [position]
  (->> bits-as-ints
       (map (fn [bit] (bit-at-position position bit)))))

(defn count-pred
  [pred bits]
  (count (filter pred bits)))

(defn most-common-bit
  [bits]
  (cond
    (> (count-pred true? bits) (count-pred false? bits)) true
    :else false))

(defn least-common-bit
  [bits]
  (not (most-common-bit bits)))

(defn set-gamma-bit
  [gamma position bits]
  (if (most-common-bit bits) (bit-set gamma (- bitfield-length position 1)) gamma))

(defn set-epsilon-bit
  [epsilon position bits]
  (if (least-common-bit bits) (bit-set epsilon (- bitfield-length position 1)) epsilon))

(def part-1
  (->>
    (reduce (fn [[gamma epsilon] position]
              (let [bit-column (bits-at-position position)]
                [(set-gamma-bit gamma position bit-column) (set-epsilon-bit epsilon position bit-column)]))
            [0 0]
            (range 0 bitfield-length))
    (reduce *)))

(defn run
  [opts]
  (println part-1))