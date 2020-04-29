FROM node:12.2.0-alpine

RUN mkdir -p /app
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package*.json ./

### INSTALLS PACKAGES
RUN npm install
COPY ./public ./public 
COPY ./src ./src 
EXPOSE 3000:3000
ENTRYPOINT ["npm", "run", "start"]
