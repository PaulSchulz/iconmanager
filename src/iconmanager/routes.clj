(ns iconmanager.routes
  (:use compojure.core
        iconmanager.views
        iconmanager.view-icons
        iconmanager.view-about
        [hiccup.middleware :only (wrap-base-url)]
        ring.middleware.refresh)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response])
  ;; Copied from 'fontmanager' project
  (:import (java.util.prefs Preferences)
           (java.lang System)
           (java.awt GraphicsEnvironment))
  )

(def options
  {:dir-incoming "~/Documents/git/monomatch-myriad/icons/incoming"
   :dir-icons    "/home/paul/Documents/git/monomatch-myriad/icons/svg/"
   :dir-archive  "~/Documents/git/monomatch-myriad/icons/archive"
   :dir-metadata "/home/paul/Documents/git/monomatch-myriad/icons/metadata/"
   ;; :dir-metadata "resources/public/icons/metadata"

   })

;; The user preferences for this application.
(def pref-node (.node (Preferences/userRoot) "iconmanager"))

(defroutes main-routes
  (GET "/"           []        (index-page options))
  (GET "/icons"      []        (icons-page options))
  (GET "/icon/:hash.svg"       [hash] (icon-image options hash))
  (GET "/icon/:hash"           [hash] (icon-page options hash))
  (GET "/tags"                 []     (tags-page))
  (GET "/about"                []     (about-page options))

  ;; New pages
  ;; (GET "index.html"  []     (about-page options))
  (GET "/icondir"   [] (options :dir-icons))
  (GET "/homedir"   [] (System/getProperty "user.home"))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)
      ;; Monitor file and automatically refresh them
      (wrap-refresh)
      ))
