FROM mongo
RUN apt-get update
WORKDIR /data
COPY ./Customers /Customers
ENTRYPOINT ["mongod","--auth", "--bind_ip_all"]
