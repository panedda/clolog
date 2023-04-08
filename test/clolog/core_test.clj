(ns clolog.core-test
  (:require [clojure.test :refer :all]
            [clolog.core :refer :all]))

(def data-lines
  "Service ID,Operation,Start Date,End Date,Agent Position,Customer ID,Product,Service Type,Agent
Case 1,Inbound Call,9.3.10 8:05,9.3.10 8:10,FL,Customer 1,MacBook Pro,Referred to Servicer,Helen
Case 1,Handle Case,11.3.10 10:30,11.3.10 10:32,FL,Customer 1,MacBook Pro,Referred to Servicer,Helen
Case 1,Call Outbound,11.3.10 11:45,11.3.10 11:52,FL,Customer 1,MacBook Pro,Referred to Servicer,Henk
Case 2,Inbound Call,4.3.10 11:43,4.3.10 11:46,FL,Customer 2,MacBook Pro,Referred to Servicer,Susi
Case 3,Inbound Call,25.3.10 9:32,25.3.10 9:33,FL,Customer 3,MacBook Pro,Referred to Servicer,Mary
Case 18,Inbound Call,25.3.10 10:35,25.3.10 10:38,FL,Customer 16,iPhone,Referred to Servicer,Karen
Case 19,Inbound Email,14.3.10 14:08,18.3.10 8:04,FL,Customer 17,MacBook Pro,Product Assistance,Henk
Case 19,Inbound Email,18.3.10 8:06,18.3.10 8:07,FL,Customer 17,MacBook Pro,Product Assistance,Henk
Case 19,Handle Case,18.3.10 8:07,18.3.10 8:08,FL,Customer 17,MacBook Pro,Product Assistance,Henk
Case 19,Handle Case,18.3.10 8:09,18.3.10 8:09,FL,Customer 17,MacBook Pro,Product Assistance,Henk
Case 20,Handle Case,27.3.10 11:39,27.3.10 11:39,FL,Customer 18,iPhone,Product Assistance,Mariska
Case 20,Handle Case,27.3.10 12:04,27.3.10 12:10,FL,Customer 18,iPhone,Product Assistance,Mariska
Case 20,Handle Case,4.4.10 9:34,4.4.10 9:34,FL,Customer 18,iPhone,Product Assistance,Mariska
Case 21,Inbound Call,4.3.10 12:17,4.3.10 12:22,FL,Customer 19,iPhone,Product Assistance,Anne
Case 22,Inbound Call,22.3.10 8:30,22.3.10 8:31,FL,Customer 3,MacBook Pro,Product Assistance,Erik
Case 23,Inbound Call,22.3.10 14:59,22.3.10 15:13,FL,Customer 20,iPhone,Referred to Servicer,Ton
Case 24,Inbound Call,28.3.10 10:04,28.3.10 10:09,FL,Customer 21,iPhone,Referred to Servicer,Kenny
Case 25,Inbound Call,12.3.10 9:53,12.3.10 9:56,FL,Customer 22,iPhone,Product Assistance,Henk
Case 25,Call Outbound,12.3.10 11:45,12.3.10 11:49,FL,Customer 22,iPhone,Product Assistance,Henk
Case 26,Inbound Call,18.3.10 17:52,18.3.10 17:52,FL,Customer 23,iPhone,Product Assistance,Christian
Case 27,Inbound Call,29.3.10 14:33,29.3.10 14:35,FL,Customer 3,iPhone,Product Assistance,Anne
Case 28,Handle Email,6.3.10 13:57,6.3.10 14:03,FL,Customer 24,iPhone,Product Assistance,Mary
Case 28,Call Outbound,6.3.10 14:03,6.3.10 14:04,FL,Customer 24,iPhone,Product Assistance,Mary
Case 29,Inbound Call,20.3.10 7:35,20.3.10 7:36,FL,Customer 3,MacBook Pro,Product Assistance,Henk
Case 30,Inbound Call,22.3.10 10:16,22.3.10 10:17,FL,Customer 25,iPhone,Product Assistance,Maja")

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
