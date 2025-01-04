import { createContext, useState, useContext } from "react";

const UserContext = createContext();

export const UserProvider = ({children}) => {

    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);

    const updateUser = (newUser, newToken) => {
        setUser(newUser);  
        setToken(newToken);
    }

    const clearUser = () => {
        setUser(null);
        setToken(null)
    }

    return (
        <UserContext.Provider value ={{ user, token, updateUser, clearUser }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUser = () => {
    return useContext(UserContext);
};