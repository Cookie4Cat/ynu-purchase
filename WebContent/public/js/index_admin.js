! function(angular, window) {
    var app = angular.module('myApp', ['ngRoute','smart-table']);

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
        $routeProvider.when("/adminIndex", {
                templateUrl: "/public/template/adminIndex.html",
                controller: "indexController"
            })
            .when("/adminHistory",{
                templateUrl: "/public/template/approvalHistoryTpl.html",
                controller: "historyController"
            })
            .when("/adminVerify", {
                templateUrl: "/public/template/ExaDetail.html",
                controller: "verifyController"
            })
            .when("/adminSetUp",{
                templateUrl: "/public/template/projectVerify.html",
                controller: "setUpController"
            })
            .when("/adminView",{
                templateUrl: "/public/template/adminView.html",
                controller: "viewController"
            })
            .otherwise({ redirectTo: "/index" });

    }]);

    //admin首页控制器
    app.controller('indexController', function($scope, $http) {
        //获取待处理列表
        $http.get("admin/projects/handling/all?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.handlingProjectList = response;
            });
        //审核
        $scope.verify = function(pid){
            window.location.href='#/adminVerify?projectId='+ pid;
        };
        //立项
        $scope.setUp = function (pid) {
            window.location.href='#/adminSetUp?projectId='+ pid;
        }
    });
    
    //admin历史控制器
    app.controller('historyController', function($scope, $http) {
        //获取历史列表
        $http.get("admin/projects/history/all?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
                $scope.handlingProjectList = response;
            });
        //查看
        $scope.view = function (pid) {
            location.href = "/index_admin.html#/adminView?projectId=" + pid;
        }
    });

    app.controller('viewController', function($scope, $http) {
        //获取pid
        var url = window.location.toString();
        $scope.projectId = url.substring(url.lastIndexOf('=') + 1, url.length);

        //根据pid获取项目实体
        $http.get("/admin/projects/" + $scope.projectId + "?token="+ sessionStorage.getItem("token") )
            .success(function(response) {
                $scope.project = response;
            });
    });

    //审核控制器
    app.controller('verifyController', function($scope, $http) {
        //获取pid
        var url = window.location.toString();
        $scope.submit = false;
        $scope.projectId = url.substring(url.lastIndexOf('=') + 1, url.length);

        //根据pid获取项目实体
        $http.get("/admin/projects/" + $scope.projectId + "?token="+ sessionStorage.getItem("token") )
            .success(function(response) {
                $scope.project = response;
            });
        //审核通过
        $scope.approve = function() {
            $scope.submit = true;
            $http({
                url: "/admin/projects/"+$scope.projectId+"/suggestion?token=" + sessionStorage.getItem("token"),
                method: "post",
                data:{
                    "content":$scope.suggestion,
                    "flag":"approve"
                }
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功通过!", "success");
                    location.href = "/index_admin.html#/adminIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
                }
            });
        };
        //审核不通过
        $scope.refuse = function() {
            $scope.submit = true;
                $http({
                    url: "/admin/projects/"+$scope.projectId+"/suggestion?token=" + sessionStorage.getItem("token"),
                    method: "post",
                    data:{
                        "content":$scope.suggestion,
                        "flag":"refuse"
                    }
                }).success(function(response) {
                    $scope.data = response;
                    if(response == 1){
                        swal("操作成功!", "已成功驳回!", "success");
                        location.href = "/index_admin.html#/adminIndex";
                    }else{
                        swal("操作失败!", "系统发生错误!", "error");
                    }
                })
        }
    });
    app.controller('setUpController', function($scope, $http) {
        //获取pid
        var url = window.location.toString();
        $scope.projectId = url.substring(url.lastIndexOf('=') + 1, url.length);

        //根据pid获取项目实体
        $http.get("/admin/projects/" + $scope.projectId + "?token="+ sessionStorage.getItem("token") )
            .success(function(response) {
                $scope.project = response;
            });
        //立项
        $scope.setUp = function () {
            $http({
                url: "/admin/projects/"+$scope.projectId+"/setup?token=" + sessionStorage.getItem("token"),
                method: "post"
            }).success(function(response) {
                $scope.data = response;
                if(response == 1){
                    swal("操作成功!", "已成功立项!", "success");
                    location.href = "/index_admin.html#/adminIndex";
                }else{
                    swal("操作失败!", "系统发生错误!", "error");
                }
            })
        }
    });
}(angular, window);
