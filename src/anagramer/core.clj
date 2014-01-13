(ns anagramer.core)

(def words (clojure.string/split (slurp "words") #"\n"))

(defn letter-occurences [word]
  (reduce (fn [letter-map letter]
            (let [str-letter (str letter)
                  occurences (get letter-map str-letter)]
              (if (nil? occurences)
                (assoc letter-map str-letter 1)
                (assoc letter-map str-letter (inc occurences)))))
          {}
          word))

(defn all-letters-accounted-for? [word input]
  (= (letter-occurences word) (letter-occurences input)))

(defn anagram-match? [word input]
  (loop [remaining-word word]
    (cond
      (= word input) false
      (empty? remaining-word) (all-letters-accounted-for? word input)
      (>= (.indexOf input (str (first remaining-word))) 0) (recur (rest remaining-word))
      :else false)))

(defn reduced-by-letter-match [reduced-words input]
  (reduce (fn [matched-words word]
            (if (anagram-match? word input)
              (conj matched-words word)
              matched-words)) [] reduced-words))

(defn letter-count [input]
  (count (clojure.string/replace input #"\ " "")))

(defn reduced-by-count [let-count]
  (reduce (fn [out-list word]
            (if (= let-count (count word))
              (conj out-list word)
              out-list)) [] words))

(defn anagramize [input]
  (reduced-by-letter-match (reduced-by-count (letter-count input)) input))
