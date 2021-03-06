(ns generative-testing-sample.core
  (:gen-class)
  (:require
    [clojure.test.check.generators :as gen]
    [clj-http.client :as client]
    [clojure.data.json :as json]))

(def sample-webapi "https://jsonplaceholder.typicode.com/posts/1")

(defn -main
  [& args]
  (println "1 int sample (scalar):" (gen/generate gen/int))
  (println "10 int samples (list):" (gen/sample gen/int))
  (println "5 doubles:" (gen/sample gen/double 5))
  (println "10 strings:" (gen/sample gen/string))
  (println "4 int->uuid maps:" (gen/sample (gen/map gen/int gen/uuid) 4)
  (println (client/get sample-webapi))))
