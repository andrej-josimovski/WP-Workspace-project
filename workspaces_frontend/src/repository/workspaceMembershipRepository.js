import axiosInstance from "../axios/axios.js";

const workspaceMembershipRepository = {
    findAll: async () => {
        return await axiosInstance.get("/workspace/membership");
    },
    findByWorkspaceId: async (workspaceId) => {
      return await axiosInstance.get(`/workspace/membership/${workspaceId}`)
    },
    addMember: async (workspaceId, userId, role) => {
        return await axiosInstance.post(`/workspace/membership/add`, null, {
            params: {
                workspaceId,
                userId,
                role,
            },
        });
    },
    createWorkspace: async (name) => {
        return await axiosInstance.post('/workspace/membership/create', null, {
            params: { name }
        });
    },
    updateRole: async (workspaceId, memberId, role) => {
        return await axiosInstance.put(`/workspace/membership/edit/${workspaceId}/${memberId}`, null, {
        params: {
            role,
        },
    });
},
    deleteMember: async (workspaceId, userId) => {
        return await axiosInstance.delete(`/workspace/membership/delete/${workspaceId}/${userId}`);
    },
};
export default workspaceMembershipRepository;