(ns anagramer.core-spec
  (:require [speclj.core :refer :all]
            [anagramer.core :refer :all]))

(defmacro with-redef-word-list [& body]
  `(with-redefs [words (clojure.string/split (slurp "test_words") #"\n")]
     ~@body))

(describe "anagramize"
  (it "returns a vector of anagrams for a given string"
    (with-redef-word-list
      (should== {:results ["tea" "eat" "ate"] :input "tea" :count 3 :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (anagramize "tea"))))

  (it "returns an empty vector when no anagrams exist"
    (with-redef-word-list
      (should== {:results [] :input "hello" :count 5 :reduced-by-count ["ohelo" "chelo" "hollo"] }
                (anagramize "hello")))))

(describe "letter-count"
  (it "count is 0 for empty string"
    (should== {:count 0 :input ""}
             (letter-count {:input ""})))

  (it "count is 1 for 'a'"
    (should== {:count 1 :input "a"}
             (letter-count {:input "a"})))

  (it "count ignores spaces between letters"
    (should== {:count 3 :input "a bc"}
             (letter-count {:input "a bc"}))))

(describe "reduced-by-count"
  (it "reduces word list by letter count"
    (with-redef-word-list
      (should== {:count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (reduced-by-count {:count 3 :input "tea"}))))

  (it "returns empty list when word list contains no words matching given letter count"
    (with-redef-word-list
      (should== {:count 4 :input "teaa" :reduced-by-count []}
               (reduced-by-count {:count 4 :input "teaa" :reduced-by-count []})))))

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
      (should== {:results ["tea" "eat" "ate"] :count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]}
                (reduced-by-letter-match  {:count 3 :input "tea" :reduced-by-count ["tea" "eat" "ate" "cat" "hat"]})))))

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
