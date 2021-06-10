(ns iconmanager.view-about
  (:use [hiccup core page]
        iconmanager.templates)
  )

(defn about-page [options]
  (page
   [:div {:class "text"}
    [:h1 "About 'Icon Manager'"]
    [:p "This software was written to allow the easy manipulation of large "
     "sets of icon image files which could be used of an sort computing "
     "projects." ]
    [:p "The main application is to collect icons for the "
     [:a {:href "https://github.com/PaulSchulz/monomatch-myriad"} "Monomatch Myriad"] " project"
     "which is an enlarged monomatch game "
     "and requires 10,303 individual distinct icons. (Icon sets are "
     "available for download on the Internet but their licensing conditions are prohibitive.) "
     ]
    [:p "The code for this project is available on "
     [:a {:href "https://github.com/PaulSchulz/iconmanager"} "Github" "."]]
    ]
   ))
