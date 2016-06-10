$(document).ready(function(){
    $("#GoMainPage").click(function(){
        alert('asdf');
    });
});

$(function() {
    setInterval("getTime();", 1000);
});

function getTime() {
    var myDate = new Date();
    var date = myDate.toLocaleDateString();
    var hours = myDate.getHours();
    var minutes = myDate.getMinutes();
    var seconds = myDate.getSeconds();
    $("#showDate").html("<h5 class='time'>" + "<a class='time2' href='#/index'>注销</a>" + "   " + "<a class='time2'>联系我们</a>" + " " + hours + ":" + minutes + ":" + seconds + "</h5>");
}
