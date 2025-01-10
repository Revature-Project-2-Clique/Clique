const ChangeBio = ({setBios, bioSubmitHandler}) => {
    return ( 
        <>
        <form onSubmit={bioSubmitHandler}>
            <label htmlFor="bio">Bio: </label>
            <input
                name="bio"
                type="text"
                placeholder="Change me..."
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