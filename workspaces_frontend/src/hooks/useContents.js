import {useCallback, useEffect, useState} from "react";
import contentRepository from "../repository/contentRepository.js";

const initialState = {
    "contents": [],
    "loading": true,
};

const useContents = () => {
    const [state, setState] = useState(initialState);

    const fetchContents = useCallback(() => {
        setState(initialState);
        contentRepository
            .findAll()
            .then((response) => {
                setState({
                    "contents": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));
    }, []);

    const onAdd = useCallback((data) => {
        contentRepository
            .add(data)
            .then(() => {
                fetchContents();
            })
            .catch((error) => console.log(error));
    }, [fetchContents]);

    const onEdit = useCallback((id, data) => {
        contentRepository
            .edit(id, data)
            .then(() => {
                fetchContents();
            })
            .catch((error) => console.log(error));
    }, [fetchContents]);

    const onDelete = useCallback((id) => {
        contentRepository
            .delete(id)
            .then(() => {
                fetchContents();
            })
            .catch((error) => console.log(error));
    }, [fetchContents]);

    useEffect(() => {
        fetchContents();
    }, [fetchContents]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete}
}
export default useContents;