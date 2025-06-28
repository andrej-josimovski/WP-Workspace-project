import React, {useState} from 'react';
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {Box, Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import EditWorkspaceDialog from "../EditWorkspaceDialog/EditWorkspaceDialog.jsx";
import DeleteWorkspaceDialog from "../DeleteWorkspaceDialog/DeleteWorkspaceDialog.jsx";

const WorkspaceCard = ({workspace, onEdit, onDelete}) => {
    const navigate = useNavigate();
    const [editWorkspaceDialogOpen, setEditWorkspaceDialogOpen] = useState(false);
    const [deleteWorkspaceDialogOpen, setDeleteWorkspaceDialogOpen] = useState(false);

    return (
        <>
            <Card sx={{boxShadow: 3, borderRadius: 2, p: 1}}>
                <CardContent>
                    <Typography variant="h5">{workspace.name}</Typography>
                    <Typography variant="subtitle2">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab aperiam assumenda blanditiis cum
                        ducimus enim modi natus odit quibusdam veritatis.
                    </Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button
                        size="small"
                        color="info"
                        startIcon={<InfoIcon/>}
                        onClick={() => navigate(`/workspace/${workspace.id}`)}
                    >
                        Info
                    </Button>
                    <Box>
                        <Button
                            size="small"
                            color="warning"
                            startIcon={<EditIcon/>}
                            sx={{mr: "0.25rem"}}
                            onClick={() => setEditWorkspaceDialogOpen(true)}
                        >
                            Edit
                        </Button>
                        <Button
                            size="small"
                            color="error"
                            startIcon={<DeleteIcon/>}
                            onClick={() => setDeleteWorkspaceDialogOpen(true)}
                        >
                            Delete
                        </Button>
                    </Box>
                </CardActions>
            </Card>
            <EditWorkspaceDialog
                open={editWorkspaceDialogOpen}
                onClose={() => setEditWorkspaceDialogOpen(false)}
                workspace={workspace}
                onEdit={onEdit}
            />
            <DeleteWorkspaceDialog
                open={deleteWorkspaceDialogOpen}
                onClose={() => setDeleteWorkspaceDialogOpen(false)}
                workspace={workspace}
                onDelete={onDelete}
            />
        </>
    );
};

export default WorkspaceCard;
