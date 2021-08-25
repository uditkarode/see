(ns see.util)

(def file-exists?
  "Checks if a given File exists"
  #(.exists %1))

(def file-is-directory?
  "Check if a given File is a directory"
  #(.isDirectory %1))

(def get-current-directory
  "Get the current working directory"
  #(System/getProperty "user.dir"))

(def not-nil? #(not (nil? %1)))
