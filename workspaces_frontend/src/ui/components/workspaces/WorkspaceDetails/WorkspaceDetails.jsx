import React from 'react';
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

const WorkspaceDetails = () => {
    const {id} = useParams();
    const {workspace, memberships} = useWorkspaceDetails(id);
    const navigate = useNavigate();

    if (!workspace || !memberships) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
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
                    <Grid size={{xs: 12, md: 3}}>
                        <Box sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            mb: 4,
                            bgcolor: 'background.paper',
                            p: 3,
                            borderRadius: 3,
                            boxShadow: 1
                        }}>
                        </Box>
                    </Grid>
                    <Grid size={{xs: 12, md: 9}}>
                        <Box sx={{mb: 3}}>
                            <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                                {workspace.name}
                            </Typography>

                            <Typography variant="body1" sx={{mb: 3}}>
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi consequatur culpa
                                doloribus, enim maiores possimus similique totam ut veniam voluptatibus. Adipisci
                                consequatur, cum dolorem eaque enim explicabo fugit harum, id laborum non totam ut!
                                Architecto assumenda deserunt doloribus ducimus labore magnam officiis sunt. Autem culpa
                                eaque obcaecati quasi, quo vitae.
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant="h5" sx={{mb: 2}}>Memberships</Typography>
                        <Grid container spacing={2}>
                            {memberships.map((membership) => (
                                <Grid item xs={12} md={6} lg={4} key={membership.id}>
                                    <Paper sx={{p: 2, borderRadius: 2}}>
                                        <Typography variant="subtitle1" fontWeight={600}>
                                            {membership.user?.email || "No email available"}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Role: {membership.role}
                                        </Typography>
                                    </Paper>
                                </Grid>
                            ))}
                        </Grid>
                    </Grid>

                    <Grid size={12} display="flex" justifyContent="space-between">
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
        </Box>
    );
};
export default WorkspaceDetails;
