(ns anagramer.core-spec
  (:require [speclj.core :refer :all]
            [anagramer.core :refer :all]))

(defmacro with-redef-word-list [& body]
  `(with-redefs [words (clojure.string/split (slurp "test_words") #"\n")]
     ~@body))

(describe "anagramize"
  (it "returns a vector of anagrams for a given string"
    (with-redef-word-list
      (should== ["ate" "eat"] 
        (anagramize "tea")))))

(describe "letter-count"
  (it "returns 0 for empty string"
    (should= 0
             (letter-count "")))

  (it "returns 1 for 'a'"
    (should= 1
             (letter-count "a")))

  (it "does not count spaces between letters"
    (should= 3
             (letter-count "a bc"))))

(describe "reduced-by-count"
  (it "reduces word list by letter count"
    (with-redef-word-list
      (should== ["ate" "eat" "tea" "cat" "hat"]
                (reduced-by-count 3))))
  
  (it "returns empty list when word list contains no words matching given letter count"
    (with-redef-word-list
      (should= [] 
               (reduced-by-count 4)))))

(describe "anagram-match?"
  (it "returns true if an input string and word contain exactly the same letters"
    (should= true
             (anagram-match? "hat" "tah")))
  
  (it "returns false if an input string and word do not contain exactly the same lettesr"
    (should= false
             (anagram-match? "hat" "cat"))))

(describe "reduced-by-letter-match"
  (it "reduces word list by matching letters of input vs word in word list"
    (with-redef-word-list
      (should== ["ate" "eat"]
                (reduced-by-letter-match  words "tea")))))
