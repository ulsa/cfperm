(ns cfperm.test.routes
  (:use clojure.test
        ring.mock.request  
        cfperm.routes))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "CloudFormation"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404))))
    
  (testing "post some json"
    (let [response (app (request :post "/" {"cftemplate" "{\"Resources\": {\"ElasticLoadBalancer\": {\"Type\": \"AWS::ElasticLoadBalancing::LoadBalancer\"}}}"}))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "elasticloadbalancing:CreateLoadBalancer")))))