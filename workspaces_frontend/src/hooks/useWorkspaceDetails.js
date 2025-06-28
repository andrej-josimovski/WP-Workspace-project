import {useEffect, useState} from "react";
import workspaceRepository from "../repository/workspaceRepository.js";
import workspaceMembershipRepository from "../repository/workspaceMembershipRepository.js";

const useWorkspaceDetails = (id) => {
    const [state, setState] = useState({
        "workspace": null,
        "memberships": [],
    });

    useEffect(() => {
        workspaceRepository
            .findById(id)
            .then((response) => {
                setState(prevState => ({
                    ...prevState,
                    "workspace": response.data
                }));

                workspaceMembershipRepository
                    .findByWorkspaceId(id)
                    .then((res) => {
                        setState(prevState => ({
                            ...prevState,
                            "memberships": res.data
                        }));
                    })
                    .catch((error) => console.log("Membership fetch error:", error));
            })
            .catch((error) => console.log("Workspace fetch error:", error));
    }, [id]);

    return state;
};

export default useWorkspaceDetails;