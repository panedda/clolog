(ns clolog.events)

(defrecord Event [:timestamp
                  :event-id
                  :activity
                  :actor
                  :event-data])
