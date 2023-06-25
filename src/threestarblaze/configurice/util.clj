(ns threestarblaze.configurice.util
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :refer [sh]])
  (:import
   [org.ini4j Ini]))

(def build-path
  "Full path for auto-built configuration files."
  (-> (io/file (System/getProperty "user.home") ".config/configurice/build")
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

(defn write-config!
  [config]
  (let [file (io/file build-path (:key config))]
    (ensure-file-exists! file)
    (with-open [w (io/writer file)]
      (->> ((:mapper config) (:config config))
           (.write w)))))

(defn run-config!
  [config]
  (sh "bash" "-c"
      (-> (:command config)
          (format (.getPath (io/file build-path (:key config)))))))

(defn map->ini [m]
  (let [ini (Ini.)]
    (doseq [[section vars] m]
      (doseq [[k v] vars]
        (.put ini section k v)))
    (with-out-str (.store ini *out*))))

(defn map->rofi-config [m]
  (with-out-str
    (let [indentation "    "]
      (doseq [[selector vars] m]
        (printf "%s {\n" selector)
        (doseq [[k v] vars]
          (printf (str indentation "%s: %s;\n") k v))
        (println "}")))))
