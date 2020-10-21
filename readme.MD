# Microservice_Autotests
 
# Step 1. Docker

**To use centos you need to build an image with help of dockerfile**

```
docker build --rm --no-cache -t httpd .

docker run --privileged --name httpd -v /sys/fs/cgroup:/sys/fs/cgroup:ro -p 80:80 -d  httpd

docker exec -it httpd /bin/bash
```


**Then you delete httpd and install nginx and JDK:**


```
yum remove httpd
yum install epel-release
yum install nginx
yum install java-11-openjdk-devel
```


**To copy our mock server from host to container:**

```docker cp mock.jar httpd:/root```

**Then you need to Run mock server and nginx**

1. run nginx with:
`/usr/sbin/nginx`
2. run java with
`java -jar mock_jar`

# Step 2. Nginx 

To configure NGINX you need to put **nginx.conf** to
`/etc/nginx/`

There are commits in **nginx.conf** to understand, where to put static files and how proxy works

if smth is wrong, you can read logs in `var/logs/nginx`

- error.log
- access.log

to reload nginx print `nginx -s reload`

# Step 3. Manual test

1. For GET request
url is `localhost:80/mock/get_employee`

2. For POST request
url is `localhost:80/mock/create_employee`
body is `{
    "fio" : "Kenobi1",
    "position" : "JediMaster1",
    "number" : "661"
}`

3. For DELETE request
url is `localhost:8008/mock/delete_employee?id=1028`

# Step 4. Autotest

When you check that everything is ok you need to build artifact `autotest.jar`

Then run it!




