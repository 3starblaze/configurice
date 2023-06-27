(ns threestarblaze.configurice.adapters
  (:require
   [clojure.string :as s])
  (:import
   [org.ini4j Ini]))

(defn- map->ini [m]
  (let [ini (Ini.)]
    (doseq [[section vars] m]
      (doseq [[k v] vars]
        (.put ini section k v)))
    (with-out-str (.store ini *out*))))

(def i3-adapter
  {:key
   "i3"

   :transformer
   (fn [{:keys [statements]}]
     (s/join "\n" statements))

   :runner-command-builder
   (fn [{:keys [foreign-config-path]}]
     (format "i3 -c %s" foreign-config-path))})


(def rofi-adapter
  {:key
   "rofi"

   :transformer
   (fn [{:keys [mapping]}]
     (with-out-str
       (let [indentation "    "]
         (doseq [[selector vars] mapping]
           (printf "%s {\n" selector)
           (doseq [[k v] vars]
             (printf (str indentation "%s: %s;\n") k v))
           (println "}")))))

   :runner-command-builder
   (fn [_]
     "rofi -show drun")})

(def dunst-adapter
  {:key
   "dunst"

   :transformer
   (fn [{:keys [mapping]}]
     (map->ini mapping))

   :runner-command-builder
   (fn [{:keys [foreign-config-path]}]
     (format "dunst -config %s" foreign-config-path))})


(def polybar-adapter
  {:key
   "polybar"

   :transformer
   (fn [{:keys [mapping]}]
     (map->ini mapping))

   :runner-command-builder
   (fn [{:keys [foreign-config-path]}]
     (format "polybar example -config %s" foreign-config-path))})
