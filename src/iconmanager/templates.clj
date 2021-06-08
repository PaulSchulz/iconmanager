(ns iconmanager.templates
  (:use [hiccup core page])
  (:require [clj-yaml.core :as yaml]))

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
        [:li [:a {:id "fontsinfolder" :href ""} "Scan folder"]]
        [:li [:a {:href "https://github.com/PaulSchulz/iconmanager"} "About"]]
        ]]
      ]]
    [:main
     ;; [:section {:id "icons"}
     ;;  [:ul {:class "iconlist"}
     ;;   [:li
     ;;    [:img {:src "icons/svg/f07d93108f7bffa2d8b6924b11ba6d74ca8aa331.svg"}]
     ;;    [:header "A"]

     ;;    ]]
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
