import {useCallback, useEffect, useState} from "react";
import workspaceMembershipRepository from "../repository/workspaceMembershipRepository.js";

const initialState = {
    "memberships": [],
    "loading": true,
};

const useMemberships = () => {
    const [state, setState] = useState(initialState);

    const fetchMemberships = useCallback(() => {
        setState(initialState);
        workspaceMembershipRepository
            .findAll()
            .then((response) => {
                setState({
                    "memberships": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));
    }, []);

    const onAddMember = useCallback((workspaceId, userId, role) => {
        return workspaceMembershipRepository
            .addMember(workspaceId, userId, role)
            .then(fetchMemberships);
    }, [fetchMemberships]);

    const onCreateWorkspace = useCallback((name) => {
        return workspaceMembershipRepository
            .createWorkspace(name)
            .then(() => {
                fetchMemberships();
            })
            .catch((error) => console.log(error));
    }, [fetchMemberships]);


    const onUpdateRole = useCallback((workspaceId, memberId, role) => {
        return workspaceMembershipRepository
            .updateRole(workspaceId, memberId, role)
            .then(() => {
                fetchMemberships();
            })
    }, [fetchMemberships]);

    const onDeleteMember = useCallback((workspaceId, memberId) => {
        return workspaceMembershipRepository
            .deleteMember(workspaceId, memberId)
            .then(() => {
                fetchMemberships();
            })
    }, [fetchMemberships]);


    useEffect(() => {
        fetchMemberships();
    }, [fetchMemberships]);

    return {
        ...state,
        onAddMember: onAddMember,
        onUpdateRole: onUpdateRole,
        onDeleteMember: onDeleteMember,
        onCreateWorkspace: onCreateWorkspace
    }
}
export default useMemberships;