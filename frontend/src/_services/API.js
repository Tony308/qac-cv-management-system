const axios = require('axios');
const Cookies = require('js-cookie');

const API  = axios.create({
  baseURL: 'http://localhost:8081/cvsystem',
  timeout: 3500,
  headers: {'Authorization': `Bearer ${Cookies.get('authToken',{expires:1})}`},
  withCredentials: true
});


function getCookie(cookieName) {
  return Cookies.get(cookieName, {expires: 1});
}

function login(e) {
  Cookies.remove('authToken', {expires: 1})
  e.preventDefault();
  let url = "http://localhost:8081/cvsystem/login";
  let data = new FormData();

  data.append('username', this.state.username);
  data.append("password", this.state.password);

  localStorage.setItem("username", this.state.username);

  axios.post(url, data)
  .then(res => {
    if (res.status === 202) {
      this.setState({
        password: ""
      });
      console.log(res);
      Cookies.set("authToken", res.data.token, {expires: 1});
      console.log(getCookie('authToken'));
      sessionStorage.setItem("auth", 'true');
    }
    window.location.href = '/home';
  })
  .catch(err => this.handleHttpError(err));
} //End

function createAccount(e) {
  e.preventDefault();
  const url = `http://localhost:8081/cvsystem/create-account`;

  let data = new FormData();
  data.append("username", this.state.username);
  data.append("password", this.state.password);

  axios.post(url, data)
  .then(res => {
    console.log(res);
    this.setState({
        password: null
    });
    window.location.href= "/home";
  })
  .catch(err => {
    this.handleHttpError(err);
  });
}

function getCVs() {
  let resource = `/get?name=${localStorage.getItem("username")}`;

  API.get(resource)
  .then(res => {
    this.setState({
        data: res.data
    });
  })
  .catch(err => this.handleHttpError(err));
} //End

function uploadCV(e) {
    e.preventDefault();

    let file = document.getElementById("CV").files[0];

    if (!file) {
        console.log('No file');
        return;
    } else if (!localStorage.getItem("username")) {
      //Is only ever set in login hence, requries re-login
      sessionStorage.setItem('auth', 'false');
      window.location.href="/"
      return;
    } else if (!file.name) {
      console.log('File name cannot be null');
      return;
    }

    const data = new FormData();

    data.append('file', file);
    data.append('user', localStorage.getItem("username"));
    data.append('fileName', file.name);

    let url = '/upload-cv';

    API.post(url, data)
        .then(res => {
            console.log(res);
            window.location.href = "/home";
        })
        .catch(err => this.handleHttpError(err));
} //End


function retrieveCV(id) {
  let url = `/retrieve/${id}`;

  API.get(url)
      .then(res => {
        this.setState({
          cvContent: res.data.cvFile.data,
          cvFileName: res.data.fileName
        });
      })
      .catch(err => this.handleHttpError(err));
}

function updateCv(id) {
    let url = `/update-cv/${id}`;
    let data = new FormData();

    let file = document.getElementById("CV").files[0];
    if (!file || !localStorage.getItem("username") || !file.name) {
        console.log('err');
        return "err, not enough details.";
    }

    data.append("id", id);
    data.append("file", file);
    data.append("fileName", file.name);

    API.put(url, data)
        .then(res => {
            console.log(res);
            window.location.href = "/home";
        })
        .catch(err => this.handleHttpError(err));
} //End

function deleteCV(id) {
    let resource = `/delete/${id}`;

    API.delete(resource)
    .then(response => {
      console.log(response);
      if (response.status === 200) {
        window.location.href = "/home";
      }
    })
    .catch(err => this.handleHttpError(err));
} //End


module.exports = {
  login,
  createAccount,
  getCVs,
  uploadCV,
  retrieveCV,
  updateCv,
  deleteCV
}
