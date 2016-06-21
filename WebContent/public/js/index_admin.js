! function(angular, window, b) {
    var app = angular.module('myApp', ['ngRoute']);

    app.directive("navbar", function() {
        return {
            restrict: "E",
            templateUrl: '/public/template/nav.html',
            replace: true
        }
    });
    app.directive("foot", function() {
        return {
            restrict: "E",
            templateUrl: '/public/template/footerTpl.html',
            replace: true
        }
    });


    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when("/index", {
            templateUrl: "/public/template/indexTpl.html",
            controller: "indexController"
        })

        .when("/teacherIndex", {
                templateUrl: "/public/template/teaFillOut.html",
                controller: "teaFormCtr"
            })
            .when("/TeaHistory", {
                templateUrl: "/public/template/teaViewHistory.html",
                controller: "CheckMessageController"
            })
            .when("/teaViewHanding", {
                templateUrl: "/public/template/teaViewHanding.html",
                controller: "handingController"
            })
            .when("/check", {
                templateUrl: "/public/template/financial_checkTpl.html",
                //controller: "workController"
            })
            .when("/ExaIndex", {
                templateUrl: "/public/template/examinantIndex.html",
                controller: "exaController"
            })
            .when("/ExaDetail", {
                templateUrl: "/public/template/ExaDetail.html",
                controller: "exaDetailController"
            })
            .when("/operatorIndex", {
                templateUrl: "/public/template/operatorIndex.html",
                //controller: "workController"
            })
            .when("/operatorIndex", {
                templateUrl: "/public/template/operatorIndex.html",
                //controller: "workController"
            })
            .when("/negRusult", {
                templateUrl: "/public/template/negotiationResult.html",
                //controller: "workController"
            })
            .when("/operatorHistory", {
                templateUrl: "/public/template/operatorHistory.html",
                controller: "opHistoryController"
            })
            .when("/negotiationList", {
                templateUrl: "/public/template/negotiationList.html",
                //controller: "workController"
            })
            .when("/negotiationCheck", {
                templateUrl: "/public/template/negotiationCheck.html",
                //controller: "workController"
            })
            .when("/operatorHistoryDetail", {
                templateUrl: "/public/template/operatorHistoryDetail.html",
                //controller: "workController"
            })
            .when("/approvalHistory", {
                templateUrl: "/public/template/approvalHistoryTpl.html",
                controller: "appController"
            })
            .when("/approvalClassify1", {
                templateUrl: "/public/template/approvalClassify1Tpl.html",
                //controller: "workController"
            })
            .when("/approvalClassify2", {
                templateUrl: "/public/template/approvalClassify2Tpl.html",
                //controller: "workController"
            })
            .when("/projectVerify", {
                templateUrl: "/public/template/projectVerify.html",
                controller: "projectVerifyController"
            })
            .otherwise({ redirectTo: "/index" });

    }]);
    /**
     *  刘嘉航 任皓 2016-06-19
     *  起始
     */
    //token 服务

    //index
    app.controller('appController', function($scope, $http) {
        $http({
            url: "/admin/projects?token=" + sessionStorage.getItem("token") + "&currentPage=1",
            method: "get",
        }).success(function(response) {
            console.log(response);
            $scope.historyItems = response;
        })
        $scope.query = function() {
            $http({
                url: "/teacher/history/completed?token=" + sessionStorage.getItem("token") + "&projectId=" + $scope.projectId + "&purchaseType=" + $scope.purchaseType + "&proType=" + $scope.proType,
                method: "get",
            }).success(function(response) {
                $scope.historyItems = response;
            })
        }
        $scope.view=function(projectid){

            window.location.href='#/projectVerify?projectId='+projectid;
        }
    });
    //exaController
    app.controller('exaController', function($scope, $http) {
        console.log("i am here");
        $http({
            url: "/admin/projects?token=" + sessionStorage.getItem("token") + "&currentPage=1",
            method: "get",
        }).success(function(response) {
            $scope.historyItems = response;
        })
        $scope.query = function() {
            $http({
                url: "/teacher/history/completed?token=" + sessionStorage.getItem("token") + "&projectId=" + $scope.projectId + "&purchaseType=" + $scope.purchaseType + "&proType=" + $scope.proType,
                method: "get",
            }).success(function(response) {
                $scope.historyItems = response;
            })
        }
        $scope.view=function(projectid){

            window.location.href='#/ExaDetail?projectId='+projectid;
        }

    });

    //exaDetailController
    app.controller('exaDetailController', function($scope, $http) {
        var url = window.location.toString();
        var num = url.substring(url.lastIndexOf('=') + 1, url.length);
        $scope.projectId = num;
        console.log("i am here");
        $http({
            url: "/admin/projects/" + $scope.projectId + "?token="+ sessionStorage.getItem("token") ,
            method: "get",
        }).success(function(response) {
            console.log(response);
            $scope.historyItems = response;
        })
        $scope.approve = function() {
            $http({
                url: "/admin/projects/"+$scope.projectId+"/suggestion?token=" + sessionStorage.getItem("token")+
                "&suggestion=" + $scope.suggestion + "&result=approve",
                method: "post",
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    alert("操作成功");
                    location.href = "/index_admin.html#/ExaIndex";
                }else{
                    alert("运行出错");
                }
            })
        }
        $scope.refuse = function() {
                $http({
                    url: "/admin/projects/"+$scope.projectId+"/suggestion?token=" + sessionStorage.getItem("token")+
                    "&suggestion=" + $scope.suggestion + "&result=refuse",
                    method: "post",
                }).success(function(response) {
                    $scope.data = response;
                    if(response == 1){
                        alert("操作成功");
                        location.href = "/index_admin.html#/ExaIndex";
                    }else{
                        alert("运行出错");
                    }
                })
        }
    });

    // //projectVerifyController
    // app.controller('exaDetailController', function($scope, $http) {
    //     var url = window.location.toString();
    //     var num = url.substring(url.lastIndexOf('=') + 1, url.length);
    //     $scope.projectId = num;
    //     $http({
    //         url: "/teacher/history/completed?token=" + sessionStorage.getItem("token") + "&currentPage=1" + "&projectId=" + $scope.projectId,
    //         method: "get",
    //     }).success(function(response) {
    //         $scope.historyItems = response;
    //     })
    //     $scope.verify = function() {
    //         $http({
    //             url: "/teacher/history/completed?token=" + sessionStorage.getItem("token") + "&projectId=" + $scope.projectId ,
    //             method: "get",
    //         }).success(function(response) {
    //             $scope.data = response;
    //         })
    //     }
    // });
}(angular, window);
