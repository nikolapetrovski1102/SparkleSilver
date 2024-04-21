import React from 'react';
import './App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import ProductList from './layouts/Products/ListProducts';
import AddProductForm from './layouts/Products/AddProduct';
import AddCategoryForm from './layouts/AddCategory/AddCategory';
import CreateAccount from './layouts/Auth/CreateAccount';
import Login from './layouts/Auth/Login';
import Profile from './layouts/User/Profile'; // Assuming Profile component includes user info
import HomePage from './layouts/HomePage/HomePage';

export const App: React.FC = () => {
  const user = {
    username: 'john_doe',
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@example.com',
    avatarUrl: 'https://ps.w.org/user-avatar-reloaded/assets/icon-256x256.png?rev=2540745', // Provide your user's avatar URL
  };

  return (
    <div className="App">
      <Profile user={user} />
      <div className='d-flex flex-column min-vh-100 background-image'>
        <div className='flex-grow-1'>
          <Switch>
            <Route path='/' exact>
              <Redirect to='/home' />
            </Route>
            <Route path='/home'>
              <HomePage />
            </Route>
            <Route path='/earrings'>
              <ProductList />
            </Route>
            <Route path='/add-product'>
              <AddProductForm />
            </Route>
            <Route path='/add-category'>
              <AddCategoryForm />
            </Route>
            <Route path='/login'>
              <Login/>
            </Route>
            <Route path='/create-account'>
              <CreateAccount />
            </Route>
            <Route path='/profile'>
              <Profile user={user} /> {/* Render Profile component when URL matches '/profile' */}
            </Route>
          </Switch>
        </div>
      </div>
    </div>
  );
}

export default App;
