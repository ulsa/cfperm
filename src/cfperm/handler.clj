(ns cfperm.handler
  (:use compojure.core
        [hiccup core page form])
  (:require [compojure.handler :as handler]
            [compojure.route :as route])
  (:require [cheshire.core :refer :all]))

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
               {}
               "AWS::S3::BucketPolicy"
               {:Action ["s3:PutBucketPolicy"
                         "s3:DeleteBucketPolicy"]
                :Resource :*}
               "AWS::SNS::Topic"
               {:Action ["sns:CreateTopic"
                         "sns:DeleteTopic"
                         "sns:SetTopicAttributes";maybe
                         ]
                :Resource :*}})

(defn show-index
  "Display a description and a form for submitting a CF template here"
  []
  (html5
   [:head
    [:title "CloudFormation Permission Generator"]
    (include-css "/css/style.css")]
   [:body
    [:h1 "Submit Your CloudFormation Template"]
    [:p "Paste in your CloudFormation template here, and you will get back the JSON
 containing the IaM policies that your user running CloudFormation requires in order
 to actually perform the tasks specified in the template."]
    (form-to [:post "/"]
             (label "cftemplate" "CloudFormation Template:")
             [:br]
             (text-area {:cols 100 :rows 20} "cftemplate")
             [:br]
             (submit-button "Submit"))]))

(defn remove-iam [resources]
  (filter #(not (.startsWith % "AWS::IAM::")) resources))

(defn create-policies [json]
  (->> (-> json
           (parse-string true)          ;enable keywords
           (get-in [:Resources]))
       (map (comp :Type val))           ;get all :Types
       distinct
       remove-iam                       ;no need for AWS::IAM stuff
       (map policies)
       (map #(merge % {:Effect :Allow}))
       (assoc {} :Statement)))

(defroutes app-routes
  (GET "/" [] (show-index))
  (POST "/" [cftemplate]
        (html5
         [:head
          [:title "IaM Policies for Your Template"]]
         [:body
          [:pre (generate-string (create-policies cftemplate) {:pretty true})]]))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
