import AuthParentComponent from "./auth-components/AuthParentComponent";
import { useUser } from './UserContext';


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
        </>
    )
}

export default LandingPage