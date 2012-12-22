(ns cfperm.views
  (:require [cheshire.core :refer :all]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(def policies {"AWS::ElasticLoadBalancing::LoadBalancer"
               {:Action ["elasticloadbalancing:CreateLoadBalancer"
                          "elasticloadbalancing:DeleteLoadBalancer"]
                :Resource :*}
               "AWS::AutoScaling::AutoScalingGroup"
               {:Action ["autoscaling:CreateAutoScalingGroup"
                         "autoscaling:DeleteAutoScalingGroup"]
                :Resource :*}
               "AWS::AutoScaling::LaunchConfiguration"
               {:Action ["autoscaling:CreateLaunchConfiguration"
                         "autoscaling:DeleteLaunchConfiguration"]
                :Resource :*}
               "AWS::AutoScaling::ScalingPolicy"
               {:Action ["autoscaling:PutScalingPolicy"
                         "autoscaling:DeletePolicy"]
                :Resource :*}
               "AWS::CloudWatch::Alarm"
               {:Action ["cloudwatch:PutMetricAlarm"
                         "cloudwatch:DeleteAlarms"]
                :Resource :*}
               "AWS::EC2::SecurityGroup"
               {:Action ["ec2:CreateSecurityGroup"
                         "ec2:DeleteSecurityGroup"
                         "ec2:StartInstances"
                         "ec2:TerminateInstances"]
                :Resource :*}
               "AWS::IAM::User"
               {:Action ["iam:CreateUser"
                         "iam:DeleteUser"]
                :Resource :*}
               "AWS::S3::BucketPolicy"
               {:Action ["s3:PutBucketPolicy"
                         "s3:DeleteBucketPolicy"]
                :Resource :*}
               "AWS::SNS::Topic"
               {:Action ["sns:CreateTopic"
                         "sns:DeleteTopic"
                         "sns:ListTopics"
                         "sns:ListSubscriptionsByTopic"
                         "sns:Subscribe"
                         ]
                :Resource :*}})

(defn header []
  [:div.navbar.navbar-inverse.navbar-fixed-top
   [:div.navbar-inner
    [:div.container
     [:a.brand {:href "/"} "CloudFormation Permission Generator"]
     [:div.nav-collapse.collapse
      [:ul.nav
       [:li.active [:a {:href "/"} "Home"]]
       [:li.active [:a {:href "/about"} "About"]]]]]]])

(defn template [& body]
  (html5
   [:head
    [:title "CloudFormation Permission Generator"]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    (include-css "/css/bootstrap.min.css")]
   [:body {:style "padding-top:60px;"}
    (header)
    [:div.container
     body]]))

(defn index-page
  "Display a description and a form for submitting a CF template here"
  []
  (template
   [:div {:class "hero-unit"}
    [:h1 "CloudFormation Template"]
    [:p "Given a CloudFormation template, you will get back the JSON
 containing the IaM policies that your user running CloudFormation requires
 in order to actually perform the tasks specified in the template."]
    (form-to {:class "well"}
             [:post "/"]
             (label "cftemplate" "Paste CloudFormation Template here:")
             [:br]
             (text-area {:class "span10" :rows 20} "cftemplate")
             [:br]
             (submit-button "Submit"))]))

(defn about-page []
  (template
   [:div {:class "well"}
    [:h1 "About This:"]
    [:p "This Clojure project was developed by developers from "
     [:a {:href "http://www.jayway.com/"} "Jayway"]
     "."]
     [:h1 "About CloudFormation:"]
      "Read about what CloudFormation is "
     [:a {:href "https://aws.amazon.com/cloudformation"} "at Amazon Web Services"]

     ]))

(defn remove-iam [resources]
  (filter #(not (.startsWith % "AWS::IAM::")) resources))

(defn add-iam-user [resources]
  (conj resources "AWS::IAM::User"))

(defn create-policies [json]
  (->> (-> json
           (parse-string true)          ;enable keywords
           (get-in [:Resources]))
       (map (comp :Type val))           ;get all :Types
       distinct
       remove-iam                       ;no need for AWS::IAM stuff
       ;add-iam-user                     ;except possibly create/delete user
       (map policies)
       (map #(merge % {:Effect :Allow}))
       (assoc {} :Statement)))

(defn generate-policies [templ]
  (template
   [:div {:class "hero-unit"}
    [:pre (generate-string (create-policies templ) {:pretty true})]]))
