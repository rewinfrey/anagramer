(ns anagramer.main
(:require [anagramer.core :as anagramer :refer [anagramize]])
(:require [clojure.string :as string])
  (:gen-class))

(defn -main [& args]
  (println "")
  (println "Anagram results: " (:results (anagramer/anagramize (first args))))
  (println ""))
