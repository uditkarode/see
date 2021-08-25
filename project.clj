(defproject see "0.1.0"
  :description "A program to list directory contents or output file contents with syntax highlighting!"
  :url "https://github.com/uditkarode/see"
  :license {:name "GPL-version-3-or-later"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.206"]
                 [clojure-term-colors "0.1.0"]]
  :main see.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
