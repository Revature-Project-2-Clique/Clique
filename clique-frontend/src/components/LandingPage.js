import AuthParentComponent from "./auth-components/AuthParentComponent";
import { useUser } from './UserContext';
import Feed from "./post-components/Feed";

const LandingPage = () => {
    const { user } = useUser();

    if(!user){
        return(
            <div className="min-h-screen flex items-center justify-center bg-gray-50">
                <AuthParentComponent />
            </div>
        );
    }

    return(
        <div className="max-w-screen-xl mx-auto p-6 space-y-6 rounded-md">
            <h2 className="text-2xl font-bold text-[#003a92]">Welcome {user.username}</h2>
            <Feed explore={false}/>
        </div>
    )
}

export default LandingPage;
