import React from 'react';
import './App.css';
import { HomePage } from './layouts/HomePage/HomePage';
import { Redirect, Route, Switch } from 'react-router-dom';
import ProductList from './layouts/Products/ListProducts';
import AddProductForm from './layouts/Products/AddProduct';
import AddCategoryForm from './layouts/AddCategory/AddCategory';
import CreateAccount from './layouts/Auth/CreateAccount';
import Login from './layouts/Auth/Login';


export const App = () => {
  return (
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
        </Switch>
      </div>
    </div>
  );
}