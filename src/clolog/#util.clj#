(ns clolog.util
  (:require [clojure.java.io :as io]))

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
        (let [labels
               (into [] (for [x (range 10)]
                          (str "label-" (inc x))))]
           (create-record line labels)
        )
      )
    )
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
           (map #(create-record-from-line % labels) (rest file-content))
           )
         ()
       )
     )
   )
  )

