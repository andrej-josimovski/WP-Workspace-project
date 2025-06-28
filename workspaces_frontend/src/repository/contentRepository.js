import axiosInstance from "../axios/axios.js";

const contentRepository = {
    findAll: async () => {
        return await axiosInstance.get("/content");
    },
    findById: async (id) => {
        return await axiosInstance.get(`/content/${id}`);
    },
    add: async (data) => {
        return await axiosInstance.post("/content/add", data);
    },
    edit: async (id, data) => {
        return await axiosInstance.put(`/content/edit/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/content/delete/${id}`);
    },
};

export default contentRepository;