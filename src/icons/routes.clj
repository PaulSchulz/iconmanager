(ns icons.routes
  (:use compojure.core
        icons.views
        icons.view-icons
        icons.view-about
        [hiccup.middleware :only (wrap-base-url)]
        ring.middleware.refresh)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(def options
  {:dir-incoming "~/Documents/git/monomatch-myriad/icons/incoming"
   :dir-icons    "~/Documents/git/monomatch-myriad/icons/svg"
   :dir-archive  "~/Documents/git/monomatch-myriad/icons/archive"
   })

(defroutes main-routes
  (GET "/" [] (index-page options))
  (GET "/icons" [] (icons-page options))
  (GET "/icon"  [] (icon-page))
  (GET "/tags"  [] (tags-page))
  (GET "/about" [] (about-page options))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)
      ;; Monitor file and automatically refresh them
      (wrap-refresh)
      ))
