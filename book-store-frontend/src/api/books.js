import API from "./api";

export const getBooks = async () => {
  const response = await API.get("/book");
  return response.data;
};
