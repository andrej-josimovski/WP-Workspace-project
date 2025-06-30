import React, {useState} from 'react';
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
import {
    ArrowBack,
} from "@mui/icons-material";
import useWorkspaceDetails from "../../../../hooks/useWorkspaceDetails.js";
import useWorkspaceMemberships from "../../../../hooks/useWorkspaceMemberships.js";
import AddMemberDialog from "../AddMemberDialog/AddMemberDialog.jsx";
import UpdateRoleDialog from "../UpdateRoleDialog/UpdateRoleDialog.jsx";

const WorkspaceDetails = () => {
    const {id} = useParams();
    const {workspace, memberships, refetchMemberships} = useWorkspaceDetails(id);
    const navigate = useNavigate();
    const [addDialogOpen, setAddDialogOpen] = useState(false);
    const {onAddMember, onDeleteMember} = useWorkspaceMemberships(id); // Use id directly
    const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
    const [selectedMember, setSelectedMember] = useState(null);
    const {onUpdateRole} = useWorkspaceMemberships(id); // Assume this exists


    const handleAddMember = (workspaceId, userId, role) => {
        if (!onAddMember) return;
        onAddMember(workspaceId, userId, role)
            .then(() => {
                setAddDialogOpen(false);
                refetchMemberships(); // Refresh members after adding
            })
            .catch((err) => console.error(err));
    };

    const handleDeleteMember = (workspaceId, userId) => {
        if (!onDeleteMember) return;
        onDeleteMember(workspaceId, userId)
            .then(() => refetchMemberships())
            .catch((err) => console.error(err));
    };

    const handleUpdateRole = (userId, newRole) => {
        if (!onUpdateRole) return;
        onUpdateRole(workspace.id, userId, newRole)
            .then(() => refetchMemberships())
            .catch((err) => console.error(err));
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
                        {/* Optional: Workspace image or info here */}
                    </Grid>

                    <Grid item xs={12} md={9}>
                        <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                            {workspace.name}
                        </Typography>
                        <Typography variant="body1" sx={{mb: 3}}>
                            Description placeholder...
                        </Typography>
                    </Grid>

                    <Grid item xs={12}>
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                            <Typography variant="h5">Memberships</Typography>
                            <Button variant="contained" onClick={() => setAddDialogOpen(true)}>
                                Add Member
                            </Button>
                        </Box>

                        <Grid container spacing={2}>
                            {memberships.map((membership) => (
                                <Grid item xs={12} md={6} lg={4} key={membership.id}>
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
        </Box>
    );
};

export default WorkspaceDetails;