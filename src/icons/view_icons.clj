(ns icons.view-icons
  (:use [hiccup core page]
        icons.templates)
  (:require [clojure.java.io :as io]))

(defn scan-directory [icons]
  (.list (io/file "resources/public/icons/svg")
         ))

;; TODO: Change this to scan the files in 'metadata' for filenames etc.
(defn icons-page [options]
  (let [files (scan-directory {})]
    (page
     [:div
      [:h1 "Icons"]
      [:p "Directory:" (:dir-icons options)]
      [:p "Number of Icons"]
      ;;    [:p "List of Icons" (.list (io/file (:dir-icons options)))]
      (for [filename files]
        [:div
         [:a {:href (str "icon/" "f07d93108f7bffa2d8b6924b11ba6d74ca8aa331")}
          [:img {:src (str "icons/svg/" filename)}]]
         ]
        )
      ])))
