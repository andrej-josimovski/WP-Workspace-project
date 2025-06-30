import React, { useState } from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField
} from "@mui/material";

const AddWorkspaceDialog = ({ open, onClose, onAdd }) => {
    const [name, setName] = useState("");

    const handleChange = (event) => {
        setName(event.target.value);
    };

    const handleSubmit = () => {
        if (!name.trim()) return; // Optional: prevent empty names
        onAdd(name); // Only send the name, backend knows current user
        setName("");
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Add Workspace</DialogTitle>
            <DialogContent>
                <TextField
                    margin="dense"
                    label="Workspace Name"
                    name="name"
                    value={name}
                    onChange={handleChange}
                    fullWidth
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="primary">
                    Add
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default AddWorkspaceDialog;
