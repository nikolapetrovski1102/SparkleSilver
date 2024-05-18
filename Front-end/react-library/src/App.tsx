import React from 'react';
import './App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import ProductList from './layouts/Products/ListProducts';
import Cart from './layouts/Cart/Cart';
import AddProductForm from './layouts/Products/AddProduct';
import AddCategoryForm from './layouts/AddCategory/AddCategory';
import CreateAccount from './layouts/Auth/CreateAccount';
import Login from './layouts/Auth/Login';
import Profile from './layouts/User/Profile';
import HomePage from './layouts/HomePage/HomePage';
import ProductDetails from './layouts/Products/ProductDetails';
import EditProduct from './layouts/Products/EditProduct';
import PrivateRoute from './layouts/Auth/PrivateRoute';


export const App: React.FC = () => {
  return (
    <div className="App">
      <div className='d-flex flex-column min-vh-100 background-image'>
        <div className='flex-grow-1'>
          <Switch>
            <Route path='/' exact>
              <Redirect to='/home' />
            </Route>
            <Route path='/login'>
              <Login />
            </Route>
            <Route path='/create-account'>
              <CreateAccount onLogin={function (credentials: { username: string; password: string; }): void {
                throw new Error('Function not implemented.');
              } } onRegister={function (credentials: { username: string; password: string; }): void {
                throw new Error('Function not implemented.');
              } } />
            </Route>
            <PrivateRoute path='/home' component={HomePage} />
            <PrivateRoute path='/earrings' component={() => <ProductList category="earrings" />} />
            <PrivateRoute path='/cart' component={Cart} />
            <PrivateRoute path='/bracelets' component={() => <ProductList category="bracelets" />} />
            <PrivateRoute path='/necklaces' component={() => <ProductList category="necklaces" />} />
            <PrivateRoute path='/rings' component={() => <ProductList category="rings" />} />
            <PrivateRoute path='/add-product' component={AddProductForm} />
            <PrivateRoute path='/add-category' component={AddCategoryForm} />
            <PrivateRoute path='/profile' component={Profile} />
            <PrivateRoute path='/product/:productId' component={ProductDetails} />
            <PrivateRoute path='/edit/:productId' component={EditProduct} />
          </Switch>
        </div>
      </div>
    </div>
  );
}

export default App;
