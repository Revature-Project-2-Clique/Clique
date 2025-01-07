import axios from "axios";

axios.defaults.withCredentials = true;

const api2 = axios.create({
    baseURL: 'http://3.82.150.19:8080'
})

export default api2;

export const searchUsers2 = (query, headers) =>
    api2.get(`/search/users?query=${query}`, {headers}).then((res) => res.data);

// Search posts
export const searchPosts2 = (query, headers) =>
    api2.get(`/search/posts?query=${query}`, {headers}).then((res) => res.data);