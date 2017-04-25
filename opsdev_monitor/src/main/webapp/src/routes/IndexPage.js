import React, {Component, PropTypes} from 'react';
import {Layout, Menu, Breadcrumb, Row, Col} from 'antd';
import {inject, observer} from 'mobx-react';
import {Link} from 'react-router';

import  './IndexPage.css';
const SubMenu = Menu.SubMenu;
const {Header, Content, Footer} = Layout;

@inject('routing')
@observer
export default class IndexPage extends Component {


  render() {
    const {location, push, goBack} = this.props.routing;
    console.log(this.props)
    const that = this;

    const changeRoute = ({item, key, selectedKeys})=> {
      console.log(key)
      that.props.router.push(key)
    }

    return (
      <Layout className="layout">
        <Header>
          <Row>
            <Col span={4}>
              <div className="logo">监控系统</div>
            </Col>
            <Col span={10}>
              <Menu
                theme="dark"
                mode="horizontal"
                onSelect={ changeRoute.bind(this)}
                defaultSelectedKeys={['overview']}
                style={{ lineHeight: '64px' }}>
                <Menu.Item key="overview">主控制台</Menu.Item>
                <Menu.Item key="device">设备管理</Menu.Item>
                <Menu.Item key="plugin">插件管理</Menu.Item>
                <Menu.Item key="alert">告警管理</Menu.Item>
              </Menu>
            </Col>
            <Col span={10} className="header-setting">
              <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['1']}
                style={{ lineHeight: '64px' }}>
                <Menu.Item key="1">用户</Menu.Item>
                <Menu.Item key="2">设置</Menu.Item>
              </Menu>
            </Col>
          </Row>
        </Header>
        <Content>
          <div style={{ background: '#fff', paddingTop: 10 }}>
            {this.props.children}
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          alvin198761@163.com 个人爱好
        </Footer>
      </Layout>
    )
  }
}



