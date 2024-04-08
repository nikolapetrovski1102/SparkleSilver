import React from 'react';
import './App.css';
import { HomePage } from './layouts/HomePage/HomePage';
import { Redirect, Route, Switch } from 'react-router-dom';


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
        </Switch>
      </div>
    </div>
  );
}