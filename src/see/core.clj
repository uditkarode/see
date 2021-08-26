(ns see.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.java.io :as io])
  (:require [see.util :as util])
  (:gen-class))

(def cli-options
  [["-l" "--list" "Display contents in the long format"]
   ["-h" "--help" "Display this help menu"]])

(def flag-set #(util/not-nil? (%1 %2)))

(defn see--list
  [options file]
  (let [nl-cond #(<= 6 (:col %1))]
    (:lst (reduce #(do {:col (if (nl-cond %1) 1 (+ (:col %1) 1))
                        :lst (str (:lst %1) (if (nl-cond %1) "\n" "\t") %2)}) {:col 0 :lst ""} (.list file)))))

(defn see--cat
  [options file]
  (if (flag-set :list options) (slurp file) (slurp file)))

(defn get-targets
  "Returns a vector containing the current directory if the given vector is empty, or
  the vector itself otherwise"
  [raw-targets] (if (empty? raw-targets) [(util/get-current-directory)] raw-targets))

(defn get-result-vector
  "If the given file doesn't exist, returns nil
  If the given file is a directory, returns a vector containing it's contents
  If the given file is a file, returns it's contents as a string"
  [options targets]
  (map #(let [file (io/file %1)]
          (if
           (not (util/file-exists? file)) nil
           (if (util/file-is-directory? file)
             (see--list options file)
             (see--cat options file)))) targets))

(defn see-handle
  "List the target directories, defaults to the current directory"
  [options & raw-targets]
  (doseq [r (get-result-vector options (get-targets raw-targets))] (println r)))

(defn -main [& raw-args]
  (let [args (parse-opts raw-args cli-options)
        fst (first (:errors args)) cmd-args (:arguments args)]
    (if
     (util/not-nil? fst)
      (println (str fst))
      (let [options (:options args) help (:help options)]
        (cond
          ;; -h/--help was passed
          (util/not-nil? help) (println (:summary args))
          ;; certain arguments were passed
          (< 0 (count cmd-args)) (apply (partial see-handle options) cmd-args)
          ;; no flags or arguments were passed
          :else (see-handle options))))))
