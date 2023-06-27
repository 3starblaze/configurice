(ns threestarblaze.configurice.core
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :refer [sh]]
   [malli.core :as m]
   [malli.error :as me]))

(def adapter-schema
  [:map
   [:key :string]
   [:transformer fn?]
   [:runner-command-builder fn?]])

(def config-schema
  [:map
   [:config-units
    [:sequential [:map
                  [:adapter adapter-schema]
                  [:data any?]]]]])


(def build-path
  "Full path for auto-built configuration files."
  (-> (io/file (System/getProperty "user.home") ".config/configurice/build")
      .getPath))

(defn- ensure-file-exists!
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

(defn- write-config-unit!
  [config-unit]
  (let [file (io/file build-path (-> config-unit :adapter :key))]
    (ensure-file-exists! file)
    (with-open [w (io/writer file)]
      (.write w ((-> config-unit :adapter :transformer)
                 (:data config-unit))))))

(defn build-config!
  "Write foreign configuration and the intermediate file."
  [config]
  (when-let [err (m/explain config-schema config)]
    (throw (IllegalArgumentException. (str (me/humanize err)))))

  (doseq [config-unit (:config-units config)]
    (write-config-unit! config-unit))
  (let [edn-data (map (fn [config-unit]
                        {:key
                         (-> config-unit :adapter :key)

                         :command-formatted
                         ((-> config-unit :adapter :runner-command-builder)
                          {:foreign-config-path
                           (str build-path "/" (-> config-unit :adapter :key))})})
                      (:config-units config))]
    (with-open [w (io/writer (io/file build-path "intermediate.edn"))]
      (.write w (with-out-str (prn edn-data))))))
