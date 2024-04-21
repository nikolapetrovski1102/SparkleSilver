import React from 'react';
import { Card } from 'react-bootstrap';
import styled from 'styled-components';
import Navbar from '../NavbarAndFooter/Navbar';

interface UserInfoProps {
  user: {
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    avatarUrl: string;
  };
}

const UserInfoCard = styled(Card)`
  width: 300px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #C65BCF; /* Purple background color */
  margin: auto; /* Center horizontally */
  margin-top: 20px; /* Add space at the top */
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const UserAvatar = styled.div`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  margin: 20px auto; /* Add margin at the top and bottom */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const AvatarImg = styled.img`
  width: 100%;
  height: auto;
  border-radius: 50%;
`;

const Profile: React.FC<UserInfoProps> = ({ user }) => {
  return (
    <div>
    <Navbar />
    <UserInfoCard>
      <UserAvatar>
        <AvatarImg src={user.avatarUrl} alt="User Avatar" />
      </UserAvatar>
      <Card.Body>
        <Card.Title style={{ color: '#fff', fontWeight: 'bold' }}>{user.username}</Card.Title>
        <Card.Text style={{ color: '#fff' }}>
          <strong>Name:</strong> {user.firstName} {user.lastName}
        </Card.Text>
        <Card.Text style={{ color: '#fff' }}>
          <strong>Email:</strong> {user.email}
        </Card.Text>
      </Card.Body>
    </UserInfoCard>
    </div>
  );
};

export default Profile;
