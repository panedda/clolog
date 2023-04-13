(ns clolog.events
  (:require [clolog.util :refer :all]))

(defrecord Event [timestamp
                  event-id
                  activity
                  actor
                  event-data])

(defn create-event-from-line
  "Creates an Event record from a comma separated line"
  ([line]
  (let [values (split-values line)]
    (->Event (nth values 0) (nth values 1) (nth values 2) (nth values 3) (nth values 4)))
   )
  )

(defn create-event-list-from-multiple-lines
  "Creates an Event record from a comma separated line"
  ([lines]
  (let [values (split-lines lines)]
    (map create-event-from-line values)
   )
  )
)
