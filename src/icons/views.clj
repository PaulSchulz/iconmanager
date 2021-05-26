(ns icons.views
  (:use [hiccup core page]
        icons.templates
        )
  (:require [clj-yaml.core :as yaml])
  )

(defn system-summary [options]

  [:table
   [:tr [:td "Icon Repository:"] [:td "resources/public/icons/"]]
   [:tr [:td "Incoming Directory:"]  [:td "incoming/"] [:td "->"] [:td (:dir-incoming options)]]
   [:tr [:td "Processed Images:"]    [:td "svg/"]      [:td "->"] [:td (:dir-archive options)]]
   [:tr [:td "Archived Images:"]     [:td "archive/"]   [:td "->"] [:td (:dir-icons options)]]
   ]
  )

(defn icons-summary []
[:table
 [:tr [:td "Incoming Images"] [:td 0]]
 [:tr [:td "Processed Images"] [:td 0]]
 [:tr [:td "Archived Images"] [:td 0]]
 ]
)

(defn incoming-details [metadata]
  [:table
   [:tr [:td "Name:"]         [:td (:name metadata)]]
   [:tr [:td "Filename:"]     [:td (:filename metadata)]]
   [:tr [:td "Submitted:"]    [:td (:submitted metadata)]]
   [:tr [:td "Submitted By:"] [:td (:submitted-by metadata)]]
   [:tr [:td "SHA1:"]         [:td (:sha1 metadata)]]
   [:tr [:td "Size:"          [:td (:size metadata)]]]
   [:tr [:td "Tags:"]         [:td (str (:tags metadata))]]
   [:tr [:td "Result:"        [:td (str (:results metadata))]]]
   [:tr [:td "Likes:"         [:td (:likes metadata)]]]
   [:tr [:td "Dislikes:"      [:td (:dislikes metadata)]]]
   ]
  )

(defn processed-details [metadata]
  [:table
   [:tr [:td "Name:"]         [:td (:name metadata)]]
   [:tr [:td "SHA1:"]         [:td (:sha1 metadata)]]
   [:tr [:td "Size:"          [:td (:size metadata)]]]
   [:tr [:td "Original:"]     [:td (:original metadata)]]
   [:tr [:td "Processed By:"] [:td (:processed-by metadata)]]
   [:tr [:td "Submitted By:"] [:td (:submitted-by metadata)]]
   [:tr [:td "Tags:"]         [:td (str (:tags metadata))]]
   [:tr [:td "Related:"       [:td (:related metadata)]]]
   [:tr [:td "Voting:"        [:td (:voting metadata)]]]
   [:tr [:td "Likes:"         [:td (:likes metadata)]]]
   [:tr [:td "Dislikes:"      [:td (:dislikes metadata)]]]
   [:tr [:td "Status:"        [:td (:status metadata)]]]
   ]
  )

(defn read-yaml-incoming [name]
  (let [data (yaml/parse-string
              (slurp (str "resources/public/icons/metadata/incoming/"
                          name
                          ".yaml")))]
    data
    )
  )

(defn random-icon []
  )

(defn random-icons [options]
(let [;;metadata (read-yaml-incoming "spacex-dragon2")
      metadata2 (read-yaml-svg "f07d93108f7bffa2d8b6924b11ba6d74ca8aa331")
      ]
  [:table
   [:tr [:th "Incoming"] [:th ""] [:th "Processed"]] [:th ""]
   [:tr
    ;;      [:td [:img {:src (str "icons/incoming/" (:filename metadata))}]]
    ;;      [:td (incoming-details metadata)]
    [:td [:img {:src "icons/svg/f07d93108f7bffa2d8b6924b11ba6d74ca8aa331.svg"}]]
    [:td (processed-details metadata2)]
    ]
   ]))

(defn index-page [options]
(page
 [:div
  [:h1 "Icons"]
  [:h2 "A tool for collecting, managing and presenting large numbers of icons."]
  [:h3 "Random Images"]
  (random-icons options)
  [:h3 "System Summary"]
  (system-summary options)
  [:h3 "Summary"]
  (icons-summary)
  [:h3 "Pages"]
  [:ul
   [:li [:a {:href "icons"} "Icons"]]
   [:li [:a {:href "tags"}  "Tags"]]
   ]
  ]))

;; TODO: routing required
(defn incoming-page []
(page
 [:div
  [:h1 "Submitted Icon: "]
  [:p "Icon"]
  ]))

(defn icon-page [options hash]
  (let [metadata (read-yaml-svg hash)]
    (page
     [:div
      [:h1 "Icon:"]
      [:img {:src (str "/icons/svg/" hash ".svg")}]
      [:table
       (for [key (keys metadata)]
         [:tr [:td key] [:td (key metadata)]])
       ]
      ]
     )))

(defn tags-page []
  (page
   [:div
    [:h1 "Tags"]
    [:p "Tag Cloud"]
    ]))
