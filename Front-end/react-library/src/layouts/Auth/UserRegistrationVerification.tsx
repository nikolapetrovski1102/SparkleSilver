import React, { useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';

interface RouteParams {
  userId: string;
}

const UserRegistrationVerification: React.FC = () => {
  const { userId } = useParams<RouteParams>();
  const history = useHistory();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:9091/api/users/register/verify/${userId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

        if (response.ok) {
          // If response is 200 OK, redirect to login
          history.push('/login');
        } else {
          // Handle other responses here, like showing an error message
          console.error('Failed to verify registration:', response.statusText);
        }
      } catch (error) {
        console.error('Error occurred while verifying registration:', error);
      }
    };

    fetchData();
  }, [userId, history]);

  return (
    <div>
      {/* You can add a loading indicator or message here */}
      <p>Verifying registration...</p>
    </div>
  );
};

export default UserRegistrationVerification;
