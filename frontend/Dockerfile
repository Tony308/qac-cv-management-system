FROM node:12.2.0-alpine as build
RUN mkdir -p /app
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY . .
### INSTALLS PACKAGES
RUN npm i && npm run build

FROM node:12.2.0-alpine 
COPY --from=build /app/build ./build
RUN npm i -g serve
ENTRYPOINT ["serve", "-s", "build"]
