import Cookies from 'js-cookie';

export const isAuthenticated = (): boolean => {
  const userId = Cookies.get('userId'); // Replace 'userId' with your actual cookie name
  console.log('userId from cookies:', userId);
  return !!userId;
};
