(ns iconmanager.view-icons
  (:use [hiccup core page]
        iconmanager.templates)
  (:require [clojure.java.io :as io]))

(defn scan-directory [directory]
  (.list
   (io/file directory)
   ))

(defn icon-box [hash name]
  [:li
   [:a {:href (str "/icon/" hash)}
    [:img {:src (str "/icon/" hash ".svg")}]
    [:header name]
    ]]
  )

(defn icons-page [options]
  (let [files (filter (fn [x] (not= x "templates"))
                      (scan-directory (:dir-icons options)))]
    (page
     [:div
      [:h1 "Icons"]
      [:p "Number of Icons: " (count files)]
      [:p "Directory: " (:dir-icons  options)]
      [:p "Metadata: "  (:dir-metadata options)]

      [:section {:id "icons"}
       [:ul {:class "iconlist"}
        (for [filename files]
          (let [hash         (clojure.string/replace filename #".svg" "")
                metadatafile (str (:dir-metadata options)
                                  hash ".yaml")
                metadata (read-yaml-svg-file metadatafile)
                name     (:name metadata)
                ]
            (icon-box hash name)
            ))
        ]
       ]]
     )
    ))

;; Individual Icon details
(defn icon-page [options hash]
  (let [metadata (read-yaml-svg options hash)]
    (page
     [:div
      [:h1 "Icon:"]
      [:ul {:class "iconlist"}
       (icon-box hash (:name metadata))
       ]
      (if (empty? metadata)
        [:div
         [:p "Create a metadata file for " hash]
         [:pre
          "cd " (:dir-metadata options) "\n"
          "cp templates/template.yaml " hash ".yaml && \\" "\n"
          "emacs -nw " hash ".yaml" "\n"
          "\n"]
         ]
        [:table
         (for [key (keys metadata)]
           [:tr [:td key] [:td (key metadata)]])
         ]
        )
      ]
     )))
