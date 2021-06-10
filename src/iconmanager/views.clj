(ns iconmanager.views
  (:use [hiccup core page]
        iconmanager.templates
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
  [:table {:id "metadata"}
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

(defn index-page [options]
  (page
   [:div {:class "text"}
    [:h1 "Welcome"]
    [:p "Icon Manager is a tool for collecting and managing a large repository of icons"]
    [:hr]
    [:p [:b "Repository: "] (:dir-repo options)]
    [:h3 "Summary"]
    [:table
     [:tr [:td "Icons"]    [:td {:align "right"} (count (scan-directory (:dir-icons options)))]]
     [:tr [:td "Incoming"] [:td {:align "right"} (count (scan-directory (:dir-incoming options)))]]
     ]
    ]))

;; TODO Put this into a database
(defn tags-collect [options]
  (let [files (filter (fn [x] (not= x "templates")) (scan-directory (:dir-metadata options)))
        tags {}]
    (map (fn [l] [:li l])
         (sort (flatten (map (fn [f]
                               (:tags (read-yaml-svg-file
                                       (str (:dir-metadata options) f))))
                             files))))
    ))

(defn tags-page [options]
  (page
   [:div
    [:div {:class "text"}
     [:h1 "Tags"]
     [:p "Tag Cloud"]
     ]
    [:ul {:class "taglist"} (tags-collect options)]
    ]))

;; Serve SVG file
(defn icon-image [options hash]
  (let [data
        (slurp (str (:dir-icons options) hash ".svg"))]
    {:status 200
     :headers {"Content-type" "image/svg+xml"}
     :body data}
    )
  )
