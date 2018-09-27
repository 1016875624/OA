cd /d OA\admin-dashboard\
git rm -r --cached bootstrap.js
git rm -r --cached bootstrap.css
git rm -r --cached classic.json
git rm -r --cached classic.jsonp
cd build\
git rm -r --cached .
cd /d ..\..\
cd src\main\webapp\
cd classic\
git rm -r --cached .
cd ../resources/
git rm -r --cached .
cd ..
git rm -r --cached classic.json
git rm -r --cached classic.jsonp
git rm -r --cached index.html
pause