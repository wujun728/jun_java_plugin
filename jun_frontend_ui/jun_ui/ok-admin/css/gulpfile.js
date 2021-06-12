let gulp = require('gulp'),
  sass = require('gulp-sass'),//sass转css插件
  auto = require('gulp-autoprefixer');//解决浏览器兼容问题的插件
gulp.task('default', function(){
  return gulp.src('sass/*')//需要编译的文件目录
    .pipe(sass({outputStyle:'compressed'}).on('error',sass.logError))
    .pipe(auto({//处理兼容
      browsers:['last 2 version'],
      cascade:false
    }))
    .pipe(sass())//开始编译
    .pipe(gulp.dest('css'));//存放编译之后的目录
});