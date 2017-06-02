import React, {Component, PropTypes} from 'react';
import {observer, inject} from 'mobx-react';
import {Layout, Menu, Icon, Row, Col, Button} from 'antd';
const SubMenu = Menu.SubMenu;
const {Header, Footer, Sider, Content} = Layout;

@inject('routing')
@observer
class Example extends Component {
  componentWillMount() {

  }

  render() {
    return (
      <div>
        Example
      </div>
    );
  }
}

Example.propTypes = {};

export default Example;
