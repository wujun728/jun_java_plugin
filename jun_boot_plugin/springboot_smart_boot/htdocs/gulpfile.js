// 载入外挂
var gulp = require('gulp'),  
    sass = require('gulp-ruby-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    imagemin = require('gulp-imagemin'),
    rename = require('gulp-rename'),
    // clean = require('gulp-clean'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    cache = require('gulp-cache'),
    browserSync = require('browser-sync'),
    reload = browserSync.reload,
    htmlInjector = require("bs-html-injector"),
    proxyMiddleware = require('http-proxy-middleware'),
    del = require('del');




// 样式
gulp.task('style', function() {
   return sass('./src/css/*.scss')
        .on('error', function (err) {
            console.error('Error!', err.message);
        })
      // .pipe(concat('index.debug.css'))
      .pipe(autoprefixer('last 2 version', 'safari 5', 'opera 12.1', 'ios 6', 'android 4'))
      .pipe(gulp.dest('./build/css'))
      .pipe(minifycss())
      .pipe(rename(function(path){
        path.basename = path.basename.replace('.debug','')
      }))
      .pipe(gulp.dest('./build/css'))
});

// 发布html页面
gulp.task('html',function() {
  gulp.src('template/**/*.html')
    .pipe(gulp.dest('build/'))
});

// 发布字体
gulp.task('fonts',function() {
  gulp.src('src/fonts/*')
    .pipe(gulp.dest('build/fonts'))
});
// 脚本
gulp.task('scripts',function() {
  gulp.src('src/js/**/*.js')
    // .pipe(concat('all.js'))
    .pipe(gulp.dest('build/js'))
    .pipe(uglify())
    .pipe(rename(function(path){
      console.log(path);
      path.extname = '.min.js';
    }))
    .pipe(gulp.dest('build/js'))
});

// 图片压缩
gulp.task('images', function() {  
  return gulp.src('src/img/*')
    .pipe(cache(imagemin({ optimizationLevel: 3, progressive: true, interlaced: true })))
    .pipe(gulp.dest('build/img'))
    .pipe(notify({ message: 'Images task complete' }));
});

//browser-sync
gulp.task('browser-sync',['clean'], function() {
    browserSync.use(htmlInjector, {
        files: "*/*.html"
    });

    //设置代理
    var proxy = proxyMiddleware('/smartboot', {
        target: 'http://localhost:8080',
        pathRewrite: {
            '^/smartboot' : '/',     // rewrite path
        },
        // headers: {
        //     host:'localhost'   // 这个挺关键
        // }
    });
    console.log(proxy);
    browserSync({
        server: {
            baseDir: "./",
            index: "/index.html",
            middleware: [proxy]
        },
        https: false
    });
    gulp.watch(['build/**/*']).on('change', function(file) {
        reload(file.path);
    });
    gulp.watch(['src/**/*']).on('change', function(file) {
        reload(file.path);
    });
    gulp.watch(['template/**/*']).on('change', function(file) {
        reload(file.path);
    });
});

// 清理
gulp.task('clean', function() {  
  // return gulp.src('build/**/*', {read: false})
  // .pipe(clean({force: true}));
  return del(['build/**/*']);
});

// 预设任务
gulp.task('default',['clean'], function() {  
    gulp.start('html','style','fonts', 'images','scripts');
});

// 看手
gulp.task('watch', function() {

  // 看守所有.scss档
  gulp.watch('src/css/**/*.scss', ['style']);

  // 看守所有图片档
  gulp.watch('src/img/**/*', ['images']);

  // 看守所有图片档
  gulp.watch('src/js/**/*.js', ['scripts']);

  gulp.watch('src/fonts/*',['fonts']);

  gulp.watch('template/**/*.html', ['html']);

});
// start
gulp.task('start',['default','browser-sync','watch']);

module.exports = gulp;




