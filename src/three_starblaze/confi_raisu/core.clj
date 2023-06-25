(ns three-starblaze.confi-raisu.core
  (:require
   [clojure.java.io :as io]
   [three-starblaze.confi-raisu.util :as util]))

(defn build-config!
  "Write foreign configuration and the intermediate file."
  [config]
  (doseq [foreign-config (:foreign-configs config)]
    (util/write-config! foreign-config))
  (let [edn-data (map (fn [foreign-config]
                        {:key
                         (:key foreign-config)

                         :command-formatted
                         (format (:command foreign-config) util/build-path)})
                      (:foreign-configs config))]
    (with-open [w (io/writer (io/file util/build-path "intermediate.edn"))]
      (.write w (with-out-str (prn edn-data))))))
