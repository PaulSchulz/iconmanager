(ns icons.view-about
  (:use [hiccup core page]
        icons.templates)
  )

(defn about-page [options]
  (page [:div
         [:img {:src "icons-svg/A-round.svg" :align "right"}]
         [:h1 "About 'Icons'"]
         [:p "This software was written to allow the easy manipulation of large"
          "sets of "
          "icon image files which could be used of an sort computing projects."
          ]
         [:p "The main application is to collect icons for the "
          "'Monomatch Myriad' project, an extended game "
          "of \"Spot It\"[TM], which requires 10,303 individual, distinct icons. Icon sets are "
          "available for download on the Internet but their licensing conditions are prohibative. "]
         ]
        ))
