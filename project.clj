(defproject icons "0.1.0"
  :description "Display an manage Icons and Images"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]
                 [ring-refresh "0.1.2"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler icons.routes/app})

;; ring-refresh - https://github.com/weavejester/ring-refresh
;;   Used to automatically refresh browser if files are changed.
