import AuthParentComponent from "./auth-components/AuthParentComponent";
import { useUser } from './UserContext';
import SearchBar from "./search-components/SearchBar";


const LandingPage = () => {

    const { user } = useUser();

    if(!user){
        return(
            <AuthParentComponent />
        );
    }

    return(
        <>
            <h2>Welcome {user.username}</h2>
            {/* Placeholder for Feed Component */}
            <SearchBar />
        </>
    )
}

export default LandingPage;