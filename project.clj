(defproject generative-testing-sample "0.1.0-SNAPSHOT"
  :description "Sample code: Generative testing + HTTP client"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/test.check "0.9.0"]
                 [clj-http "3.6.1"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot generative-testing-sample.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repl-options {:init (do
                  (println "For test.check, see ****https://github.com/clojure/test.check/blob/master/doc/cheatsheet.md****")
                  (println "For HTTP requests, see ****https://github.com/dakrone/clj-http****")
                  (println "For JSON parsing, see ****https://github.com/clojure/data.json****")
                  (require '[clojure.test.check :as tc])
                  (require '[clojure.test.check.generators :as gen])
                  (require '[clojure.test.check.properties :as prop])
                  (require '[clj-http.client :as client])
                  (require '[clojure.data.json :as json]))})
