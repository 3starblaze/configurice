(ns three-starblaze.confi-raisu.core
  (:import
   [org.ini4j Ini]))

(defn map->ini [m]
  (let [ini (Ini.)]
    (doseq [[section vars] m]
      (doseq [[k v] vars]
        (.put ini section k v)))
    (with-out-str (.store ini *out*))))

(defn main! []
  (println
   (map->ini {"section foo" {"a" 1 "b" 2}
              "section bar" {"c" 3 "d" 4}})))
