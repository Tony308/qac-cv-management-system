FROM node:12.2.0-alpine

RUN mkdir -p /app
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app
COPY package-lock.json /app

### INSTALLS PACKAGES
RUN npm install 
RUN npm start
COPY . /app
EXPOSE 3000:3000
ENTRYPOINT ["npm", "start"]

