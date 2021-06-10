(ns iconmanager.templates
  (:use [hiccup core page])
  (:require [clj-yaml.core :as yaml]
            [clojure.java.io :as io]
            ))

(defn page-head []
  [:head
   [:title "Icon Manager"]
   (include-css "/styles.css")
   ;; (include-css "/css/style.css")
   ]
  )

(defn page-header []
  [:header
   [:h1 "Icon Manager"]
   [:section {:id "status"}]
   [:aside
    [:nav
     [:ul
      [:li "A"]
      ]]
    ]]
  )

(defn page-main []
  [
   [:section {:id "icons"}]
   [:section {:id "browser"}]
   ]
  )

(defn page-footer []
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
  " | "
  [:a {:href "index.html"} "(New) Index"]
  ]
 [:hr]
 ]
)

(defn page [contents]
  (html5
   [:head
    [:title "Icon Manager"]
    (include-css "/styles.css")
    ;; (include-css "/css/style.css")
    ]
   [:body
    [:header
     [:h1 "Icon Manager"]
     [:section {:id "status"} ""]
     [:aside
      [:nav
       [:ul
        [:li [:a {:href "/statistics"} "Statistics"]]
        [:li [:a {:href "/icons"} "Icons"]]
        [:li [:a {:id "fontsinfolder" :href ""} "Scan folder"]]
        [:li [:a {:href "https://github.com/PaulSchulz/iconmanager"} "About"]]
        ]]
      ]]
    [:main
     contents
     [:section {:id "browser"}]
     ]
    [:footer
     [:a {:href ""} "Github"]
     ]
    ]
   )
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Utilities
(defn read-yaml-svg-file [filename]
  (if (.exists (io/as-file filename))
    (let [data (yaml/parse-string (slurp filename))]
      data
      )
    {}
    )
  )

(defn read-yaml-svg [options hash]
  (let [data (read-yaml-svg-file
              (str (:dir-metadata options) hash ".yaml"))]
    data
    )
  )
