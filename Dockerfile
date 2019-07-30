FROM centos:latest

MAINTAINER Anand Reddy < anand@gmail.com >

ENV workdir /var/www/html

RUN yum -y update && yum -y install httpd && rm -rf $workdir/*

WORKDIR $workdir

COPY . $workdir

EXPOSE 80

ENTRYPOINT ["/usr/sbin/httpd", "-D", "FOREGROUND"]
