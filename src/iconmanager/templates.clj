(ns iconmanager.templates
  (:use [hiccup core page])
  (:require [clj-yaml.core :as yaml]))

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn read-yaml-svg [hash]
  (let [data (yaml/parse-string
              (slurp
               (str "resources/public/icons/metadata/" hash ".yaml")))]
    data
    )
  )

(defn read-yaml-svg-file [filename]
(let [data (yaml/parse-string
(slurp
(str "resources/public/icons/metadata/" filename)))]
data
)
)
