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
        [:li [:a {:href "/"} "Welcome"]]
        [:li [:a {:href "/repo"} "Repository"]]
        [:li [:a {:href "/icons"} "Icons"]]
        [:li [:a {:href "/tags"}  "Tags"]]
        [:li [:a {:href "/statistics"} "Statistics"]]
        [:li [:a {:href "/about"} "About"]]
        ]]
      ]]
    [:main
     contents
     [:section {:id "icons"}]
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
(defn scan-directory [directory]
  (.list
   (io/file directory)
   ))

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
