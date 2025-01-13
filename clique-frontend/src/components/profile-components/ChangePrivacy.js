const ChangePrivacy = ({ isPrivate, privacySubmitHandler }) => {
    return (
      <div className="space-y-6">
        <p className="text-gray-800 text-sm">
          Your profile is currently set to:{" "}
          <strong>{isPrivate ? "Private" : "Public"}</strong>.
        </p>
        <button
          onClick={privacySubmitHandler}
          className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none"
        >
          {isPrivate ? "Make Profile Public" : "Make Profile Private"}
        </button>
      </div>
    );
  };
  
  export default ChangePrivacy;
  