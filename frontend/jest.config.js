const {defaults} = require('jest-config');

// jest.config.js
module.exports = {
  verbose: true,
  moduleFileExtensions: [ "js", "jsx"],
  moduleDirectories: [ "node_modules", "bower_components", "shared" ],
  moduleNameMapper: {
    "\\.(css|less)$": "<rootDir>/src/__mocks__/styleMock.js",
    "^.+\\.(css)$": "identity-obj-proxy",
    // "\\.(gif|ttf|eot|svg)$": "<rootDir>/src/__mocks__/fileMock.js"
  },
  collectCoverage: true
};  