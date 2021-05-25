(ns icons.routes
  (:use compojure.core
        icons.views
        [hiccup.middleware :only (wrap-base-url)]
        ring.middleware.refresh)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)
      ;; Monitor file and automatically refresh them
      (wrap-refresh)
      ))
