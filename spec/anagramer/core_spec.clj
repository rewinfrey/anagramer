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
                (anagramize "tea"))))

  (it "returns an empty vector when no anagrams exist"
    (with-redef-word-list
      (should== []
                (anagramize "hello")))))

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
             (anagram-match? "hat" "cat")))

  (it "returns false if a word contains all the characters of an input string, but also contains other characters"
    (should= false
             (anagram-match? "Chelo" "hello"))))

(describe "reduced-by-letter-match"
  (it "reduces word list by matching letters of input vs word in word list"
    (with-redef-word-list
      (should== ["ate" "eat"]
                (reduced-by-letter-match  words "tea")))))

(describe "all-letters-accounted-for?"
  (it "ensures that anagram-like matches contain the same number of occurences of letters"
    (should= true
              (all-letters-accounted-for? "hello" "ohell")))

  (it "ensures that repeated letters are properly accounted for"
    (should= false
             (all-letters-accounted-for? "hello" "ohelo")))

  (it "ensures that letters not in the input word are not contained within the anagram result list"
    (should= false
             (all-letters-accounted-for? "hello" "ochel"))))

(describe "letter-occurences"
  (it "returns a map of letter keys, with the value of one occurence for the string 'abc'"
    (should= {"a" 1 "b" 1 "c" 1}
             (letter-occurences "abc")))

  (it "returns a map of letter keys, with the value of two occurences for the string 'aabbcc'"
    (should= {"a" 2 "b" 2 "c" 2}
             (letter-occurences "aabbcc")))

  (it "returns a map of letter keys, with mixed values of the occurences for the string 'abbccc'"
    (should= {"a" 1 "b" 2 "c" 3}
             (letter-occurences "abbccc"))))
