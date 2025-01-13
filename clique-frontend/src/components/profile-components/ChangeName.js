import { useState } from "react";

const ChangeName = ({
  firstName,
  lastName,
  nameSubmitHandler,
  setFirstName,
  setLastName,
}) => {
  return (
    <form onSubmit={nameSubmitHandler} className="space-y-6">
      <div>
        <label htmlFor="firstName" className="text-gray-800 text-xs block mb-2">
          First Name:
        </label>
        <input
          name="firstName"
          type="text"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          required
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label htmlFor="lastName" className="text-gray-800 text-xs block mb-2">
          Last Name:
        </label>
        <input
          name="lastName"
          type="text"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          required
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <button className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none">
        Submit Changes
      </button>
    </form>
  );
};

export default ChangeName;
