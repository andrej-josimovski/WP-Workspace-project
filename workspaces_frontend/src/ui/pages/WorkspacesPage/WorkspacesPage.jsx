import React, { useState } from "react";
import { Box, Button, CircularProgress } from "@mui/material";
import WorkspacesGrid from "../../components/workspaces/WorkspacesGrid/WorkspacesGrid.jsx";
import useWorkspaces from "../../../hooks/useWorkspaces.js";
import useMemberships from "../../../hooks/useWorkspaceMemberships.js";
import useAuth from "../../../hooks/useAuth.js"; // <-- your auth hook here
import AddWorkspaceDialog from "../../components/workspaces/AddWorkspaceDialog/AddWorkspaceDialog.jsx";

const WorkspacesPage = () => {
    const { workspaces, loading, onEdit, onDelete, fetchWorkspaces } = useWorkspaces();
    const { onCreateWorkspace } = useMemberships();
    const { user } = useAuth(); // <-- get current logged-in user from auth

    const [addWorkspaceDialogOpen, setAddWorkspaceDialogOpen] = useState(false);

    const handleAddWorkspace = async (name) => {
        if (!user) {
            alert("You must be logged in to create a workspace");
            return;
        }
        try {
            await onCreateWorkspace(name);
            await fetchWorkspaces();  // <- refresh workspace list
            setAddWorkspaceDialogOpen(false);
        } catch (e) {
            console.error(e);
        }
    };

    return (
        <>
            <Box className="workspaces-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress />
                    </Box>
                )}
                {!loading && (
                    <>
                        <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => setAddWorkspaceDialogOpen(true)}
                            >
                                Add Workspace
                            </Button>
                        </Box>
                        <WorkspacesGrid workspaces={workspaces} onEdit={onEdit} onDelete={onDelete} />
                    </>
                )}
            </Box>
            <AddWorkspaceDialog
                open={addWorkspaceDialogOpen}
                onClose={() => setAddWorkspaceDialogOpen(false)}
                onAdd={handleAddWorkspace}
            />
        </>
    );
};

export default WorkspacesPage;
