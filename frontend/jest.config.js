const {defaults} = require('jest-config');

// jest.config.js
module.exports = {
  verbose: true,
  moduleFileExtensions: [ "js", "jsx"],
  moduleDirectories: [ "node_modules", "bower_components", "shared" ],
  moduleNameMapper: {
    "\\.(css|less|scss|sass|json)$": "identity-obj-proxy"
  },
  collectCoverage: true,
};  