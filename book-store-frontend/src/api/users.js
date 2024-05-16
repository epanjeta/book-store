import API from "./api";
  
  export const login = async (data) => {
    const response = await API.post("authentication/authenticate", data);
    return response.data;
  };

  export const register = async (data) => {
    const response = await API.post("authentication/register", data);
    return response.data;
  };

  export const getUser = async (id) => {
    const response = await API.get("/user/" + id);
    return response.data;
  };