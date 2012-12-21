(ns cfperm.routes
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer :all]
            [cfperm.views :refer :all]))

(defroutes app-routes
  (GET "/" [] (show-index))
  (POST "/" [cftemplate] (generate-policies cftemplate))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
