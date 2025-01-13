import { useState } from "react";

const ChangePassword = ({
  password,
  newPassword,
  passwordSubmitHandler,
  setPassword,
  setNewPassword,
}) => {
  return (
    <form onSubmit={passwordSubmitHandler} className="space-y-6">
      <div>
        <label htmlFor="password" className="text-gray-800 text-xs block mb-2">
          Enter your current password:
        </label>
        <input
          name="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label htmlFor="newPassword" className="text-gray-800 text-xs block mb-2">
          Enter your new password:
        </label>
        <input
          name="newPassword"
          type="password"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
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

export default ChangePassword;
