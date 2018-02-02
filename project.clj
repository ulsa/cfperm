(defproject cfperm "0.1.0-SNAPSHOT"
  :description "A web service for parsing an Amazon Web Services (AWS) CloudFormation file and returning the AWS permissions needed for running that specific template, so that you can tailor the permissions of an AWS user perfectly."
  :url "http://cfperm.ulsa.cloudbees.net/"
  :min-lein-version "2.0.0"
  :cloudbees-app-id "ulsa/cfperm"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [cheshire "5.8.0"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-cloudbees "1.0.3"]]
  :ring {:handler cfperm.routes/app}
  :profiles
  {:dev {:dependencies [[ring/ring-mock "0.3.2"]
                        [javax.servlet/javax.servlet-api "4.0.0"]]}})
