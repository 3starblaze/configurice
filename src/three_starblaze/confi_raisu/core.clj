(ns three-starblaze.confi-raisu.core
  (:require
   [clojure.java.io :as io]
   [three-starblaze.confi-raisu.examples.example :refer [dunst-data]])
  (:import
   [org.ini4j Ini]))

(def build-path
  "Full path for auto-built configuration files."
  (-> (io/file (System/getProperty "user.home") ".config/confi-raisu/build")
      .getPath))

(defn ensure-file-exists!
  "Recursively create file and every parent directory if they don't exist."
  ([s]
   (ensure-file-exists! s false))
  ([s dir?]
   (let [f (io/file s)]
     (when-let [parent (.getParentFile f)]
       (ensure-file-exists! (.getPath parent) true))
     (when-not (.exists f)
       (printf "%s does not exist, creating it ...\n" f)
       (if dir?
         (.mkdir f)
         (.createNewFile f))))))

(defn map->ini [m]
  (let [ini (Ini.)]
    (doseq [[section vars] m]
      (doseq [[k v] vars]
        (.put ini section k v)))
    (with-out-str (.store ini *out*))))

(defn generate-dunst
  "Generate dunst config and return the new command."
  [config]
  (let [file (io/file build-path (:key config))
        full-filename (.getPath file)]
    (ensure-file-exists! file)
    (with-open [w (io/writer file)]
      (->> (:config config)
         map->ini
         (.write w)))
    (format (:command config) full-filename)))

(defn main! []
  (println (generate-dunst dunst-data)))
