import axiosInstance from "../axios/axios.js";

const workspaceRepository = {
    findAll: async () => {
        return await axiosInstance.get("/workspace");
    },
    findById: async (id) => {
        return await axiosInstance.get(`/workspace/${id}`);
    },
    add: async (data) => {
        return await axiosInstance.post("/workspace/add", data);
    },
    edit: async (id, data) => {
        return await axiosInstance.put(`/workspace/edit/${id}`, data);
    },
    delete: async (id) => {
        return await axiosInstance.delete(`/workspace/delete/${id}`);
    },
    addContent: async (id, data) => {
        return await axiosInstance.post(`/workspace/add-content/${id}`, data);
    },
    deleteContent: async (workspaceId, contentId) => {
        return await axiosInstance.delete(`/workspace/delete-content/${workspaceId}/${contentId}`)
    }
}
export default workspaceRepository;