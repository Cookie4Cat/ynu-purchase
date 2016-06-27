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
        //获取总数以分页
        $scope.getPageCount = function (url) {
            $http.get(url)
                .success(function(response){
                    $scope.indexList = [];
                    $scope.pageNum = Math.ceil(response/8);
                    if($scope.pageNum<=1){
                        $scope.hidePagination = true;
                    }
                    for(var i=0;i<$scope.pageNum;i++){
                        $scope.indexList.push(i+1);
                    }
                });
        };

        //根据页码获取项目列表
        $scope.getProjectList = function (pageNum) {
            $http.get("/recorder/plans/handling?token=" + sessionStorage.getItem("token")
                + "&countPerPage=8&pageNum=" + pageNum
            ).success(function (response) {
                $scope.handlingPlanList = response;
            });
        };

        //进入页面先加载第一页
        $scope.getPageCount("/recorder/plans/handling/count?token=" + sessionStorage.getItem("token"));
        $scope.getProjectList(1);

        //换页
        $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            $scope.currentPageNum = pageNum;
            $scope.getProjectList(pageNum);
        };
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
        $http.get("/recorder/projects/setup?token="+ sessionStorage.getItem("token"))
            .success(function(response){
                $scope.readyProjectList = response;
            });

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
                    alert("操作成功");
                    location.href = "/index_record.html#/recorderIndex";
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
        $scope.currentPageNum = 1;
        $http.get("/recorder/plans/history/count?token="+sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.indexList = [];
                console.log(response);
                $scope.pageNum = Math.ceil(response/8);
                if($scope.pageNum<=1){
                    $scope.hidePagination = true;
                }
                for(var i=0;i<$scope.pageNum;i++){
                    $scope.indexList.push(i+1);
                }
            });

        
        $scope.getHistoryList = function (pageNum) {
            $http.get("/recorder/plans/history?token=" + sessionStorage.getItem("token")
                + "&pageNum=" + pageNum + "&countPerPage=8")
                .success(function (response) {
                    $scope.historyItems = response;
                    console.log(response);
                });
        }
        //获取第一页
        $scope.getHistoryList($scope.currentPageNum);
        
        $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            $scope.getProjectList(pageNum);
            $scope.currentPageNum = pageNum;
        };
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
        $scope.approval = function (pid) {
            $http({
                url: "/recorder/plans/"+pid+"/reply?token="+ sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "orgType":$scope.plan.orgType,
                    "purchaseType":$scope.plan.purchase
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    alert("操作成功");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    alert("运行出错");
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
                    alert("操作成功");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    alert("运行出错");
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
                for(var i in response.projectsList){
                    for(var j in response.projectsList[i].table){
                        var item = response.projectsList[i].table[j];
                        item.projectId = response.projectsList[i].projectId;
                        $scope.itemList.push(item);
                    }
                }
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
                    alert("操作成功");
                    location.href = "/index_record.html#/recorderIndex";
                }else{
                    alert("运行出错");
                }
            });
        };
        $scope.clear = function () {
            $scope.contract = {};
        }
    });
}(angular, window);
