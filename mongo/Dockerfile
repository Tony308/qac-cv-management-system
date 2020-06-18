FROM mongo:4.2.7-bionic
ADD mongo-init.js /docker-entrypoint-initdb.d/mongo-init.js
WORKDIR /data
ENTRYPOINT ["mongod", "--bind_ip_all", "--auth"]
EXPOSE 27017:27017
