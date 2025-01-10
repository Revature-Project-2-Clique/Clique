const ChangeBio = ({bio, setBios, bioSubmitHandler}) => {
    return ( 
        <>
        <form onSubmit={bioSubmitHandler}>
            <label htmlFor="bio">Bio: </label>
            <input
                name="bio"
                type="text"
                value={bio}
                onChange={(e) => setBios(e.target.value)} 
            />
            <br/>
            <br/>
            <button>Submit Changes</button><br/>
        </form>
        </>
     );
}
 
export default ChangeBio;