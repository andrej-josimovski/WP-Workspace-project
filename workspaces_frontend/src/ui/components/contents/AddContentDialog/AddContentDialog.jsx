import React, { useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    MenuItem,
    Select,
    InputLabel,
    FormControl,
} from '@mui/material';

const contentTypes = ["ZIP", "PDF", "TEXT", "PNG"]; // only valid enum values

const AddContentDialog = ({ open, onClose, onAddContent }) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [type, setType] = useState(contentTypes[0]); // default to first valid type

    const handleSubmit = () => {
        onAddContent({ name, description, type });
        setName('');
        setDescription('');
        setType(contentTypes[0]);
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Add Content</DialogTitle>
            <DialogContent sx={{ minWidth: 400 }}>
                <TextField
                    autoFocus
                    margin="dense"
                    label="Name"
                    fullWidth
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <TextField
                    margin="dense"
                    label="Description"
                    fullWidth
                    multiline
                    minRows={2}
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
                <FormControl fullWidth margin="dense">
                    <InputLabel id="content-type-label">Content Type</InputLabel>
                    <Select
                        labelId="content-type-label"
                        value={type}
                        label="Content Type"
                        onChange={(e) => setType(e.target.value)}
                    >
                        {contentTypes.map((ct) => (
                            <MenuItem key={ct} value={ct}>
                                {ct}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit} disabled={!name}>
                    Add
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default AddContentDialog;
