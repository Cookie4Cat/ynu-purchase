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
                //controller: "CheckMessageController"
            })
            .when("/teaViewHanding", {
                templateUrl: "/public/template/teaViewHanding.html",
                controller: "handingController"
            })
            .when("/check", {
                templateUrl: "/public/template/financial_checkTpl.html",
                //controller: "workController"
            })
            .when("/adminIndex", {
                templateUrl: "/public/template/adminIndex.html",
                controller: "indexController"
            })
            .when("/adminHistory",{
                templateUrl: "/public/template/approvalHistoryTpl.html",
                controller: "historyController"
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
                //controller: "appController"
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
    app.controller('indexController', function($scope, $http) {
        
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
            $http.get("/admin/projects/handling?token=" + sessionStorage.getItem("token")
                            + "&countPerPage=8&pageNum=" + pageNum
            ).success(function (response) {
                $scope.handlingProjectList = response;
            });
        };

        //进入页面先加载第一页
        $scope.getPageCount("/admin/projects/handling/count");
        $scope.getProjectList(1);

        //换页
        $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            //如果搜索框中存在值
            if(!$scope.pidSearch||$scope.statusSearch||$scope.typeSearch){
                $scope.getPageCount("/admin/projects/handling/search/count?projectId="+$scope.pidSearch+"&type="
                    + $scope.typeSearch + "&status=" + $scope.statusSearch +"&token=" + sessionStorage.getItem("token"));
                $scope.search(pageNum);
            }else{
                $scope.getPageCount("/admin/projects/handling/count?token="+sessionStorage.getItem("token"));
                $scope.getProjectList(pageNum);
            }
        };

        //init 
        $scope.typeSearch = "";
        $scope.statusSearch = "";
        $scope.pidSearch = "";
        //根据查询条件获取项目列表
        $scope.search = function(pageNum) {
            $http.get("/admin/projects/handling/search?token=" + sessionStorage.getItem("token")
                + "&countPerPage=8&pageNum=" + pageNum + "&type=" + $scope.typeSearch
                + "&status=" + $scope.statusSearch + "&projectId=" + $scope.pidSearch
            ).success(function (response) {
                $scope.handlingProjectList = response;
            });
        };
        //
        $scope.view=function(pid){
            window.location.href='#/projectVerify?projectId='+pid;
        }
    });
    //history
    app.controller('historyController', function($scope, $http) {

        //获取总数以分页
        $scope.getPageCount = function (url) {
            $http.get(url)
                .success(function(response){
                    console.log(response);
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
            $http.get("/admin/projects/history?token=" + sessionStorage.getItem("token")
                + "&countPerPage=8&pageNum=" + pageNum
            ).success(function (response) {
                $scope.handlingProjectList = response;
            });
        };


        //进入页面先加载第一页
        $scope.getPageCount("/admin/projects/history/count");
        $scope.getProjectList(1);

        //换页
        $scope.changePage = function (pageNum) {
            //防止越界
            if(pageNum < 1){
                pageNum = 1;
            }else if(pageNum > $scope.pageNum){
                pageNum = $scope.pageNum;
            }
            //如果搜索框中存在值
            if(!$scope.pidSearch||$scope.statusSearch||$scope.typeSearch){
                $scope.getPageCount("/admin/projects/history/search/count?projectId="+$scope.pidSearch+"&type="
                    + $scope.typeSearch + "&status=" + $scope.statusSearch +"&token=" + sessionStorage.getItem("token"));
                $scope.search(pageNum);
            }else{
                $scope.getPageCount("/admin/projects/history/count?token="+sessionStorage.getItem("token"));
                $scope.getProjectList(pageNum);
            }
        };

        //init
        $scope.typeSearch = "";
        $scope.statusSearch = "";
        $scope.pidSearch = "";
        //根据查询条件获取项目列表
        $scope.search = function(pageNum) {
            $scope.getPageCount("/admin/projects/history/search/count?projectId="+$scope.pidSearch+"&type="
                + $scope.typeSearch + "&status=" + $scope.statusSearch +"&token=" + sessionStorage.getItem("token"));
            console.log(
                "/admin/projects/history/search/count?projectId="+$scope.pidSearch+"&type="
                + $scope.typeSearch + "&status=" + $scope.statusSearch +"&token=" + sessionStorage.getItem("token")
            );
            $http.get("/admin/projects/history/search?token=" + sessionStorage.getItem("token")
                + "&countPerPage=8&pageNum=" + pageNum + "&type=" + $scope.typeSearch
                + "&status=" + $scope.statusSearch + "&projectId=" + $scope.pidSearch
            ).success(function (response) {
                $scope.handlingProjectList = response;
            });
        };
        //
        $scope.view=function(pid){
            window.location.href='#/projectVerify?projectId='+pid;
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
                "&content=" + $scope.suggestion + "&result=approve",
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
