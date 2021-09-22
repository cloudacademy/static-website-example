#!/bin/sh

npm run build
rm -rf assets/sass
rm assets/js/main.js assets/js/util.js
rm -rf node_modules
rm .gitignore LICENSE.MD README.MD package*.json
