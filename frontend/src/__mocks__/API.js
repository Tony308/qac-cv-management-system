
jest.mock('../_services/API');
const {login, createAccount, getCVs, uploadCV, retrieveCV, updateCv, deleteCV} = require('../_services/API');

login.mockImplementation(() => {
  return { data: {
      message: "Login Successful",
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJodWFuZyIsImlhdCI6MTU5Mjc1NzE0NywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.yOWC1fuISRo5Vtj87DZ7mzT9ZyuSBZrrtB-Bt4J45SF-J9e5EmDbQlPjI-bik0Jz9Qu4lct43tz48oXZXogDHg"
    }
  }
});
