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
    /**
     *  王浩 2016-06-12
     *  起始
     */
    app.controller("handingController", function($scope, $http) {
        $scope.currentPageNum = 1;
        $http.get("/teacher/projects/handling/count?token" + sessionStorage.getItem("token"))
            .success(function(response){
                console.log("response " + response);
                $scope.indexList = [];
                $scope.pageNum = Math.ceil(response/8);
                if($scope.pageNum<=1){
                    $scope.hidePagination = true;
                }
                for(var i=0;i<$scope.pageNum;i++){
                    $scope.indexList.push(i+1);
                }

            });
        $scope.getProjectList = function (currentPageNum) {
            $http.get("/teacher/projects/handling?token=" + sessionStorage.getItem("token")
                    + "&pageNum=" + currentPageNum + "&countPerPage=8")
                .success(function (response) {
                    console.log("response " + response);
                    $scope.handle = response;
                });
        };
        $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            $scope.getProjectList(pageNum);
            console.log(pageNum);
            $scope.currentPageNum = pageNum;
        };
        
        $scope.showProject = function (projectId) {
            location.href = "/index_teacher.html#/teaViewDetail?projectId="+projectId;
        };
        $scope.showCurrentProject = function () {
            console.log("show " + $scope.currentProjectId);
            history.go(0);
            $scope.showProject($scope.currentProjectId);
        };

        //此处获取第一页
        $scope.getProjectList($scope.currentPageNum);

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
        {
            var url = window.location.toString();
            var projectId = url.substring(url.lastIndexOf('=') + 1, url.length);
            $scope.projectId = projectId;
            $http({
                url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
                method:"get"
            }).success(function(response){
                $scope.project = response;
                $scope.xianshi = true;
            })
        }
    });

    app.controller('updateController',function ($scope,$http) {
            var url = window.location.toString();
            var projectId = url.substring(url.lastIndexOf('=') + 1, url.length);
            $scope.projectId = projectId;
            $http({
                url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
                method:"get"
            }).success(function(response){
                $scope.project = response;
                $scope.xianshi = true;
                console.log($scope.project);
            });
          $scope.addItem = function() {
            var item = {};
            item['type'] = "";
            item['name'] = "";
            item['count'] = "";
            item['unit'] = "";
            item['budget'] = "";
            item['totalMoney_real'] = "";
            item['address'] = "";
            $scope.project.table.push(item);
        }
           $scope.removeItem = function(index) {
            $scope.project.table.splice(index, 1)
           }
          $scope.clear = function() {
            if (confirm('是否确认清空？')) {
                $scope.project = {table:[]};
                $scope.addItem();
            } else {}
           }

           $scope.reSubmit = function () {
               $http({
                   url:"/teacher/projects/"+projectId+"?token="+sessionStorage.getItem("token"),
                   method:"post",
                   data: $scope.project
               }).success(function(response){
                   if(response == 1){
                       alert("提交成功");
                       window.href = "#/teaViewHanding";
                   }else{
                       alert("提交失败！！！");
                   }
               })
           }
    });
