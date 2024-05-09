import API from "./api";
  
  export const login = async (data) => {
    const response = await API.post("authentication/authenticate", data);
    return response.data;
  };