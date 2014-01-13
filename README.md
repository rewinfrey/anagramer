# Anagramer

Simple anagram finder in Clojure. Uses OSx word list found in `/usr/share/dict/words`.

### Usage

Prepare the repo:

```
$ git clone git@github.com:rewinfrey/anagramer.git
$ cd anagramer
```

Prepare the executable jar:

`$ lein uberjar`

(note: this requires [Leiningen](http://leiningen.org/))

Find an anagram:

```
$ java -jar target/anagramer-0.1.0-SNAPSHOT-standalone.jar <input word>
```

Example:

```
$ java -jar target/anagramer-0.1.0-SNAPSHOT-standalone.jar tea
```

Returns ["eat", "ate", "eta", "tae"]

### License

Copyright 2014 Rick Winfrey

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
