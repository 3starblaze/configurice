(ns three-starblaze.confi-raisu.boot-config
  (:require
   [boot.core :as boot]
   [three-starblaze.confi-raisu.core :as core]))

(boot/deftask run []
  (core/main!))
