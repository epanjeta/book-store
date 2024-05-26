import API from "./api";
import { getToken } from "./jwt";

const token = getToken();


export const getCartDetails = async (params) => {
  let tk = await token;
  const config = {
    headers: {
      'Authorization': `Bearer ${tk}`,
      'Content-Type': 'application/json', // Optionally set content type
    },
    params: params,
  };
  const response = await API.get(`/cartItem/cartForUser`,  config );
  return response.data;
};

export const removeItemFromCart = async (data) => {
  let tk = await token;
  const config = {
    headers: {
      'Authorization': `Bearer ${tk}`,
      'Content-Type': 'application/json', // Optionally set content type
    },
    data: data,
  };
  const response = await API.delete(`/cartItem/deleteFromCart`, config);
  return response;
};

export const addItemToCart = async (data) => {
  let tk = await token;
  const config = {
    headers: {
      'Authorization': `Bearer ${tk}`,
      'Content-Type': 'application/json', // Optionally set content type
    }
  };
  const response = await API.post(`/cartItem/addToCart`, data , config);
  return response;
};

export const emptyCart = async (userId) => {
  let tk = await token;
  const config = {
    headers: {
      'Authorization': `Bearer ${tk}`,
      'Content-Type': 'application/json', // Optionally set content type
    },
    userId: userId,
  };
  const response = await API.post(`/cartItem/emptyCart`, config);
  return response;
};