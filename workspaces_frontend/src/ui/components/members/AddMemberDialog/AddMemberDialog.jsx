import React, { useState } from "react";
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    MenuItem
} from "@mui/material";

const roles = ["OWNER", "MEMBER"];

const AddMemberDialog = ({ open, onClose, onAddMember }) => {
    const [workspaceId, setWorkspaceId] = useState("");
    const [userId, setUserId] = useState("");
    const [role, setRole] = useState("MEMBER");

    const handleSubmit = () => {
        if (typeof onAddMember === "function") {
            onAddMember(workspaceId, userId, role);
            onClose();
        } else {
            console.error("onAddMember is not a function");
        }
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Add Member</DialogTitle>
            <DialogContent>
                <TextField
                    margin="dense"
                    label="Workspace ID"
                    fullWidth
                    value={workspaceId}
                    onChange={(e) => setWorkspaceId(e.target.value)}
                />
                <TextField
                    margin="dense"
                    label="User ID"
                    fullWidth
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                />
                <TextField
                    margin="dense"
                    label="Role"
                    select
                    fullWidth
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                >
                    {roles.map((r) => (
                        <MenuItem key={r} value={r}>
                            {r}
                        </MenuItem>
                    ))}
                </TextField>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} variant="contained">
                    Add
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default AddMemberDialog;
