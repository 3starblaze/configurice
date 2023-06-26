#!/usr/bin/env bb

(defn wrong-usage! []
  (println "Wrong usage!"))

(defn run! [key]
  (->> (str (System/getProperty "user.home") "/.config/configurice/build/intermediate.edn")
       slurp
       edn/read-string
       (filter #(= (:key %) key))
       first
       :command-formatted
       (shell/sh "bash" "-c")
       println))

(let [[cmd key] *command-line-args*]
  (if (and (= cmd "run") key)
    (run! key)
    (wrong-usage!)))
