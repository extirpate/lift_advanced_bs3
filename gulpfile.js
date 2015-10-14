var gulp=require('gulp'),
    merge2=require('merge2'),
    concat=require('gulp-concat'),
    jshint=require('gulp-jshint'),
    uglify=require('gulp-uglify'),
    minifyCss=require('gulp-minify-css')
    flatten=require('gulp-flatten'),
    less=require('gulp-less'),
    csslint=require('gulp-csslint'),
    imageMin=require('gulp-imagemin')
    rename=require('gulp-rename'),
    mainBowerFiles=require('gulp-main-bower-files'),
    gzip=require('gulp-gzip'),
    sourcemaps=require('gulp-sourcemaps');
//var mainBowerFiles=require('main-bower-files');

//gulp.task('default',function(){
  //var unfiltered=mainBowerFiles('**/*.js');
  //console.log('unfiltered:',unfiltered);
//});
//把bower.json配置中的文件夹全部读出来，根据bower info中获取的main，把文件全部取出来
//main-bower-files[]
/*
gulp.task('default',function(){
  return gulp.src('./bower.json')
    .pipe(mainBowerFiles('** /*.js',{
              overrides:{
                bootstrap:{
                  main:[
                    './js/alert.js'  
                  ]                  
                }
              }
                              }))
    .pipe(gulp.dest('temp'));
});
*/

//angular angular.js
//angular-animate angular-animate.js
//angular-bootstrap  ui-bootstrap-tpls.js
//jquery  jquery.js
//bootstrap
/*
 'less/bootstrap.less',
 'dist/js/bootstrap.js'
overrides: 
   './fonts/glyphicons-halflings-regular.eot',
   './fonts/glyphicons-halflings-regular.svg',
   './fonts/glyphicons-halflings-regular.ttf',
   './fonts/glyphicons-halflings-regular.woff',
   './fonts/glyphicons-halflings-regular.woff2'

 */
//bootstrap-material-design 
/*  "dist/css/material.css",
    "dist/js/material.js",
    "dist/css/ripples.css",
    "dist/js/ripples.js",
    "dist/fonts/Material-Design-Icons.eot",
    "dist/fonts/Material-Design-Icons.svg",
    "dist/fonts/Material-Design-Icons.ttf",
    "dist/fonts/Material-Design-Icons.woff"
*/
gulp.task('checkJS',function(){
    return  gulp.src('./bower.json')
    .pipe(mainBowerFiles('**/*.js'))
    .pipe(jshint())
    .pipe(jshint.reporter('default'));

});

gulp.task('checkCSS',function(){
    return  merge2(
       gulp.src(['./src/main/webapp/assets/css/*.css','!./src/main/webapp/assets/css/*.min.css']),
      gulp.src('./bower.json')
        .pipe(mainBowerFiles('**/*.less'))
        .pipe(less())
     )
     .pipe(csslint())
     .pipe(csslint.reporter());

})


gulp.task('handleJS',function(){
 /* return merge2(
    gulp.src('./bower.json')
     .pipe(mainBowerFiles(['** /*.js'])),
    gulp.src('./src/main/webapp/assets/*.js')
    )*/
  return gulp.src('./bower.json')
    .pipe(mainBowerFiles('**/*.js'))
    .pipe(concat('main.min.js'))
    .pipe(sourcemaps.init())
    .pipe(uglify())
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('./src/main/webapp/assets/js'));
});

//var imagemin = require('gulp-imagemin')
/*/.pipe(imagemin({
            progressive: true
        })*/

gulp.task('handleCSS',function(){
  return merge2(
      /* gulp.src(['./src/main/webapp/assets/css/*.css','!./src/main/webapp/assets/css/*.min.css']),
      */gulp.src('./bower.json')
        .pipe(mainBowerFiles('**/*.less'))
        .pipe(sourcemaps.init())
        .pipe(less())
        .pipe(sourcemaps.write())
     )
    .pipe(sourcemaps.init())
    .pipe(minifyCss())
    .pipe(concat('main.min.css'))
    //.pipe(rename({suffix:'.min'}))
    .pipe(sourcemaps.write())
    .pipe(flatten())
   // .pipe(gzip())
    .pipe(gulp.dest('./src/main/webapp/assets/css'));
});

gulp.task('handleFonts',function(){
    gulp.src('./bower.json')
      .pipe(mainBowerFiles('**/fonts/*.*',{
        overrides:{
          bootstrap:{
            main:[
               './less/bootstrap.less',
               './dist/js/bootstrap.js',
               './fonts/glyphicons-halflings-regular.eot',
               './fonts/glyphicons-halflings-regular.svg',
               './fonts/glyphicons-halflings-regular.ttf',
               './fonts/glyphicons-halflings-regular.woff',
               './fonts/glyphicons-halflings-regular.woff2'
            ]
          }
        }
      }))
      .pipe(flatten())
      .pipe(gulp.dest('./src/main/webapp/assets/fonts'));
});
/*
gulp.task('handleImages',function(){
    gulp.src('src/images/** /*')
        .pipe(imagemin({
            progressive: true
        }))
        .pipe(gulp.dest('dist/images'))
});

gulp.task('images', function () {
  return gulp.src('./img/*')
    .pipe(imagemin({
      progressive: true,
      svgoPlugins: [{removeViewBox: false}],
      use: [pngcrush()]
    }))
    .pipe(gulp.dest('img'));
});

// Sprite et minification SVG
gulp.task('svg', function () {
  return gulp.src('./svg/*.svg')
    .pipe(svgmin([{
      collapseGroups:false
    }]))
    .pipe(svgstore({
      fileName: 'icons.svg',
      prefix: 'icon-',
      inlineSvg: true
    }))
    .pipe(gulp.dest('./dist/'))
});
*/

