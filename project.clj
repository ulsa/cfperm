(defproject cfperm "0.1.0-SNAPSHOT"
  :description "A web service for parsing an Amazon Web Services (AWS) CloudFormation file and returning the AWS permissions needed for running that specific template, so that you can tailor the permissions of an AWS user perfectly."
  :url "http://cfperm.ulsa.cloudbees.net/"
  :cloudbees-app-id "ulsa/cfperm"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.3"]
                 [hiccup "1.0.0"]
                 [cheshire "5.0.1"]]
  :plugins [[lein-ring "0.7.5"]
            [lein-cloudbees "1.0.3"]]
  :ring {:handler cfperm.routes/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
