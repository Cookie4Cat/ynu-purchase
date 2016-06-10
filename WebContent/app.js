var express = require("express");
var app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.json("{type:'application/*+json'}"));

app.use("/", express.static(__dirname + "/"));


app.post("/loginCheck", function(req, res) {
    console.log(req.body);
    // 测试数据 
    var example = {
        teacher: {
            name: "teacher",
            password: "teacher"
        },
        admin: {
            name: "admin",
            password: "admin"
        },
        record: {
            name: "record",
            password: "record"
        },

    };

    // 提取数据
    if (req.body) {
        var user = {
                name: req.body.userName,
                password: req.body.password
            }
            // 处理
        switch (user.name) {
            case "teacher":
                // compareUser(user, example.teacher);
                res.end("1");
                break;
            case "admin":
                // compareUser(user, example.admin);
                res.end("2");

                break;
            case "record":
                res.end("3");

                break;
        }
    }
})

app.listen(3000);
