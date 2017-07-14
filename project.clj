(defproject generative-testing-sample "0.1.0-SNAPSHOT"
  :description "Sample code"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/test.check "0.9.0"]]
  :main ^:skip-aot generative-testing-sample.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repl-options {:init (do
                  (println "See ****https://github.com/clojure/test.check/blob/master/doc/cheatsheet.md****")
                  (require '[clojure.test.check :as tc])
                  (require '[clojure.test.check.generators :as gen])
                  (require '[clojure.test.check.properties :as prop]))})
