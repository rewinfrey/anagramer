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

(defn reduce-by-letter-match [{:keys [reduced-by-count] :as anagram-map}]
  (let [input-word (:input anagram-map)
        results (reduce (fn [matched-words word]
                          (if (anagram-match? word input-word)
                            (conj matched-words word)
                            matched-words))
                        []
                        reduced-by-count)]
    (assoc anagram-map :results results)))

(defn count-letters [{:keys [input] :as anagram-map}]
  (let [letters (count (clojure.string/replace input #"\ " ""))]
    (assoc anagram-map :letter-count letters)))

(defn reduce-by-count [{:keys [letter-count] :as anagram-map}]
  (let [reduced-by-count (reduce (fn [out-list word]
                                   (if (= letter-count (count word))
                                     (conj out-list word)
                                     out-list))
                                 []
                                 words)]
    (assoc anagram-map :reduced-by-count reduced-by-count)))

(defn anagramize [input]
  (-> {:input input}
      count-letters
      reduce-by-count
      reduce-by-letter-match))
