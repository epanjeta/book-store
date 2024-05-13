import API from "./api";
import {number} from "yup";

export const getBooks = async () => {
  const response = await API.get("/book");
  return response.data;
};

export const getBook = async (id) => {
  const response = await API.get("/book/" + id);
  return response.data;
};
