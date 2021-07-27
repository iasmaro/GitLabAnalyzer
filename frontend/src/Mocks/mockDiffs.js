export const diffs = [
    {
       old_path:"backend/.dockerignore",
       new_path:"backend/.dockerignore",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,5 @@\n+.idea\n+.gradle\n+database\n+build\n+.gitignore\n\\ No newline at end of file\n"
    },
    {
       old_path:"backend/Dockerfile",
       new_path:"backend/Dockerfile",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,13 @@\n+# multi-stage builds, keep image size small\n+# location of Dockerfile are important\n+# COPY will ignore files/folders in .dockerignore\n+FROM gradle:6.8-jdk11 AS build\n+RUN mkdir -p /backend\n+WORKDIR /backend\n+COPY . .\n+RUN ./gradlew build\n+\n+FROM openjdk:8-jre-alpine3.8\n+COPY --from=build /backend/build/libs/*.jar app.jar\n+EXPOSE 8080\n+ENTRYPOINT [\"java\",\"-jar\",\"app.jar\"]\n\\ No newline at end of file\n"
    },
    {
       old_path:"backend/src/main/resources/application.properties",
       new_path:"backend/src/main/resources/application.properties",
       a_mode:"100644",
       b_mode:"100644",
       new_file:false,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -14,13 +14,13 @@\n #spring.data.mongodb.database=\n \n # local server\n-spring.data.mongodb.host=localhost\n+# spring.data.mongodb.host=localhost\n # team VM server\n #spring.data.mongodb.host=142.58.22.176\n #spring.data.mongodb.port=27017\n \n #Connect to a remote MongoDB server\n-# spring.data.mongodb.uri=mongodb+srv://haumea:cmpt3732021@cmpt373-project.unzqm.mongodb.net/testdb?retryWrites=true\u0026w=majority\n+spring.data.mongodb.uri=mongodb+srv://haumea:cmpt3732021@cmpt373-project.unzqm.mongodb.net/testdb?retryWrites=true\u0026w=majority\n #mongodb+srv://haumea:\u003cpassword\u003e@cmpt373-project.unzqm.mongodb.net/test\n \n #spring.data.mongodb.uri=mongodb://admin:NU%2B!n2ju%2BVFr!X*F@cmpt373-1211-11.cmpt.sfu.ca:27017/?authSource=admin\u0026ssl=true\n"
    },
    {
       old_path:"docker-compose.yaml",
       new_path:"docker-compose.yaml",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,25 @@\n+version: '3'\n+services:\n+\n+  backend:\n+    # specify the directory of the Dockerfile\n+    build: ./backend\n+    image: gitlabanalyzer_backend:latest\n+    container_name: gitanalyzer-backend\n+    ports:\n+      - 8080:8080\n+\n+  frontend:\n+    build: ./frontend\n+    container_name: gitanalyzer-frontend\n+    ports:\n+      - 80:80\n+    links:\n+      - backend\n+    depends_on:\n+      - backend  \n+\n+# https://wkrzywiec.medium.com/how-to-run-database-backend-and-frontend-in-a-single-click-with-docker-compose-4bcda66f6de\n+# https://turkogluc.com/run-react-and-spring-docker-compose/\n+# docker-compose up --build -d\n+# docker-compose down --rmi all\n\\ No newline at end of file\n"
    },
    {
       old_path:"frontend/.dockerignore",
       new_path:"frontend/.dockerignore",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,5 @@\n+node_modules\n+build\n+package-lock.json\n+README.md\n+.eslintcache\n\\ No newline at end of file\n"
    },
    {
       old_path:"frontend/Dockerfile",
       new_path:"frontend/Dockerfile",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,14 @@\n+FROM node:14.15.5-alpine as build\n+WORKDIR /frontend\n+ENV PATH /frontend/node_modules/.bin:$PATH\n+COPY package.json /frontend/package.json\n+RUN npm install --silent\n+COPY . .\n+RUN npm run build\n+\n+FROM nginx:1.16.0-alpine\n+COPY --from=build /frontend/build /usr/share/nginx/html\n+RUN rm /etc/nginx/conf.d/default.conf\n+COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf\n+EXPOSE 80\n+CMD [\"nginx\", \"-g\", \"daemon off;\"]\n\\ No newline at end of file\n"
    },
    {
       old_path:"frontend/nginx/default.conf",
       new_path:"frontend/nginx/default.conf",
       a_mode:"0",
       b_mode:"100644",
       new_file:true,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -0,0 +1,14 @@\n+server {\n+    listen 80;\n+    server_name cmpt373-1211-11.cmpt.sfu.ca;\n+    root /usr/share/nginx/html;\n+    index index.html index.html;\n+\n+    location /api {\n+        proxy_pass http://cmpt373-1211-11.cmpt.sfu.ca:8080/api;\n+    }\n+\n+    location / {\n+        try_files $uri $uri/ /index.html;\n+    }\n+}\n\\ No newline at end of file\n"
    },
    {
       old_path:"frontend/src/Constants/constants.js",
       new_path:"frontend/src/Constants/constants.js",
       a_mode:"100644",
       b_mode:"100644",
       new_file:false,
       renamed_file:false,
       deleted_file:false,
       diff:"@@ -8,10 +8,10 @@ const dev = {\n \n const prod = {\n     SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',\n-    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/users/userId?url=http://cmpt373-1211-11.cmpt.sfu.ca/',\n-    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/projects',\n-    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/users',\n-    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/members',\n+    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/userId?url=http://cmpt373-1211-11.cmpt.sfu.ca/',\n+    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/projects',\n+    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users',\n+    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members',\n }\n \n export const config = process.env.NODE_ENV === 'development' ? dev : prod;\n"
    }
 ]