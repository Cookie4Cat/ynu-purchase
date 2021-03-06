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
        $routeProvider.when("/index", {
            templateUrl: "./public/template/indexTpl.html",
            controller: "indexController"
            })
            .when("/teacherIndex", {
                templateUrl: "./public/template/teaFillOut.html",
                controller: "teaFormCtr"
            })
            .when("/TeaHistory", {
                templateUrl: "./public/template/teaViewHistory.html",
                controller: "historyController"
            })
            .when("/teaViewHanding", {
                templateUrl: "./public/template/teaViewHanding.html",
                controller: "handingController"
            })
            .when("/teaViewDetail", {
                templateUrl: "./public/template/teaViewDetail.html",
                controller: "viewProjectController"
            })
            .when("/teaUpdate",{
                templateUrl: "./public/template/teaUpdateProjectTpl.html",
                controller: "updateController"
            })
            .otherwise({ redirectTo: "/index" });

    }]);

    app.controller("handingController", function($scope, $http,$timeout) {

        //获取待处理列表
        $http.get("/teacher/projects/handling/all?token=" + sessionStorage.getItem("token"))
            .success(function (response) {
               $scope.handle = response;
            });

        //查看项目
        $scope.showProject = function (projectId) {
            location.href = "/index_teacher.html#/teaViewDetail?projectId="+projectId;
        };

        //查看当前项目
        $scope.showCurrentProject = function (projectId) {
            $timeout(function () {
                location.href = "/index_teacher.html#/teaViewDetail?projectId="+projectId;
            },500);
        };

        //重新提交
        $scope.reSubmit = function (projectId) {
            $timeout(function () {
                location.href="/index_teacher.html#/teaUpdate?projectId=" + projectId;
            },500);
        };

        $scope.methods = {
            showDetail: function(message,projectId,action) {
                console.log(message);
                $scope.message = message;
                $scope.action = action;
                $scope.currentProjectId = projectId;
            }
        }

    });
    app.controller('viewProjectController',function ($scope,$http) {
        var url = window.location.toString();
        var projectId = url.substring(url.lastIndexOf('=') + 1, url.length);
        $scope.projectId = projectId;
        $http({
            url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
            method:"get"
        }).success(function(response){
            $scope.project = response;
            $scope.change();
            $scope.xianshi = true;
            console.log($scope.project);
        });

        $scope.change = function() {
            if ($scope.project.purchaseType == "国产" || $scope.project.purchaseType == "进口") {
                $scope.xianshi = true;

            } else if ($scope.project.purchaseType == "C-工程") {
                $scope.xianshi = false;
                $scope.replace = "C-工程";
                for(var i in $scope.project.table){
                    $scope.project.table[i].type = "C-工程";
                }
            } else if ($scope.project.purchaseType == "S-服务") {
                $scope.xianshi = false;
                $scope.replace = "S-服务";
                for(var j in $scope.project.table){
                    $scope.project.table[j].type = "S-服务";
                }
            }
        };
    });

    app.controller('updateController',function ($scope,$http,$timeout) {
        var url = window.location.toString();
        var projectId = url.substring(url.lastIndexOf('=') + 1, url.length);
        $scope.projectId = projectId;
        $http({
            url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
            method:"get"
        }).success(function(response){
            $scope.project = response;
            $scope.xianshi = true;
            if(response.table.length==0)
                $scope.addItem();
            console.log($scope.project);
        });
        $scope.sum = function () {
            var sum = 0;
            $scope.submit = false;
            for(var i=0; i<$scope.project.table.length; i++){
                sum = sum + $scope.project.table[i].totalMoney_real;
            }
            console.log(sum);
            $scope.project.totalMoney_pre =sum;
        };
        $scope.addItem = function() {
            var item = {};
            $scope.project.table.push(item);
        };
        $scope.removeItem = function(index) {
            $scope.project.table.splice(index, 1)
        };
        $scope.clear = function() {
            $scope.project = {table: []};
            $scope.addItem();
        };

        $scope.reSubmit = function () {
            $scope.submit = true;
            swal({
                title: "是否确认提交?",
                text: "您将会提交所填信息",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "提交",
                cancelButtonText:"返回",
                closeOnConfirm: false
            }, function(){
                $scope.submit = true;
                $http({
                    url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
                    method:"post",
                    data: $scope.project
                }).success(function(response) {
                    console.log($scope.project);
                    if (response == "1") {
                        swal("操作成功!", "已成功提交!", "success");
                        $timeout(function() {
                            location.href = "#/teaViewHanding";
                        }, 1000);
                    } else{
                        swal("操作失败!", "系统发生错误!", "error");
                    }
                })
            });
        };
        $scope.change = function() {
            if ($scope.project.purchaseType == "国产" || $scope.project.purchaseType == "进口") {
                $scope.xianshi = true;
            } else if ($scope.project.purchaseType == "C-工程") {
                $scope.xianshi = false;
                $scope.replace = "C-工程";
                for(var i in $scope.project.table){
                    $scope.project.table[i].type = "C-工程";
                }
            } else if ($scope.project.purchaseType == "S-服务") {
                $scope.xianshi = false;
                $scope.replace = "S-服务";
                for(var j in $scope.project.table){
                    $scope.project.table[j].type = "S-服务";
                }
            }
        };
    });

    app.controller("historyController", function($scope, $http) {
        //获取历史列表
        $http.get("/teacher/projects/history/all?token=" + sessionStorage.getItem("token"))
            .success(function(response){
                $scope.historyItems = response;
            });
    });

    app.controller('teaFormCtr', function($scope, $http, $timeout) {
        $scope.project = {table:[]};
        $scope.submit = false;
        $scope.sum = function () {
            var sum = 0;
            for(var i=0; i<$scope.project.table.length; i++){
                sum = sum + $scope.project.table[i].totalMoney_real;
            }
            console.log(sum);
            $scope.project.totalMoney_pre =sum;
        };
        $scope.addItem = function() {
            var item = {};
            $scope.project.table.push(item);
        };
        //添加一行项目设备输入框
        $scope.addItem();
        $scope.removeItem = function(index) {
            $scope.project.table.splice(index, 1)
        };
        $scope.clear = function() {
            swal({
                title: "是否确认清空?",
                text: "您将会清空所填信息",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "清空",
                cancelButtonText:"返回",
                closeOnConfirm: false
            },function () {
                $scope.project = {table:[]};
                $scope.addItem();
            });
        };

        $scope.formSubmit = function() {
            swal({
                title: "是否确认提交?",
                text: "您将会提交所填信息",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "提交",
                cancelButtonText:"返回",
                closeOnConfirm: false
            }, function(){
                $scope.submit = true;
                $http({
                    url: "/teacher/PurchaseApplySheet/submit" + "?token=" + sessionStorage.getItem("token"),
                    method: "post",
                    data: $scope.project
                }).success(function(response) {
                    console.log($scope.project);
                    if (response == "1") {
                        swal("操作成功!", "已成功提交!", "success");
                        $timeout(function() {
                            location.href = "#/teaViewHanding";
                        }, 1000);
                    } else{
                        swal("操作失败!", "系统发生错误!", "error");
                    }
                })
            });
        };
        
        $scope.draftSubmit = function() {
            swal({
                title: "是否确认存入草稿?",
                text: "所有信息会当作草稿保存",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "存入",
                cancelButtonText:"返回",
                closeOnConfirm: false
            }, function(){
                $http({
                    url: "/teacher/PurchaseApplySheet/submitDraft" + "?token=" + sessionStorage.getItem("token"),
                    method: "post",
                    data: $scope.project
                }).success(function(response) {
                    if (response == "1") {
                        swal("操作成功!", "已成功存入!", "success");
                        $timeout(function() {
                            location.href = "#/teaViewHanding";
                        }, 1000);
                    } else{
                        swal("操作失败!", "系统发生错误!", "error");
                    }
                })
            });
        };
        $scope.change = function() {
            if ($scope.project.purchaseType == "国产" || $scope.project.purchaseType == "进口") {
                $scope.xianshi = true;
                for(var n in $scope.project.table){
                    $scope.project.table[n].type = null;
                }
                $scope.project.table
            } else if ($scope.project.purchaseType == "C-工程") {
                $scope.xianshi = false;
                $scope.replace = "C-工程";
                for(var i in $scope.project.table){
                    $scope.project.table[i].type = "C-工程";
                }
            } else if ($scope.project.purchaseType == "S-服务") {
                $scope.xianshi = false;
                $scope.replace = "S-服务";
                for(var j in $scope.project.table){
                    $scope.project.table[j].type = "S-服务";
                }
            }
        };
        $scope.loadDraft = function() {
            $http({
                url: "teacher/PurchaseApplySheet/draft" + "?token=" + sessionStorage.getItem("token"),
                method: "Get"
            }).success(function(response) {
                $scope.project = response;
                $scope.xianshi = true;
                if($scope.project.table.length == 0){
                    $scope.project.table.push({});
                }
                $scope.change();
            })
        };
    });
}(angular, window);
