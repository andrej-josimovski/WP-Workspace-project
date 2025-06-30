import {useEffect, useState} from "react";
import workspaceRepository from "../repository/workspaceRepository.js";
import workspaceMembershipRepository from "../repository/workspaceMembershipRepository.js";

const useWorkspaceDetails = (id) => {
    const [workspace, setWorkspace] = useState(null);
    const [memberships, setMemberships] = useState([]);

    const fetchData = async () => {
        try {
            const res = await workspaceRepository.findById(id);
            setWorkspace(res.data);
        } catch (err) {
            console.error("Workspace fetch error:", err);
        }
    };

    const fetchMemberships = async () => {
        try {
            const res = await workspaceMembershipRepository.findByWorkspaceId(id);
            setMemberships(res.data);
        } catch (err) {
            console.error("Membership fetch error:", err);
        }
    };

    useEffect(() => {
        fetchData();
        fetchMemberships();
    }, [id]);

    return {workspace, memberships, refetchMemberships: fetchMemberships};
};

export default useWorkspaceDetails;
