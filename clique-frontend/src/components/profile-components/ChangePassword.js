const ChangePassword = ({password, newPassword, passwordSubmitHandler, setPassword, setNewPassword}) => {

    return (
        <>
        <form onSubmit={passwordSubmitHandler}>
            <label htmlFor="password">Enter your current password: </label>
            <input
                name="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required 
            /><br/>
            <label htmlFor="newPassword">Enter your new password: </label>
            <input 
                name="newPassword"
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
            /><br/><br/>
            <button>Submit Changes</button><br/>
        </form>
        </>
    );

}

export default ChangePassword;