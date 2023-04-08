(ns clolog.core
  (:require [clojure.java.io :as io])
  (:gen-class))

;;(defn csv-line-to-map
;;  "Transform a CSV line into an hashmap of column key and value"
;;  ([line separator]
;;   (list (clojure.string/split line #separator))))
;;  ([line]
;;   (csv-line-to-map line ",")))

(defn split-lines
  [lines]
  (clojure.string/split (clojure.string/trim lines) #"\n")
  )

(defn split-values
  ([line separator]
   (clojure.string/split line (re-pattern separator)))
  ([line]
   (split-values line ",")))

(defn create-record
  [line labels]
  (apply merge (map #(into {} {%1 %2}) labels (split-values line)))
  )

(defn create-record-from-line
  [line labels]
  (do
    (let [values (split-values line)]
      (if (= (count values) (count labels))
        (create-record line labels)
        (hash-map)
        ))
    )
  )

(defn create-records-from-multiple-lines
  "Insert a list of records starting from multiple lines"
  ([lines labels]
   (let [data-lines (split-lines lines)
         column-names (split-values labels)]
        (if (= (count data-lines) 0)
          ()
          (if (= data-lines [""])
                 (hash-map)
                 (map #(create-record-from-line % column-names) data-lines)
                 )
            )
        )
   )
  ([lines]
   (create-records-from-multiple-lines lines "")
   )
  )

(defn create-records-from-file
  [filename has-labels]
  (with-open [rdr (io/reader filename)]
     (let [file-content (reduce conj [] (line-seq rdr))]
       (if (= has-labels true)
         (do
           (def labels (split-values (first file-content)))
           (def records (map #(create-record-from-line % labels) (rest file-content)))
           (println (count records))
           )
         ()
       )
     )
   )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (create-records-from-file "C:/Users/paolo.anedda/Documents/Projects/clojure/clojure-data/resources/CallcenterExample.csv" true))
