import React, {useState} from 'react';
import {
    Box, TextField, Button, Typography, Container, Paper
} from '@mui/material';
import userRepository from "../../../../repository/userRepository.js";
import {useNavigate} from "react-router";

const initialFormData = {
    "username": "",
    "password": "",
    "email": "",
};

const Register = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState(initialFormData);

    const handleChange = (event) => {
        const {name, value} = event.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = () => {
        userRepository
            .register(formData)
            .then(() => {
                console.log("The user is successfully registered.");
                setFormData(initialFormData);
                navigate("/login");
            })
            .catch((error) => console.log(error));
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={3} sx={{padding: 4, mt: 4}}>
                <Typography variant="h5" align="center" gutterBottom>Register</Typography>
                <Box>
                    <TextField
                        fullWidth label="Username"
                        name="username"
                        margin="normal"
                        required
                        value={formData.username}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Password"
                        name="password"
                        type="password"
                        margin="normal"
                        required
                        value={formData.password}
                        onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="Email"
                        name="email"
                        margin="normal"
                        required
                        value={formData.email}
                        onChange={handleChange}
                    />
                    <Button fullWidth variant="contained" type="submit" sx={{mt: 2}} onClick={handleSubmit}>
                        Register
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
};

export default Register;
