import { useState } from "react";

const ChangeBio = ({ setBios, bioSubmitHandler }) => {
  return (
    <form onSubmit={bioSubmitHandler} className="space-y-6">
      <div>
        <label htmlFor="bio" className="text-gray-800 text-xs block mb-2">
          Bio:
        </label>
        <input
          name="bio"
          type="text"
          placeholder="Change me..."
          onChange={(e) => setBios(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <button className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none">
        Submit Changes
      </button>
    </form>
  );
};

export default ChangeBio;
