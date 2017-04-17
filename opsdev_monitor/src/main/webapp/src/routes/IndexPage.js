import React, {Component, PropTypes} from 'react';
import { Layout, Menu, Breadcrumb ,Row,Col} from 'antd';
import {inject, observer} from 'mobx-react';
import {Link} from 'react-router';

import  './IndexPage.css';
const SubMenu = Menu.SubMenu;
const { Header, Content, Footer } = Layout;

@inject('routing')
@observer
export default class IndexPage extends Component {

  render() {
    const {location, push, goBack} = this.props.routing;
    return (
    <Layout className="layout">
      <Header>
        <Row>
          <Col span={4}> <div className="logo" /></Col>
          <Col span={10}>
            <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['1']}
            style={{ lineHeight: '64px' }}  >
            <Menu.Item key="1">主控制台</Menu.Item>
            <Menu.Item key="2">设备状态</Menu.Item>
            <Menu.Item key="3">告警状态</Menu.Item>
          </Menu></Col>
          <Col span={10} className="header-setting">
            <Menu
              theme="dark"
              mode="horizontal"
              defaultSelectedKeys={['1']}
              style={{ lineHeight: '64px' }}  >
              <Menu.Item key="1">用户</Menu.Item>
              <Menu.Item key="2">设置</Menu.Item>
            </Menu>
          </Col>
        </Row>
      </Header>
      <Content>
        <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>
          {this.props.children}
        </div>
      </Content>
      <Footer style={{ textAlign: 'center' }}>
        Ant Design ©2016 Created by Ant UED
      </Footer>
    </Layout>
    )
  }
}



