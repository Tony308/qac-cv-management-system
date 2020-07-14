FROM node:12.2.0-alpine
RUN mkdir -p /app
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY . .
### INSTALLS PACKAGES
RUN npm install
EXPOSE 3000:3000
ENTRYPOINT ["npm", "run", "start"]
