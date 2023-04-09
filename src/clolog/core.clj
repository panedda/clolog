(ns clolog.core
  (:require [clolog.util :refer :all]
            [clojure.java.io :as io])
  (:gen-class))

(defn -main
  "Parsing the file"
  [& args]
  (let [filename (first args)]
    (println "Parsing the file:" filename)
    (def records (create-records-from-file filename true))
    (println records)
  )
)
