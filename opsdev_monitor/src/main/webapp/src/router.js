import React, {PropTypes} from 'react';
import {Router, Route,IndexRoute, browserHistory} from 'react-router';
import {RouterStore, syncHistoryWithStore} from 'mobx-react-router';

import IndexPage from './routes/IndexPage';
import MainConsole from './components/mian/MainConsole';
import DeviceMgr from './components/device/DeviceMgr';
import AlertStatus from './components/alert/AlertMgr';
import PluginMgr from './components/plugin/PluginMgr';
import List from './components/List';
import LineChartDemo from './components/LineChartDemo';
export const routingStore = new RouterStore();

const history = syncHistoryWithStore(browserHistory, routingStore);

export const CustomRouter = () => (
  <Router history={history}>
    <Route path='/' component={IndexPage}>
      <IndexRoute components={MainConsole}></IndexRoute>
      <Route path='/overview' component={MainConsole}/>
      <Route path='/device' component={DeviceMgr}/>
      <Route path='/plugin' component={PluginMgr}/>
      <Route path='/alert' component={AlertStatus}/>
      <Route path='/list' component={List}/>
      <Route path='/chart' component={LineChartDemo}/>
    </Route>
  </Router>
)



