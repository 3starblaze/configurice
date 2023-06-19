(ns three-starblaze.confi-raisu.core
  (:require
   [clojure.pprint :as pprint]
   [three-starblaze.confi-raisu.util :as util]
   [three-starblaze.confi-raisu.examples.example
    :refer [dunst-data polybar-data rofi-data]]))

(def registry
  [dunst-data polybar-data rofi-data])

(defn error! [msg]
  (binding [*out* *err*]
    (println msg)
    (System/exit 1)))

(defn error-wrong-usage! []
  (error! "Wrong usage!"))

(defn list! []
  (doseq [item registry]
    (pprint/pprint item)))

(defn build! []
  (doseq [item registry]
    (util/write-config! item)))

(defn run-command! [args]
  (when (<= (count args) 1) (error-wrong-usage!))
  (let [config-name (second args)]
    (if-let [config (->> registry
                         (filter (fn [config] (= (:key config) config-name)))
                         first)]
      (util/run-config! config)
      (error! (format "Could not find config by key '%s'!" config-name)))))

(defn -main [& args]
  (if (= (count args) 0)
    (list!)
    (case (first args)
      "build" (build!)
      "run" (run-command! args)
      (error-wrong-usage!))))
