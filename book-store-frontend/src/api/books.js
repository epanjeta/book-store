import API from "./api";
import {number} from "yup";
import { getToken } from "./jwt";

const token = getToken();

export const getBooks = async () => {
  const response = await API.get("/book");
  return response.data;
};

export const getBook = async (id) => {
  const response = await API.get("/book/" + id);
  return response.data;
};

export const createBook = async (data) => {
  let tk = await token;
  const config = {
    headers: {
      'Authorization': `Bearer ${tk}`,
      'Content-Type': 'application/json', // Optionally set content type
    }
  };
  const response = await API.post(`/book/createNewBook`, data, config);
  return response;
};

export const createImage = async (data) => {
  const response = await API.post(`/image/create`, data);
  return response;
};