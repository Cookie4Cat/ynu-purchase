! function(angular, window, b) {
    var app = angular.module('myApp', ['ngRoute','smart-table']);

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
            .when("/recorderViewDetail", {
                templateUrl: "/public/template/recorderViewDetail.html",
                controller: "detailController"
            })
            .when("/recorderApproval", {
                templateUrl: "/public/template/recorderApproval.html",
                controller: "approvalController"
            })
            .when("/recorderCreateContract", {
                templateUrl: "/public/template/recorderCreateContract.html",
                controller: "createContractController"
            })
            .otherwise({ redirectTo: "/recorderIndex" });

    }]);
    //首页
    app.controller('indexController', function($scope,$http) {
            $http.get("recorder/plans/handling/all?token=" + sessionStorage.getItem("token"))
                .success(function(response){
                    console.log(response);
                    $scope.handlingPlanList = response;
                });

        $scope.approval = function (pid) {
            location.href = "/index_record.html#/recorderApproval?planId=" + pid;
        };
        $scope.addInfo = function (pid) {
            location.href = "/index_record.html#/recorderCreateContract?planId=" + pid;
        }
    });
    //创建采购批次
    app.controller("createPlanController", function ($scope,$http) {
        $scope.planProjectList = [];
        $scope.readyProjectList = [];
        $scope.plan = {};
        $scope.notAdd = true;
        $scope.sub = false;
        $http.get("/recorder/projects/setup?token="+ sessionStorage.getItem("token"))
            .success(function(response){
                $scope.readyProjectList = response;
            });



        //查看采购项目详情
        $scope.showProject=function (index) {
            $scope.viewProject = $scope.readyProjectList[index];
            console.log(index);
            console.log($scope.viewProject);
        }
        //查看选入的采购项目
        $scope.showPlanProject=function (index) {
            $scope.viewProject = $scope.planProjectList[index];
            console.log(index);
            console.log($scope.viewProject);
        }
        //加入到采购计划
        $scope.addToPlan = function (index) {
            console.log("add to plan " + index);
            $scope.planProjectList.push($scope.readyProjectList[index]);
            $scope.readyProjectList.splice(index,1);
            if($scope.planProjectList.length==0){
                $scope.notAdd = true;
            }else {
                $scope.notAdd=false;
            }
        };
        //从采购计划中移除
        $scope.removeFormPlan = function (index) {
            console.log("remove from plan " + index);
            $scope.readyProjectList.push($scope.planProjectList[index]);
            $scope.planProjectList.splice(index,1);
            if($scope.planProjectList.length==0){
                $scope.notAdd = true;
            }else {
                $scope.notAdd=false;
            }
        };
        //提交
        $scope.submit = function () {
            var projectIdList = [];
            $scope.sub = true;
            for(var i in $scope.planProjectList){
                projectIdList.push($scope.planProjectList[i].projectId);
            }
            console.log($scope.plan);
            console.log(projectIdList);
            $http({
                url: "/recorder/plans?token="+ sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "planId":$scope.plan.planId,
                    "preOrgType":$scope.plan.preOrgType,
                    "prePurchaseType":$scope.plan.prePurchaseType,
                    "projectIdList":projectIdList
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功创建采购批次", "success");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
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
        $http.get("/recorder/plans/history/all?token="+sessionStorage.getItem("token"))
            .success(function (response) {
                console.log(response);
                $scope.historyItems = response;
            });
       });


    //查看采购计划
    app.controller("detailController",function ($scope,$http){
        var url = window.location.toString();
        $scope.planId = url.substring(url.lastIndexOf('=') + 1, url.length);
        $http.get("/recorder/plans/" + $scope.planId + "?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.plan = response;
                console.log($scope.plan);
            });
        $scope.showProject=function (id) {
            $scope.viewProject = $scope.plan.projectsList[id];
            console.log(id);
            console.log($scope.viewProject);
        }
    });


    //批复
    app.controller("approvalController", function ($scope,$http) {
        //获取planId
        var url = window.location.toString();
        $scope.planId = url.substring(url.lastIndexOf('=') + 1, url.length);
        $http.get("/recorder/plans/" + $scope.planId + "?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.plan = response;console.log($scope.plan);
            });
        //查看项目
        $scope.showProject=function (id) {
            $scope.viewProject = $scope.plan.projectsList[id];
            console.log(id);
            console.log($scope.viewProject);
        }
        
        $scope.approval = function (pid) {
            console.log($scope.plan.orgType);
            console.log($scope.plan.purchaseType);
            console.log($scope.plan.preFinishTime);
            $http({
                url: "/recorder/plans/"+pid+"/reply?token="+ sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "orgType":$scope.plan.orgType,
                    "purchaseType":$scope.plan.purchaseType,
                    "preFinishTime":$scope.plan.preFinishTime
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功录入审核结果!", "success");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
                }
            });
        };
        $scope.refuse = function (pid) {
            $http({
                url: "/recorder/plans/"+pid+"/drop?token="+ sessionStorage.getItem("token"),
                method: "post"
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功驳回批次!", "success");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
                }
            });
        }
    });
    //创建合同
    app.controller("createContractController", function ($scope,$http) {
        //获取planId
        var url = window.location.toString();
        $scope.planId = url.substring(url.lastIndexOf('=') + 1, url.length);
        $http.get("/recorder/plans/" + $scope.planId + "?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.plan = response;
                $scope.itemList = [];
                var total = 0;
                for(var i in response.projectsList){
                    total = total + response.projectsList[i].totalMoney_pre;
                    console.log(response.projectsList[i].totalMoney_pre+" "+total);
                    for(var j in response.projectsList[i].table){
                        var item = response.projectsList[i].table[j];
                        item.projectId = response.projectsList[i].projectId;
                        $scope.itemList.push(item);
                    }
                }
                $scope.s = total;
                console.log($scope.itemList);
            });
        $scope.addContract = function () {
            $http({
                url: "/recorder/plans/"+$scope.plan.planId+"/contract?token="+ sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "contractName":$scope.contract.name,
                    "contractId":$scope.contract.number,
                    "biddingCompany":$scope.contract.bidCompany,
                    "bidTime":$scope.contract.bidTime,
                    "negotiateTime":$scope.contract.negotiateTime,
                    "projectList":$scope.plan.projectsList
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功添加合同!", "success");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
                }
            });
        };
        $scope.clear = function () {
            $scope.contract = {};
        }
    });
}(angular, window);
