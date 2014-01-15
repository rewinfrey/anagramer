# Anagramer

Simple anagram finder in Clojure. Uses OSx word list found in `/usr/share/dict/words`.

### Usage

Prepare the repo:

```
$ git clone git@github.com:rewinfrey/anagramer.git
$ cd anagramer
```

Run the anagramer:

```
$ ./anagramer <input string>
```

Special Notes:

1. The first time anagramer is executed it will build an executable jar using your system's Java binary
2. This requires [Leiningen](http://leiningen.org/) in order to build the executable jar

Example:

```
$ ./anagramer tea

Anagram results:  [ate eat eta tae tea]

```

### License

Copyright 2014 Rick Winfrey

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
