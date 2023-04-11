(ns clolog.core-test
  (:require [clojure.test :refer :all]
            [clolog.test-util :refer :all]
            [clolog.util :refer :all]
            [clolog.events :refer :all]
            [clolog.core :refer :all]))

(deftest testcsv-line-to-map
  (testing "Testing csv-line-to-map-test, I fail."
    (is (= '("A" "B") (split-values "A,B")))
    (is (= '("") (split-values "")))
    (is (= '("A" "B" "C") (split-values "A;B;C" ";")))
    (is (= '("A" "B" "C") (split-values "A;B;C;" ";")))
    (is (= '("A" "B" "C" " ") (split-values "A;B;C; " ";")))
    )
  )

(deftest test-create-record-from-line
  (testing "Testing the creation of a record from a csv line"
    (is (= {"name" "Paolo" "surname" "Anedda"}
           (create-record-from-line "Paolo,Anedda" ["name" "surname"])))
    (is (= {"label-1" "Paolo"}
           (create-record-from-line "Paolo" ["name" "surname"])))
    )
  )


(deftest test-create-records-from-multiple-lines
  (testing "Testing the creation of different records from several csv lines"
    (is (= '{}
           (create-records-from-multiple-lines "")))
    (is (= '({"a" "abc" "b" " cde"})
           (create-records-from-multiple-lines "abc, cde" "a,b")))
    (let [record-line (str "field1,field2
field3,field4,
field5,field6")]
      (is (= '({"label1" "field1" "label2" "field2"}
               {"label1" "field3" "label2" "field4"}
               {"label1" "field5" "label2" "field6"})
             (create-records-from-multiple-lines record-line "label1,label2")))
      (is (= '({"label-1" "field1" "label-2" "field2"}
               {"label-1" "field3" "label-2" "field4"}
               {"label-1" "field5" "label-2" "field6"})
             (create-records-from-multiple-lines record-line)))
      )
    )
  )

(deftest test-create-event-from-line
  (testing "Testing the creation of an Event from a line"
    (is (instance? clolog.events.Event (create-event-from-line "09/04/2023,000001,activity,joe,event_data"))))
  )
