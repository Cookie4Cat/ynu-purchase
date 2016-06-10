var gulp = require('gulp'),
    connect = require('gulp-connect'),
    sass = require('gulp-ruby-sass');


gulp.task('connect', function() {
    connect.server({
        root: './',
        livereload: true
    });
});

gulp.task('watch', function() {
    gulp.watch("public/*.html", ["index"]);
    gulp.watch("public/template/*.html", ["template"]);
    gulp.watch("public/js/*.js", ["js"]);
    gulp.watch("public/sass/*.scss", ["sass"]);
});

gulp.task("js", function() {
    gulp.src("public/js/*.js")
        .pipe(connect.reload());
});

gulp.task("index", function() {
    gulp.src("public/*.html")
        .pipe(connect.reload());
});

gulp.task("template", function() {
    gulp.src("public/template/*.html")
        .pipe(connect.reload());
});

gulp.task('sass', function() {
    return sass('./public/sass/*.scss', { style: 'expanded' })
        .pipe(gulp.dest('./public/stylesheet'))
        .pipe(connect.reload())
});

//运行Gulp时，默认的task
gulp.task('default', ['connect', 'sass', 'watch']);
