(ns three-starblaze.confi-raisu.core
  (:require
   [clojure.pprint :as pprint]
   [three-starblaze.confi-raisu.util :as util]
   [three-starblaze.confi-raisu.examples.example
    :refer [dunst-data polybar-data rofi-data]]))

(def registry
  [dunst-data polybar-data rofi-data])

(defn error-wrong-usage! []
  (binding [*out* *err*]
      (println "Wrong usage!")
      (System/exit 1)))

(defn list! []
  (doseq [item registry]
    (pprint/pprint item)))

(defn build! []
  (doseq [item registry]
    (util/write-config! item)))

(defn -main [& args]
  (case (count args)
    0 (list!)
    1 (case (first args)
        "build" (build!)
        (error-wrong-usage!))
    (error-wrong-usage!)))
