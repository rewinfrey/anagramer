(ns anagramer.core)

(def words (clojure.string/split (slurp "words") #"\n"))

(defn letter-count [input]
  (count (clojure.string/replace input #"\ " "")))

(defn reduced-by-count [let-count]
  (reduce (fn [out-list word]
            (if (= let-count (count word))
              (conj out-list word)
              out-list)) [] words))

(defn anagram-match? [word input]
  (loop [remaining-input input]
    (cond
      (= word input) false
      (empty? (rest remaining-input)) true
      (>= (.indexOf word (str (first remaining-input))) 0) (recur (rest remaining-input))
      :else false)))

(defn reduced-by-letter-match [reduced-words input]
  (reduce (fn [matched-words word]
            (if (anagram-match? word input)
              (conj matched-words word)
              matched-words)) [] reduced-words))

(defn anagramize [input]
  (reduced-by-letter-match (reduced-by-count (letter-count input)) input))
