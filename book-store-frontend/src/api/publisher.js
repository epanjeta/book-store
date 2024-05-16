import API from "./api";

export const getPublishers = async () => {
    const response = await API.get("/publisher");
    return response.data;
};