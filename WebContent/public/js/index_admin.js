! function(angular, window) {
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
    /**
     *  刘嘉航 任皓 2016-06-19
     *  起始
     */

    //admin首页控制器
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
            $scope.getPageCount("/admin/projects/handling/search/count?projectId="+$scope.pidSearch+"&type="
                + $scope.typeSearch + "&status=" + $scope.statusSearch +"&token=" + sessionStorage.getItem("token"));
            $http.get("/admin/projects/handling/search?token=" + sessionStorage.getItem("token")
                + "&countPerPage=8&pageNum=" + pageNum + "&type=" + $scope.typeSearch
                + "&status=" + $scope.statusSearch + "&projectId=" + $scope.pidSearch
            ).success(function (response) {
                $scope.handlingProjectList = response;
            });
        };
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
        $scope.projectId = url.substring(url.lastIndexOf('=') + 1, url.length);

        //根据pid获取项目实体
        $http.get("/admin/projects/" + $scope.projectId + "?token="+ sessionStorage.getItem("token") )
            .success(function(response) {
                $scope.project = response;
            });
        //审核通过
        $scope.approve = function() {
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
                    alert("操作成功");
                    location.href = "/index_admin.html#/adminIndex";
                }else{
                    alert("运行出错");
                }
            });
        };
        //审核不通过
        $scope.refuse = function() {
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
                        alert("操作成功");
                        location.href = "/index_admin.html#/adminIndex";
                    }else{
                        alert("运行出错");
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
                    alert("操作成功");
                    location.href = "/index_admin.html#/adminIndex";
                }else{
                    alert("运行出错");
                }
            })
        }
    });
}(angular, window);
