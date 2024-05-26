import API from "./api";
import {getToken} from "./jwt";
const token = getToken();

export const exportOrders = async (params) => {
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/pdf', // Optionally set content type
        },
        responseType: 'blob',
        params: params
    };
    return await API.get(`/orders/export/pdf`, config );
};

export const exportUsers = async () => {
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/pdf', // Optionally set content type
        },
        responseType: 'blob'
    };
    return await API.get(`/users/export/pdf`, config );
};