/**
 * 结束
 */


    app.controller("historyController", function($scope, $http) {
        $scope.currentPageNum = 1;
        $http.get("/teacher/history/completed/count?token" + sessionStorage.getItem("token"))
            .success(function(response){
                console.log("response " + response);
                $scope.indexList = [];
                $scope.pageNum = Math.ceil(response/8);
                if($scope.pageNum<=1){
                    $scope.hidePagination = true;
                }
                for(var i=0;i<$scope.pageNum;i++){
                    $scope.indexList.push(i+1);
                }
            });

           $scope.getCompletedList = function (currentPageNum) {
            $http.get("/teacher/history/completed?token=" + sessionStorage.getItem("token")
                + "&pageNum=" + currentPageNum + "&countPerPage=8")
                .success(function (response) {
                    console.log("response " + response);
                    $scope.historyItems = response;
                });
          };

            $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            $scope.getCompletedList(pageNum);
            console.log(pageNum);
            $scope.currentPageNum = pageNum;
        };

        $scope.getCompletedList($scope.currentPageNum);
    });

    app.controller('teaFormCtr', function($scope, $http, $timeout, $rootScope) {
        $scope.form = {};
        $scope.items = [];

        $rootScope.addItem = function() {
            var item = {};
            item['type'] = "";
            item['name'] = "";
            item['count'] = "";
            item['unit'] = "";
            item['budget'] = "";
            item['totalMoney_real'] = "";
            item['address'] = "";
            $scope.items.push(item);
        }
        $scope.addItem();
        $scope.removeItem = function(index) {
            $scope.items.splice(index, 1)
        }
        $scope.clear = function() {
            if (confirm('是否确认清空？')) {
                $scope.form = {};
                $scope.items = [];
                $scope.addItem();
            } else {}
        }

        $scope.formSubmit = function() {
            if (confirm('是否确认提交？')) {

                console.log($scope.form, $scope.items, $scope.form.type);

                $http({
                    url: "/teacher/PurchaseApplySheet/submit" + "?token=" + sessionStorage.getItem("token"),
                    method: "post",
                    data: {
                        "purchaseType": $scope.form.type,
                        "projectName": $scope.form.name,
                        "leader": $scope.form.leader,
                        "m_tel": $scope.form.telephone,
                        "s_tel": $scope.form.guhua,
                        "totalMoney_pre": $scope.form.money,
                        "comeFrom": $scope.form.source,
                        "reason": $scope.form.reason,
                        "table": $scope.items
                    }
                }).success(function(response) {
                    if (response == "1") {
                        alert('已成功提交');
                        $timeout(function() {
                            location.href = "#/teaViewHanding";
                        }, 3000);
                    } else if (response == "2") {
                        alert('ERROR');
                    }
                })
            }
        }
        $scope.draftSubmit = function() {
            if (confirm('是否确定存入草稿？')) {

                console.log($scope.form, $scope.items, $scope.form.type);
                
                $http({
                    url: "/teacher/PurchaseApplySheet/submitDraft" + "?token=" + sessionStorage.getItem("token"),
                    method: "post",
                    data: {
                        "purchaseType": $scope.form.type,
                        "projectName": $scope.form.name,
                        "leader": $scope.form.leader,
                        "m_tel": $scope.form.telephone,
                        "s_tel": $scope.form.guhua,
                        "totalMoney_pre": $scope.form.money,
                        "comeFrom": $scope.form.source,
                        "reason": $scope.form.reason,
                        "table": $scope.items
                    }
                }).success(function(response) {
                    if (response == "1") {
                        alert('已成功提交');
                        location.href = "#/teaViewHanding";
                    } else{
                        alert('ERROR');
                    }
                })
            }
        };
        $scope.change = function() {
            if ($scope.form.type == "国产" || $scope.form.type == "进口") {
                $scope.xianshi = true;

            } else if ($scope.form.type == "C-工程") {
                $scope.xianshi = false;
                $scope.replace = "C-工程";
                for(var i in $scope.items){
                    $scope.items[i].type = "C-工程";
                }
            } else if ($scope.form.type == "S-服务") {
                $scope.xianshi = false;
                $scope.replace = "S-服务";
                for(var j in $scope.items){
                    $scope.items[j].type = "S-服务";
                }
            }
        };
        $scope.loadDraft = function() {
            $http({
                url: "teacher/PurchaseApplySheet/draft" + "?token=" + sessionStorage.getItem("token"),
                method: "Get"
            }).success(function(response) {
                $scope.form.type = response.purchaseType;
                $scope.form.name = response.projectName;
                $scope.form.leader = response.leader;
                $scope.form.telephone = response.m_tel;
                $scope.form.guhua = response.s_tel;
                $scope.form.money = response.totalMoney_pre;
                $scope.form.source = response.comeFrom;
                $scope.form.reason = response.reason;
                $scope.items = response.table;
                $scope.xianshi = true;
                if($scope.items.length == 0){
                    $scope.items.push({});
                }
                $scope.change();
                console.log(response);
                console.log($scope.form.type);
            })
        };
    });
}(angular, window);
