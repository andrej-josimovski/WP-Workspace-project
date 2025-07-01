import React from 'react';
import { Box, Container } from '@mui/material';
import Header from '../Header/Header.jsx';
import { Outlet } from 'react-router-dom';

const Layout = () => {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                height: '100vh'
            }}
        >
            <Header />
            <Container
                maxWidth={false}
                sx={{
                    flexGrow: 1,
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    px: 2,
                    textAlign: 'center'
                }}
            >
                <Outlet />
            </Container>
        </Box>
    );
};

export default Layout;
