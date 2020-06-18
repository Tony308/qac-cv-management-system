import axios from 'axios';
import Cookies from 'js-cookie';

export default axios.create({
  baseURL: 'http://localhost:8081/cvsystem',
  timeout: 3500,
  headers: {'Authorization': `Bearer ${Cookies.get('authToken')}`},
  withCredentials: true
});
