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
        $routeProvider.when("/index", {
            templateUrl: "./public/template/indexTpl.html",
            controller: "indexController"
        })

        .when("/teacherIndex", {
                templateUrl: "./public/template/teaFillOut.html",
                //controller: "workController"
            })
            .when("/TeaHistory", {
                templateUrl: "./public/template/teaViewHistory.html",
                //controller: "CheckMessageController"
            })
            .when("/teaViewHanding", {
                templateUrl: "./public/template/teaViewHanding.html",
                //controller: "CheckMessageController"
            })
            .when("/check", {
                templateUrl: "./public/template/financial_checkTpl.html",
                //controller: "workController"
            })
            .when("/ExaIndex", {
                templateUrl: "./public/template/examinantIndex.html",
                //controller: "workController"
            })
            .when("/ExaDetail", {
                templateUrl: "./public/template/ExaDetail.html",
                //controller: "workController"
            })
            .when("/operatorIndex", {
                templateUrl: "./public/template/operatorIndex.html",
                //controller: "workController"
            })
            .when("/operatorIndex", {
                templateUrl: "./public/template/operatorIndex.html",
                //controller: "workController"
            })
            .when("/negRusult", {
                templateUrl: "./public/template/negotiationResult.html",
                //controller: "workController"
            })
            .when("/operatorHistory", {
                templateUrl: "./public/template/operatorHistory.html",
                controller: "opHistoryController"
            })
            .when("/negotiationList", {
                templateUrl: "./public/template/negotiationList.html",
                //controller: "workController"
            })
            .when("/negotiationCheck", {
                templateUrl: "./public/template/negotiationCheck.html",
                //controller: "workController"
            })
            .when("/operatorHistoryDetail", {
                templateUrl: "./public/template/operatorHistoryDetail.html",
                //controller: "workController"
            })
            .when("/approvalHistory", {
                templateUrl: "./public/template/approvalHistoryTpl.html",
                //controller: "workController"
            })
            .when("/approvalClassify1", {
                templateUrl: "./public/template/approvalClassify1Tpl.html",
                //controller: "workController"
            })
            .when("/approvalClassify2", {
                templateUrl: "./public/template/approvalClassify2Tpl.html",
                //controller: "workController"
            })
            .when("/projectVerify", {
                templateUrl: "./public/template/projectVerify.html",
                //controller: "proworkController"
            })
            .otherwise({ redirectTo: "/index" });

    }]);

    app.controller("indexController", function($scope, $http) {
        location.href = "/login.html";
    })


    app.controller('CheckMessageController', function($scope) {
        $scope.data = "太贵了";
        $scope.myboolean = true;
        $scope.docData = function() {
            $scope.myboolean = !$scope.myboolean;
        }
    });
    app.controller('opHistoryController', function($scope) {
        // $scope.bold = "bold";
        $scope.key = '';
        $scope.data = [
        {id:"cg20160517-04",totalMoney:"32,434",updataTime:"2016-01-17"},
        {id:"cg20150517-04",totalMoney:"32,434",updataTime:"2016-01-17"},
        {id:"ck20160517-04",totalMoney:"32,434",updataTime:"2016-01-17"},
        {id:"cy20130517-04",totalMoney:"32,434",updataTime:"2016-01-17"},
        {id:"cy20130517-04",totalMoney:"32,434",updataTime:"2016-01-17"},
        ];
    });
}(angular, window);
