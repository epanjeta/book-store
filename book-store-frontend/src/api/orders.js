import API from "./api";

export const getOrders = async (params) => {
    const response = await API.get(`/order`, { params });
    return response.data;
};

export const updateOrders = async (data) => {
    console.log(data)
    const response = await API.put(`/order/update`,data);
    return response.data;
};