import API from "./api";

export const getSession = async () => {
    //const response = await API.get("???????/session");
    //return response.data;
    return {data: "OK"}
  };
  
  export const login = async (data) => {
    const response = await API.post("authentication/authenticate", data);
    if(response.data.errorMessage == null || response.data.errorMessage === ""){
      localStorage.setItem('CurrentUserId', response.data.id)
      localStorage.setItem('CurrentUserRole', response.data.role)
      localStorage.setItem('CurrentUserName', response.data.userName)
    }
    return response.data;
  };