import {useCallback, useEffect, useState} from "react";
import workspaceRepository from "../repository/workspaceRepository.js";

const initialState = {
    "workspaces": [],
    "loading": true,
};

const useWorkspaces = () => {
    const [state, setState] = useState(initialState);

    const fetchWorkspaces = useCallback(() => {
        setState(initialState);
        workspaceRepository
            .findAll()
            .then((response) => {
                setState({
                    "workspaces": response.data,
                    "loading": false,
                });
                console.log(response.data)
            })
            .catch((error) => console.log(error));
    }, []);

    const onAdd = useCallback((data) => {
        workspaceRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new workspace.")
                fetchWorkspaces();
            })
            .catch((error) => console.log(error));
    }, [fetchWorkspaces]);

    const onEdit = useCallback((id, data) => {
        console.log("Editing workspace ID:", id);
        workspaceRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the workspace with id ${id}.`);
                fetchWorkspaces();
            })
            .catch((error) => console.log(error));
    }, [fetchWorkspaces]);

    const onDelete = useCallback((id) => {
        console.log("Deleting workspace ID:", id);
        workspaceRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the workspace with id ${id}.`);
                fetchWorkspaces();
            })
            .catch((error) => console.log(error));
    }, [fetchWorkspaces]);

    const onAddContent = useCallback((id, data) => {
        workspaceRepository
            .addContent(id, data)
            .then(() => fetchWorkspaces())
            .catch((error) => console.log(error))
    }, [fetchWorkspaces]);

    useEffect(() => {
        fetchWorkspaces();
    }, [fetchWorkspaces]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onAddContent: onAddContent, onDelete: onDelete, fetchWorkspaces};

};
export default useWorkspaces;