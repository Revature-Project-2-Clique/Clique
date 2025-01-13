import { Link } from 'react-router-dom';

const ConnectionList = ({ connections, title, onLinkClick }) => {
  return (
    <>
      <br/>
      <h2 className="text-2xl font-bold text-[#003a92]">{title}</h2><br/>
      {connections.length > 0 ? (
        <>
          {connections.map((connection) => (
            <div key={connection.userId}>
              <Link 
                className="text-sm text-[#003a92] font-bold hover:underline"
                to={`/user/${connection.userId}`}
                onClick={onLinkClick}
              >
                {connection.username}: {connection.firstName} {connection.lastName}
              </Link>
              <br/>
            </div>
          ))}
        </>
      ) : (
        <p>Nothing to display</p>
      )}
    </>
  );
}

export default ConnectionList;