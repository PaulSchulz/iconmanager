(ns iconmanager.view-icons
  (:use [hiccup core page]
        iconmanager.templates)
  (:require [clojure.java.io :as io]))

(defn scan-directory [metadata]
  (.list
   (io/file metadata)
   ))

(defn icons-page [options]
  (let [files (filter (fn [x] (not= x "incoming"))
                      (scan-directory (:dir-metadata options)))]
    (page
     [:div
      [:h1 "Icons"]
      [:p "Number of Icons: " (count files)]
      ;;    [:p "List of Icons" (.list (io/file (:dir-icons options)))]

      [:section {:id "icons"}
       [:ul {:class "iconlist"}
        (for [filename files]
          (let [metadata (read-yaml-svg-file filename)
                sha1 (:sha1 metadata)]
            [:li
             [:header "icon"]
             [:img {:src (str "/icons/svg/" sha1 ".svg")}]
             ]
            )
          )
        ]]]
     )
    ))
