import API from "./api";

export const getGenres = async () => {
    const response = await API.get("/autocomplete/genre");
    return response.data;
};

export const getLanguage = async () => {
    const response = await API.get("/autocomplete/language");
    return response.data;
};