const ChangePrivacy = ({ isPrivate, privacySubmitHandler }) => {

    return(
        <>
            <p>Your profile is currently set to: <strong>{isPrivate ? "Private" : "Public"}</strong>.</p>
            <button onClick={privacySubmitHandler}>
                {isPrivate ? "Make Profile Public" : "Make Profile Private"}
            </button>
        </>
    )

}

export default ChangePrivacy;