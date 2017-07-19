(ns generative-testing-sample.core
  (:gen-class)
  (:require
    [clojure.test.check.generators :as gen]
    [clj-http.client :as client]
    [clojure.data.json :as json]))

(def sample-webapi "https://jsonplaceholder.typicode.com/posts/1")

(def ads-write-endpoint "http://localhost:5801/write")
(def ads-query-endpoint "http://localhost:5700/query")

(def txEntity [{:id -1000, :dt "Transaction", :txAuthor "root", :txTargetBranch 0}])
(defn getJsonForWrite [txData] (json/write-str (concat txData txEntity)))
(defn write [txData] (json/read-str ((client/post ads-write-endpoint {:body (getJsonForWrite txData)}) :body)))

(def patientSchema [{:id -1, :dt "Datatype", :dtName "Patient", :dtFields [-10]}, {:id -10, :dt "Attribute", :attrName "firstName", :attrType 3}])
(defn writePatientSchema [] (write patientSchema))
(defn writePatient [name] (write [{:dt "Patient", :firstName name}]))
(defn getResolvedId [tmpId, txResult] (get-in txResult ["resolvedIds", (.indexOf (txResult "ids") tmpId)]))

(defn getPatientByIdQueryData [id] ["a", {}, {:patients ["Patient", {:filters ["=", "id", id]}, {:firstName ["firstName", {}, {}]}]}])
(defn getPatientNameById [id] (get-in (json/read-str ((client/post ads-query-endpoint {:body (json/write-str (getPatientByIdQueryData id))}) :body)) ["patients" 0 "firstName"]))

(def write-retrieve
  (prop/for-all [k gen-key
                 v gen-value]
    (let [id (getResolvedId -1 (writePatient v))]
    (= v (getPatientNameById id)))))

(def gen-key gen/large-integer)
(def gen-value gen/string)

(defn -main
  [& args]
  (println "1 int sample (scalar):" (gen/generate gen/int))
  (println "10 int samples (list):" (gen/sample gen/int))
  (println "5 doubles:" (gen/sample gen/double 5))
  (println "10 strings:" (gen/sample gen/string))
  (println "4 int->uuid maps:" (gen/sample (gen/map gen/int gen/uuid) 4)
  (println (client/get sample-webapi))))
