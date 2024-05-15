import API from "./api";

export const getAuthors = async () => {
    const response = await API.get("/author");
    return response.data;
};