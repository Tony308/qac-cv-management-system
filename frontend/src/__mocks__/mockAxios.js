const axios = require('axios');
jest.mock('axios');

const login = () => axios.post("/login").mockResolvedValue({
  data: {
      message: "Login Successful",
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJodWFuZyIsImlhdCI6MTU5Mjc1NzE0NywidXNlcm5hbWUiOiJ1c2VybmFtZSJ9.yOWC1fuISRo5Vtj87DZ7mzT9ZyuSBZrrtB-Bt4J45SF-J9e5EmDbQlPjI-bik0Jz9Qu4lct43tz48oXZXogDHg"
    }
});



module.exports = {
  login
  // createAccount,
  // getCVs,
  // uploadCV,
  // retrieveCV,
  // updateCv,
  // deleteCV
}