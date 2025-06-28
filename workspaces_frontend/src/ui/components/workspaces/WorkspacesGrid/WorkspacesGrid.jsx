import React from 'react';
import WorkspaceCard from "../WorkspaceCard/WorkspaceCard.jsx";
import {Grid} from "@mui/material";

const WorkspacesGrid = ({workspaces, onEdit, onDelete}) => {
    return (
        <Grid container spacing={{xs: 2, md: 3}}>
            {workspaces.map((workspace) => (
                <Grid key={workspace.id} size={{xs: 12, sm: 6, md: 4, lg: 3}}>
                    <WorkspaceCard workspace={workspace} onEdit={onEdit} onDelete={onDelete}/>
                </Grid>
            ))}
        </Grid>
    );
};

export default WorkspacesGrid;
