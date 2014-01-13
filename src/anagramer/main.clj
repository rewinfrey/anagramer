(ns anagramer.main
(:require [anagramer.core :as anagramer :refer [anagramize]])
(:require [clojure.string :as string])
  (:gen-class))

(defn -main [& args]
  (prn (anagramer/anagramize (first args))))
