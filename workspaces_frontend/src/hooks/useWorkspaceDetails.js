import { useEffect, useState, useCallback } from "react";
import workspaceRepository from "../repository/workspaceRepository.js";
import workspaceMembershipRepository from "../repository/workspaceMembershipRepository.js";

const useWorkspaceDetails = (id) => {
    const [workspace, setWorkspace] = useState(null);
    const [memberships, setMemberships] = useState([]);
    const [loadingWorkspace, setLoadingWorkspace] = useState(true);
    const [loadingMemberships, setLoadingMemberships] = useState(true);

    const fetchWorkspace = useCallback(async () => {
        setLoadingWorkspace(true);
        try {
            const res = await workspaceRepository.findById(id);
            setWorkspace(res.data);
        } catch (err) {
            console.error("Workspace fetch error:", err);
        } finally {
            setLoadingWorkspace(false);
        }
    }, [id]);

    const fetchMemberships = useCallback(async () => {
        setLoadingMemberships(true);
        try {
            const res = await workspaceMembershipRepository.findByWorkspaceId(id);
            setMemberships(res.data);
        } catch (err) {
            console.error("Membership fetch error:", err);
        } finally {
            setLoadingMemberships(false);
        }
    }, [id]);

    useEffect(() => {
        fetchWorkspace();
        fetchMemberships();
    }, [fetchWorkspace, fetchMemberships]);

    return {
        workspace,
        memberships,
        loadingWorkspace,
        loadingMemberships,
        refetchWorkspace: fetchWorkspace,
        refetchMemberships: fetchMemberships,
    };
};

export default useWorkspaceDetails;
