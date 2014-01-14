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

(defn anagram-match? [word1 word2]
  (= (letter-occurences word1) (letter-occurences word2)))

(defn reduced-by-letter-match [anagram-map]
  (let [input-word (:input anagram-map)
        results (reduce (fn [matched-words word]
                          (if (anagram-match? word input-word)
                            (conj matched-words word)
                            matched-words))
                        [] (:reduced-by-count anagram-map))]
    (assoc anagram-map :results results)))

(defn letter-count [anagram-map]
  (let [letters (count (clojure.string/replace (:input anagram-map) #"\ " ""))]
    (assoc anagram-map :count letters)))

(defn reduced-by-count [anagram-map]
  (let [reduced-by-count (reduce (fn [out-list word]
                                   (if (= (:count anagram-map) (count word))
                                     (conj out-list word)
                                     out-list))
                                 [] words)]
    (assoc anagram-map :reduced-by-count reduced-by-count)))

(defn anagramize [input]
  (-> {:input input}
      letter-count
      reduced-by-count
      reduced-by-letter-match))
