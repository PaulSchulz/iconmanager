(ns icons.templates
  (:use [hiccup core page]))

(defn page-head []
  [:head
   [:title "Icons"]
   (include-css "/css/style.css")]
  )

(defn page-foot []
  [:div
   [:hr]
   [:p "Github"]]
  )

(defn page-navigation []
  [:div
   [:p
    [:a {:href "/"} "Home"]
    " : "
    [:a {:href "icons"} "Icons"]
    " | "
    [:a {:href "tags"} " Tags"]
    " | "
    [:a {:href "about"} "About"]
    ]
   [:hr]
   ]
  )

(defn page [contents]
  (html5
   (page-head)
   [:body
    (page-navigation)
    contents
    (page-foot)
    ]
   )
  )
