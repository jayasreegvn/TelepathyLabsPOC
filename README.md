# TelepathyLabsPOC

docker pull jayasreegvn/planfinder:1.0.0
docker run -p 8080:8080 --name samplepoccontainer jayasreegvn/planfinder:1.0.0

Swagger URL : http://localhost:8080/swagger-ui/index.html

Question2 + BonusQuestion

API URL : http://localhost:8080/api/plans
Type:POST
Request{
file:"D:\CodingworkArea\SamplePlanSelection.txt",
"featuresRequired":"voice,database,admin"
}
Response
{
    "planCost": 235,
    "plansToBeSelected": "PLAN1,PLAN4"
}
