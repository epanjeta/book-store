import API from "./api";

export const getCartDetails = async (params) => {
  const response = await API.get(`/cartItem/cartForUser`, { params });
  return response.data;
};

export const removeItemFromCart = async (data) => {
  const response = await API.delete(`/cartItem/deleteFromCart`, { data });
  return response;
};