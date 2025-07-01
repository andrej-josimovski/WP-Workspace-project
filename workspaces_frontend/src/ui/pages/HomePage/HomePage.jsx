import React from "react";
import { Box, Typography, Paper } from "@mui/material";

const HomePage = () => {
    return (
        <Box
            sx={{
                maxWidth: 600,
                width: "100%",
                mx: "auto",
                my: 6,
                p: 4,
                textAlign: "center",
                bgcolor: "background.paper",
                borderRadius: 3,
                boxShadow: 3,
            }}
        >
            <Typography variant="h4" gutterBottom sx={{ color: "black" }}>
                Welcome to Workspaces Management App! 👋
            </Typography>

            <Typography variant="body1" sx={{ color: "text.secondary", fontSize: 18, mb: 2 }}>
                Manage your workspaces effortlessly and collaborate with your team seamlessly.
            </Typography>
            <Typography variant="body2" sx={{ color: "text.secondary" }}>
                Use the navigation above to explore your workspaces or create a new one to get started.
            </Typography>
        </Box>
    );
};

export default HomePage;
