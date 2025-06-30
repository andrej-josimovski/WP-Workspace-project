import React, {useState} from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, FormControl, InputLabel, Select, MenuItem
} from '@mui/material';

const UpdateRoleDialog = ({open, onClose, onUpdateRole, currentRole, userId}) => {
    const [newRole, setNewRole] = useState(currentRole);

    const handleSubmit = () => {
        if (newRole && userId) {
            onUpdateRole(userId, newRole);
            onClose();
        }
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Update Role</DialogTitle>
            <DialogContent>
                <FormControl fullWidth>
                    <InputLabel id="role-select-label">Role</InputLabel>
                    <Select
                        labelId="role-select-label"
                        value={newRole}
                        onChange={(e) => setNewRole(e.target.value)}
                    >
                        <MenuItem value="OWNER">OWNER</MenuItem>
                        <MenuItem value="MEMBER">MEMBER</MenuItem>
                    </Select>
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit}>Update</Button>
            </DialogActions>
        </Dialog>
    );
};

export default UpdateRoleDialog;