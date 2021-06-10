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
      [:p "Directory: " (:dir-icons  options)]
      [:p "Metadata: "  (:dir-metadata options)]

      ;;    [:p "List of Icons" (.list (io/file (:dir-icons options)))]

      [:section {:id "icons"}
       [:ul {:class "iconlist"}
        (for [filename files]
          (let [fullfilename (str (:dir-metadata options) filename)
                metadata (read-yaml-svg-file fullfilename)
                sha1     (:sha1 metadata)
                name     (:name metadata)
                ]
            [:li
             [:header [:a {:href (str "/icon/" sha1)}
                       name]]
             [:img {:src (str "/icon/" sha1 ".svg")}]
             ]
            ;;)
            ))
        ]
       ]]
     )
    ))
