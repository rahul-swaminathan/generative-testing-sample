(ns generative-testing-sample.core-test
  (:require [clojure.test :refer :all]
            [generative-testing-sample.core :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(def sort-idempotent-prop
  (prop/for-all [v (gen/vector gen/int)]
    (= (sort v) (sort (sort v)))))

(defspec sort-is-idempotent ;; test name
  10 ;; number of iterations
  sort-idempotent-prop) ;; property being tested

(defspec first-element-is-min-after-sorting ;; test name
  100 ;; number of iterations
  (prop/for-all [v (gen/not-empty (gen/vector gen/int))]
    (= (apply min v)
       (first (sort v)))))

(deftest test-user-id-from-post
  (testing "Post 1 is written by User 1"
    (let [response (client/get sample-webapi) ;; see core.clj
          body (json/read-str (:body response))]
      (is (= 1 (get body "userId"))))))
