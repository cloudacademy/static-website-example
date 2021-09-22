#!/bin/sh

node-sass assets/sass/main.scss assets/css/main.css
node-sass assets/sass/ie9.scss assets/css/ie9.css
node-sass assets/sass/noscript.scss assets/css/noscript.css

minify assets/js/main.js > assets/js/main.min.js
minify assets/js/util.js > assets/js/util.min.js
