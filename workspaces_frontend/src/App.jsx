import React from 'react';
import WorkspacesPage from "./ui/pages/WorkspacesPage/WorkspacesPage.jsx";
import {BrowserRouter, Routes, Route} from "react-router";
import Layout from "./ui/components/layout/Layout/Layout.jsx";
import HomePage from "./ui/pages/HomePage/HomePage.jsx";
import WorkspaceDetails from "./ui/components/workspaces/WorkspaceDetails/WorkspaceDetails.jsx";
import Register from "./ui/components/auth/Register/Register.jsx";
import Login from "./ui/components/auth/Login/Login.jsx";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/register" element={<Register/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<HomePage/>}/>
                    <Route path="workspace" element={<WorkspacesPage/>}/>
                    <Route path="workspace/:id" element={<WorkspaceDetails/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
