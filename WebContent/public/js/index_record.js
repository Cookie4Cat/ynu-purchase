! function(angular, window, b) {
    var app = angular.module('myApp', ['ngRoute']);

    app.directive("navbar", function() {
        return {
            restrict: "E",
            templateUrl: './public/template/nav.html',
            replace: true
        }
    });
    app.directive("foot", function() {
        return {
            restrict: "E",
            templateUrl: './public/template/footerTpl.html',
            replace: true
        }
    });


    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when("/recorderIndex", {
                templateUrl: "/public/template/recorderIndex.html",
                controller: "indexController"
            })
            .when("/recorderCreatePlan", {
                templateUrl: "/public/template/recorderCreatePlan.html",
                controller: "createPlanController"
            })
            .when("/recorderHistory", {
                templateUrl: "/public/template/recorderHistory.html",
                controller: "historyController"
            })
            .when("/recorderApproval", {
                templateUrl: "/public/template/recorderApproval.html",
                controller: "approvalController"
            })
            .when("/recorderCreateContract", {
                templateUrl: "/public/template/recorderCreateContract.html",
                controller: "createContractController"
            })
            .otherwise({ redirectTo: "/index" });

    }]);
    //首页
    app.controller('indexController', function($scope,$http) {

    });
    //创建采购批次
    app.controller("createPlanController", function ($scope,$http) {
        $scope.planProjectList = [];
        $scope.readyProjectList = [];
        $scope.plan = {};
        //加入到采购计划
        $scope.addToPlan = function (index) {
            console.log("add to plan " + index);
            $scope.planProjectList.push($scope.readyProjectList[index]);
            $scope.readyProjectList.splice(index,1);
        };
        //从采购计划中移除
        $scope.removeFormPlan = function (index) {
            console.log("remove from plan " + index);
            $scope.readyProjectList.push($scope.planProjectList[index]);
            $scope.planProjectList.splice(index,1);
        };
        //提交
        $scope.submit = function () {
            var projectIdList = [];
            for(var i in $scope.planProjectList){
                projectIdList.push($scope.planProjectList[i].projectId);
            }
            $http({
                url: "/recorder/plans?token="+ sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "number":$scope.plan.number,
                    "organization":$scope.plan.organization,
                    "purchase":$scope.plan.purchase,
                    "projectIdList":projectIdList
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    alert("操作成功");
                    location.href = "/index_recorder.html#/recorderIndex";
                }else{
                    alert("运行出错");
                }
            });
        };
        //清空
        $scope.clear = function () {
            $scope.plan = {};
        }
    });
    //历史纪录
    app.controller("historyController", function ($scope,$http) {

    });
    //批复
    app.controller("approvalController", function ($scope,$http) {

    });
    //创建合同
    app.controller("createContractController", function ($scope,$http) {

    });
}(angular, window);
