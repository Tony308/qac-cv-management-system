const axios = require('axios');
const Cookies = require('js-cookie');

const baseURL = process.env.NODE_ENV === "production" ? `https://cv-management-sys-backend.herokuapp.com/cvsystem` : `http://localhost:8081/cvsystem`;

const API  = axios.create({
  baseURL,
  timeout: 3500,
  withCredentials: true
});

const attributes = {expires: 1, secure: true, sameSite: 'lax'}

function getCookie(cookieName) {
  return Cookies.get(cookieName, attributes);
}

function login(e) {
  e.preventDefault();
  Cookies.remove('authToken', attributes)
  let url = `${baseURL}/login`;
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
      Cookies.set("authToken", res.data.token, attributes);
      console.log(getCookie('authToken'));
      sessionStorage.setItem("auth", 'true');
    }
    window.location.href = '/home';
  })
  .catch(err => this.handleHttpError(err));
} //End

function createAccount(e) {
  e.preventDefault();
  const url = `${baseURL}/create-account`;

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
} //End

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
} //End

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
