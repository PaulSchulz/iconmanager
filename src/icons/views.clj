(ns icons.views
  (:use [hiccup core page]))

(defn index-page []
  (html5
   [:head
    [:title "Icons"]
    (include-css "/css/style.css")]
   [:body
    [:h1 "Icons"]
    [:h2 "A tool for collecting, managing and presenting large numbers of icons."]
    ]))
