import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import WorkspacesGrid from "../../components/workspaces/WorkspacesGrid/WorkspacesGrid.jsx";
import useWorkspaces from "../../../hooks/useWorkspaces.js";
import AddWorkspaceDialog from "../../components/workspaces/AddWorkspaceDialog/AddWorkspaceDialog.jsx";
import "./WorkspacesPage.css";

const WorkspacesPage = () => {
    const {workspaces, loading, onAdd, onEdit, onDelete} = useWorkspaces();
    const [addWorkspacesDialogOpen, setAddWorkspaceDialogOpen] = useState(false);

    return (
        <>
            <Box className="workspaces-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={() => setAddWorkspaceDialogOpen(true)}>
                                Add Workspace
                            </Button>
                        </Box>
                        <WorkspacesGrid workspaces={workspaces} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddWorkspaceDialog
                open={addWorkspacesDialogOpen}
                onClose={() => setAddWorkspaceDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default WorkspacesPage;
