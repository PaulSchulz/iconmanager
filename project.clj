(defproject iconmanager "0.1.0"
  :description "Display and manage Icons and Images"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]
                 [ring-refresh "0.1.2"]
                 [clj-commons/clj-yaml "0.7.0"]
                 ]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler iconmanager.routes/app})

;; ring-refresh - https://github.com/weavejester/ring-refresh
;;   Used to automatically refresh browser if files are changed.
