FROM mongo
RUN apt-get update
VOLUME /Users/tony/Documents/qac-cv-management-system/mongo/mongodb:/data/db
WORKDIR /data
COPY ./Customers /Customers
EXPOSE 27017:27107
ENTRYPOINT ["mongod","--bind_ip_all", "--auth"]










