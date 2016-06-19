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
                controller: "CheckMessageController"
            })
            .when("/teaViewHanding", {
                templateUrl: "./public/template/teaViewHanding.html",
                controller: "handingController"
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
    //token 服务
    app.service('tokenHander', ['', function() {
        return {
            setToken: function(token) {
                return sessionStorage.set("token", token);
            },
            getToken: function() {
                return sessionStorage.getItem("token");
            }
        }

    }])
    app.service("setPage", function() {
        return {
            init: function(pages, callback) {

                var lastPage = document.getElementById("lastPage");
                var fNode = document.getElementById("ul");
                for (var i = 1; i <= pages; i++) {
                    var newNode = document.createElement("li");
                    var AElement = document.createElement("a");
                    var textnode = document.createTextNode(i);
                    AElement.appendChild(textnode);
                    newNode.appendChild(AElement);
                    newNode.addEventListener("click", function() {

                        var index = i;
                        return function() {
                            callback(index);
                        }
                    }())
                    fNode.insertBefore(newNode, lastPage);

                }

            }
        }
    })
    app.controller("handingController", function($scope, $http, setPage) {
        $http({
                url: "/teacher/history/unCompleted?token=" + sessionStorage.getItem("token") + "&currentPage=1",
                method: "get"
            }
        ).success(function(response) {
            $scope.handle = response;
        });
        // 测试数据
        $http({
            url: "",
            method: "get"
        }).success(function(response) {
            $scope.pages = response;
            setPage.init(response, chagePages);

            function chagePages(page) {
                $http({
                        url: "/teacher/history/unCompleted?token=" + sessionStorage.getItem("token") + "&currentPage=" + page,
                        method: "get"
                    }

                ).success(function(response) {
                    $scope.handle = response;
                });

            }
        })

        $scope.methods = {
            showDetail: function(message) {
                console.log(message)
                $scope.message = message; //切换toggle里面的message
            },
            download: function(id) {
                // 下载申请表
                $http({
                    url: "  ?projectId=" + id, //+"&token=" + tokenHander.getToken(),
                    method: "get"
                }).success(function(response) {
                    // do something
                })

            }
        }

    })
    app.controller('CheckMessageController', function($scope,$http) {
        $http({
            url:"/teacher/history/completed?token="+sessionStorage.getItem("token") + "&currentPage=1",
            method:"get",
        }).success(function(response){
            $scope.historyItems = response;
        })
        
        $scope.methods = {
            showDetail: function(message, id) {
                $scope.pro = {
                    message: message,
                    id: id
                }
            },
            download: function(id) {
                // 下载申请表 
                $http({
                    url: "  ?projectId=" + id, //+"&token=" + tokenHander.getToken(),
                    method: "get"
                }).success(function(response) {
                    // do something
                })
            }
        }
    });

/**
 * 结束
 */


    app.controller("indexController", function($scope, $http) {
        location.href = "/login.html";
    })

    app.controller('teaFormCtr', function($scope, $http, $timeout, $rootScope) {
        $scope.form = {};
        $scope.items = [];
        $scope.form.token = 'abc1234';

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
                    url: "/" + "?token=" + sessionStorage.getItem("token"),
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
            })
        }
        $scope.change = function() {
            if ($scope.form.type == "国产" || $scope.form.type == "进口") {
                $scope.xianshi = true;
             
            } else if ($scope.form.type == "C-工程") {
                $scope.xianshi = false;
                $scope.replace = "C-工程"
            } else if ($scope.form.type == "S-服务") {
                $scope.xianshi = false;
                $scope.replace = "S-服务"
            }
        }



    });

    app.controller('opHistoryController', function($scope) {
        // $scope.bold = "bold";
        $scope.key = '';
        $scope.data = [
            { id: "cg20160517-04", totalMoney: "32,434", updataTime: "2016-01-17" },
            { id: "cg20150517-04", totalMoney: "32,434", updataTime: "2016-01-17" },
            { id: "ck20160517-04", totalMoney: "32,434", updataTime: "2016-01-17" },
            { id: "cy20130517-04", totalMoney: "32,434", updataTime: "2016-01-17" },
            { id: "cy20130517-04", totalMoney: "32,434", updataTime: "2016-01-17" },
        ];
    });
}(angular, window);
