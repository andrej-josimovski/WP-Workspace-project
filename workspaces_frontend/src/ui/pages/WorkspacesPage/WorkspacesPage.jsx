import React, {useState} from "react";
import {Box, Button, CircularProgress} from "@mui/material";
import WorkspacesGrid from "../../components/workspaces/WorkspacesGrid/WorkspacesGrid.jsx";
import useWorkspaces from "../../../hooks/useWorkspaces.js";
import useMemberships from "../../../hooks/useWorkspaceMemberships.js";
import useAuth from "../../../hooks/useAuth.js"; // <-- your auth hook here
import AddWorkspaceDialog from "../../components/workspaces/AddWorkspaceDialog/AddWorkspaceDialog.jsx";
import {Snackbar, Alert} from "@mui/material";


const WorkspacesPage = () => {
    const {workspaces, loading, onEdit, onDelete, fetchWorkspaces} = useWorkspaces();
    const {onCreateWorkspace} = useMemberships();
    const {user} = useAuth(); // <-- get current logged-in user from auth
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarSeverity, setSnackbarSeverity] = useState("error");

    const showError = (message) => {
        setSnackbarMessage(message);
        setSnackbarSeverity("error");
        setSnackbarOpen(true);
    };


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
                        <CircularProgress/>
                    </Box>
                )}
                {!loading && (
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => setAddWorkspaceDialogOpen(true)}
                            >
                                Add Workspace
                            </Button>
                        </Box>
                        <WorkspacesGrid
                            workspaces={workspaces}
                            onEdit={async (id, data) => {
                                try {
                                    await onEdit(id, data);
                                } catch (e) {
                                    if (e.response?.status === 403) {
                                        showError("You are not the owner of this workspace and cannot edit it.");
                                    }
                                }
                            }}
                            onDelete={async (id) => {
                                try {
                                    await onDelete(id);
                                } catch (e) {
                                    if (e.response?.status === 403) {
                                        showError("You are not the owner of this workspace and cannot delete it.");
                                    }
                                }
                            }}
                        />
                    </>
                )}
            </Box>
            <AddWorkspaceDialog
                open={addWorkspaceDialogOpen}
                onClose={() => setAddWorkspaceDialogOpen(false)}
                onAdd={handleAddWorkspace}
            />
            <Snackbar
                open={snackbarOpen}
                autoHideDuration={4000}
                onClose={() => setSnackbarOpen(false)}
                anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}
            >
                <Alert severity={snackbarSeverity} sx={{width: '100%'}} onClose={() => setSnackbarOpen(false)}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>

        </>
    );
};

export default WorkspacesPage;
