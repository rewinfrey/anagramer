(ns anagramer.core-spec
  (:require [speclj.core :refer :all]
            [anagramer.core :refer :all]))

(def test-words (clojure.string/split (slurp "test_words") #"\n"))

(describe "anagramer"
  (around [it]
          (with-redefs [words test-words]
            (it)))

  (context "anagramize"
    (it "returns a map whose :results is a vector of anagrams for a given string"
      (should== {:results ["tea" "eat" "ate"] :input "tea" :letter-count 3 :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (anagramize "tea")))

    (it "returns a map whose :results is an empty vector when no anagrams exist"
      (should== {:results [] :input "hello" :letter-count 5 :reduced-by-count ["ohelo" "chelo" "hollo"] }
                (anagramize "hello"))))

  (context "count-letters"
    (it "count is 0 for empty string"
      (should== {:letter-count 0 :input ""}
                (count-letters {:input ""})))

    (it "count is 1 for 'a'"
      (should== {:letter-count 1 :input "a"}
                (count-letters {:input "a"})))

    (it "count ignores spaces between letters"
      (should== {:letter-count 3 :input "a bc"}
                (count-letters {:input "a bc"}))))

  (context "reduce-by-count"
    (it "reduces word list by letter count"
      (should== {:letter-count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (reduce-by-count {:letter-count 3 :input "tea"})))

    (it "returns empty list when word list contains no words matching given letter count"
      (should== {:letter-count 4 :input "teaa" :reduced-by-count []}
                (reduce-by-count {:letter-count 4 :input "teaa" :reduced-by-count []}))))

  (context "anagram-match?"
    (it "returns true if an input string and word contain exactly the same letters"
      (should= true
               (anagram-match? "hat" "tah")))

    (it "returns false if an input string and word do not contain exactly the same lettesr"
      (should= false
               (anagram-match? "hat" "cat")))

    (it "returns false if a word contains all the characters of an input string, but also contains other characters"
      (should= false
               (anagram-match? "Chelo" "hello"))))

  (context "reduce-by-letter-match"
    (it "reduces word list by matching letters of input vs word in word list"
      (should== {:results ["tea" "eat" "ate"] :letter-count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (reduce-by-letter-match  {:letter-count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}))))

  (context "letter-occurences"
    (it "returns a map of letter keys, with the value of one occurence for the string 'abc'"
      (should= {"a" 1 "b" 1 "c" 1}
               (letter-occurences "abc")))

    (it "returns a map of letter keys, with the value of two occurences for the string 'aabbcc'"
      (should= {"a" 2 "b" 2 "c" 2}
               (letter-occurences "aabbcc")))

    (it "returns a map of letter keys, with mixed values of the occurences for the string 'abbccc'"
      (should= {"a" 1 "b" 2 "c" 3}
               (letter-occurences "abbccc"))))
  )
