import axios from 'axios'

const Users_REST_API_URL = 'http://localhost:8085/users';

class UserService{
    getUsers(){
       return axios.get(Users_REST_API_URL);
    }
}

export default new UserService();