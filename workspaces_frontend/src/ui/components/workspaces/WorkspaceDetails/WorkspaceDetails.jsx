import React, {useState} from 'react';
import {Snackbar, Alert} from "@mui/material";
import {useNavigate, useParams} from "react-router";
import {
    Box,
    Button,
    CircularProgress,
    Grid,
    Typography,
    Paper,
    Breadcrumbs,
    Link
} from "@mui/material";
import {ArrowBack} from "@mui/icons-material";
import useWorkspaceDetails from "../../../../hooks/useWorkspaceDetails.js";
import useWorkspaceMemberships from "../../../../hooks/useWorkspaceMemberships.js";
import useWorkspaces from "../../../../hooks/useWorkspaces.js";
import AddMemberDialog from "../AddMemberDialog/AddMemberDialog.jsx";
import UpdateRoleDialog from "../UpdateRoleDialog/UpdateRoleDialog.jsx";
import AddContentDialog from "../../contents/AddContentDialog/AddContentDialog.jsx";
import DeleteContentDialog from "../../contents/DeleteContentDialog/DeleteContentDialog.jsx";

const WorkspaceDetails = () => {
    const {id} = useParams();
    const {workspace, memberships, refetchMemberships, refetchWorkspace} = useWorkspaceDetails(id);
    const {onAddMember, onDeleteMember, onUpdateRole} = useWorkspaceMemberships(id);
    const {onAddContent, onDeleteContent} = useWorkspaces();
    const navigate = useNavigate();

    const [addDialogOpen, setAddDialogOpen] = useState(false);
    const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
    const [selectedMember, setSelectedMember] = useState(null);

    const [addContentDialogOpen, setAddContentDialogOpen] = useState(false);

    const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
    const [selectedContent, setSelectedContent] = useState(null);

    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarSeverity, setSnackbarSeverity] = useState("error");

    const showError = (message) => {
        setSnackbarMessage(message);
        setSnackbarSeverity("error");
        setSnackbarOpen(true);
    };

    const handleAddMember = (workspaceId, userId, role) => {
        if (!onAddMember) return;
        onAddMember(workspaceId, userId, role)
            .then(() => {
                setAddDialogOpen(false);
                refetchMemberships();
            })
            .catch((err) => {
                if (err.response?.status === 403) {
                    showError("You are not the owner of this membership and cannot add members.");
                }
            });
    };

    const handleDeleteMember = (workspaceId, userId) => {
        if (!onDeleteMember) return;
        onDeleteMember(workspaceId, userId)
            .then(() => refetchMemberships())
            .catch((err) => {
                if (err.response?.status === 403) {
                    showError("You are not the owner of this membership and cannot delete the member.");
                }
            });
    };

    const handleUpdateRole = (userId, newRole) => {
        if (!onUpdateRole) return;
        onUpdateRole(workspace.id, userId, newRole)
            .then(() => refetchMemberships())
            .catch((err) => {
                if (err.response?.status === 403) {
                    showError("You are not the owner of this membership and cannot update the role.");
                }
            });
    };

    const handleAddContent = (data) => {
        if (!onAddContent) return;
        onAddContent(workspace.id, data)
            .then(() => {
                setAddContentDialogOpen(false);
                refetchWorkspace();
            })
            .catch((error) => {
                if (error.response?.status === 403) {
                    showError("You are not a member of this workspace and cannot add content.");
                }
            });
    };

    const handleDeleteContent = (contentId) => {
        onDeleteContent(workspace.id, contentId)
            .then(() => {
                refetchWorkspace();  // Refresh content after deletion
                setDeleteDialogOpen(false);
            })
            .catch((error) => {
                if (error.response?.status === 403) {
                    showError("You are not a member of this workspace and cannot delete content.");
                }
            });
    };


    if (!workspace || !memberships) {
        return (
            <Box sx={{padding: 4, display: 'flex', justifyContent: 'center'}}>
                <CircularProgress/>
            </Box>
        );
    }

    return (
        <Box>
            <Breadcrumbs aria-label="breadcrumb" sx={{mb: 3}}>
                <Link
                    underline="hover"
                    color="inherit"
                    href="#"
                    onClick={(e) => {
                        e.preventDefault();
                        navigate("/workspace");
                    }}
                >
                    Workspaces
                </Link>
                <Typography color="text.primary">{workspace.name}</Typography>
            </Breadcrumbs>

            <Paper elevation={2} sx={{p: 4, borderRadius: 4}}>
                <Grid container spacing={4}>
                    <Grid item xs={12} md={3}>
                        {/* Optional: Workspace image or info */}
                    </Grid>

                    <Grid item xs={12} md={9}>
                        <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                            {workspace.name}
                        </Typography>
                        <Typography variant="body1" sx={{mb: 3}}>
                            Description placeholder...
                        </Typography>

                        {/* Memberships Header and Add Button */}
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                            <Typography variant="h5">Memberships</Typography>
                            <Button variant="contained" onClick={() => setAddDialogOpen(true)}>
                                Add Member
                            </Button>
                        </Box>

                        {/* Memberships list with fixed height + scroll */}
                        <Grid container spacing={2}>
                            {memberships.map((membership) => (
                                <Grid item xs={12} sm={6} md={4} lg={3} key={membership.id}>
                                    <Paper sx={{p: 2, borderRadius: 2}}>
                                        <Typography variant="subtitle1" fontWeight={600}>
                                            {membership.userEmail || "No email available"}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Role: {membership.role}
                                        </Typography>
                                        <Button
                                            variant="outlined"
                                            color="primary"
                                            sx={{mt: 1, mr: 1}}
                                            onClick={() => {
                                                setSelectedMember(membership);
                                                setUpdateDialogOpen(true);
                                            }}
                                        >
                                            Update Role
                                        </Button>
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            sx={{mt: 1}}
                                            onClick={() => handleDeleteMember(workspace.id, membership.userId)}
                                        >
                                            Delete
                                        </Button>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>


                        {/* Content Header and Add Button */}
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                            <Typography variant="h5">Content</Typography>
                            <Button variant="contained" onClick={() => setAddContentDialogOpen(true)}>
                                Add Content
                            </Button>
                        </Box>

                        {/* Content list with fixed height + scroll and vertical list */}
                        {workspace.contents && workspace.contents.length > 0 ? (
                            <Box
                                sx={{
                                    maxHeight: 300,
                                    overflowY: 'auto',
                                    pr: 1,
                                }}
                            >
                                <Grid container direction="column" spacing={2}>
                                    {workspace.contents.map((content) => (
                                        <Grid item xs={12} key={content.id}>
                                            <Paper sx={{p: 2, borderRadius: 2}}>
                                                <Typography variant="subtitle1" fontWeight={600}>
                                                    {content.name}
                                                </Typography>
                                                <Typography variant="body2" color="text.secondary">
                                                    Type: {content.type}
                                                </Typography>
                                                <Typography variant="body2" color="text.secondary">
                                                    {content.description}
                                                </Typography>
                                                <Typography variant="body2" color="text.secondary">
                                                    Uploaded
                                                    at: {content.uploadedAt ? new Date(content.uploadedAt).toLocaleString() : "N/A"}
                                                </Typography>
                                                <Button
                                                    variant="outlined"
                                                    color="error"
                                                    sx={{mt: 1}}
                                                    onClick={() => {
                                                        setSelectedContent(content);
                                                        setDeleteDialogOpen(true);
                                                    }}
                                                >
                                                    Delete
                                                </Button>
                                            </Paper>
                                        </Grid>
                                    ))}
                                </Grid>
                            </Box>
                        ) : (
                            <Typography color="text.secondary">No content added yet.</Typography>
                        )}
                    </Grid>

                    <Grid item xs={12} display="flex" justifyContent="flex-start" mt={4}>
                        <Button
                            variant="outlined"
                            startIcon={<ArrowBack/>}
                            onClick={() => navigate("/workspace")}
                        >
                            Back to Workspaces
                        </Button>
                    </Grid>
                </Grid>
            </Paper>

            {/* Dialogs */}
            <AddMemberDialog
                open={addDialogOpen}
                onClose={() => setAddDialogOpen(false)}
                onAddMember={handleAddMember}
                workspaceId={workspace.id}
            />

            <UpdateRoleDialog
                open={updateDialogOpen}
                onClose={() => setUpdateDialogOpen(false)}
                onUpdateRole={handleUpdateRole}
                currentRole={selectedMember?.role}
                userId={selectedMember?.userId}
            />

            <AddContentDialog
                open={addContentDialogOpen}
                onClose={() => setAddContentDialogOpen(false)}
                onAddContent={handleAddContent}
            />

            <DeleteContentDialog
                open={deleteDialogOpen}
                onClose={() => setDeleteDialogOpen(false)}
                content={selectedContent}
                onDeleteContent={handleDeleteContent}
            />


            <Snackbar
                open={snackbarOpen}
                autoHideDuration={4000}
                onClose={() => setSnackbarOpen(false)}
                anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}
            >
                <Alert
                    severity={snackbarSeverity}
                    sx={{width: '100%'}}
                    onClose={() => setSnackbarOpen(false)}
                >
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </Box>
    );
};

export default WorkspaceDetails;
