(ns threestarblaze.configurice.core
  (:require
   [clojure.java.io :as io]
   [malli.core :as m]
   [malli.error :as me]
   [threestarblaze.configurice.util :as util]))

(def config-schema
  [:map
   [:foreign-configs
    [:sequential [:map
                  [:key :string]
                  [:config [:map-of any? any?]]
                  [:mapper fn?]
                  [:command :string]]]]])

(defn build-config!
  "Write foreign configuration and the intermediate file."
  [config]
  (when-let [err (m/explain config-schema config)]
    (throw (IllegalArgumentException. (str (me/humanize err)))))

  (doseq [foreign-config (:foreign-configs config)]
    (util/write-config! foreign-config))
  (let [edn-data (map (fn [foreign-config]
                        {:key
                         (:key foreign-config)

                         :command-formatted
                         (format (:command foreign-config)
                                 (str util/build-path "/" (:key foreign-config)))})
                      (:foreign-configs config))]
    (with-open [w (io/writer (io/file util/build-path "intermediate.edn"))]
      (.write w (with-out-str (prn edn-data))))))
